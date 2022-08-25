package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jili20.client.ArticleClient;
import com.jili20.client.vo.UserMainInfoVo;
import com.jili20.entity.SysUser;
import com.jili20.entity.security.LoginUser;
import com.jili20.entity.security.UserInfoVo;
import com.jili20.entity.security.UserLoginVo;
import com.jili20.enums.UserStatusEnum;
import com.jili20.exception.Assert;
import com.jili20.mapper.SysUserMapper;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.SysUserService;
import com.jili20.service.ThreadService;
import com.jili20.util.AuthUtil;
import com.jili20.util.JwtUtil;
import com.jili20.util.RedisCache;
import com.jili20.util.RegexValidateUtils;
import com.jili20.vo.api.SysUserVo;
import com.jili20.vo.user.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-01-06
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisCache redisCache;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private ThreadService threadService;

    @Resource
    private ArticleClient articleClient;


    /**
     * 门户网站 - 用户登录 - 实现登录功能 - 使用图灵验证码
     *
     * @param vo
     * @param request
     * @param response
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Result login(LoginVo vo, HttpServletRequest request, HttpServletResponse response) {
        // 1、取值、判空、校验格式
        String username = vo.getUsername();
        String password = vo.getPassword();
        // 判空：验证码、用户名、密码
        Assert.notEmpty(username, ResponseEnum.USERNAME_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);
        // 校验格式：用户名、密码
        Assert.isTrue(RegexValidateUtils.checkUsername(username), ResponseEnum.USERNAME_FORMAT_ERROR);
        Assert.isTrue(RegexValidateUtils.checkPwd(password), ResponseEnum.PASSWORD_FORMAT_ERROR);
        // 3、准备进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = null;
        // 用户登录输入的可能是：用户名，或手机号，或邮箱
        // 正则匹配用户输入的格式
        String email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String phone = "^[1][3578]\\d{9}$";
        // 如果用户的输入，格式符合邮箱，为邮箱登陆
        if (username.matches(email)) {
            // 通过邮箱查询数据库用户
            SysUser dbUser = baseMapper.selectOne(new QueryWrapper<SysUser>().eq("email", username));
            // 用户不存在
            Assert.notNull(dbUser, ResponseEnum.USER_NO_EXIST_ERROR);
            if (UserStatusEnum.CLOSE_FOREVER.getCode().equals(dbUser.getStatus())) {
                return Result.error().message("此账号已被永久封禁");
            }
            authenticationToken = new UsernamePasswordAuthenticationToken(dbUser.getUsername(), password);
        } else if (username.matches(phone)) {
            // 如果用户的输入，格式符合手机号，为手机号登陆
            SysUser dbUser = baseMapper.selectOne(new QueryWrapper<SysUser>().eq("phone", username));
            // 用户不存在
            Assert.notNull(dbUser, ResponseEnum.USER_NO_EXIST_ERROR);
            // 检查用户状态，是否被封号
            if (UserStatusEnum.CLOSE_FOREVER.getCode().equals(dbUser.getStatus())) {
                return Result.error().message("很抱歉，您的账号已被锁定，不能再登录");
            }
            authenticationToken = new UsernamePasswordAuthenticationToken(dbUser.getUsername(), password);
        } else {
            // 如果用户的输入，不是邮箱，也不是手机号，那就是用户名登陆
            SysUser dbUser = baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
            // 用户不存在
            Assert.notNull(dbUser, ResponseEnum.USER_NO_EXIST_ERROR);
            // 检查用户状态，是否被封号
            if (UserStatusEnum.CLOSE_FOREVER.getCode().equals(dbUser.getStatus())) {
                return Result.error().message("很抱歉，您的账号已被锁定，不能再登录");
            }
            // 去认证
            authenticationToken = new UsernamePasswordAuthenticationToken(dbUser.getUsername(), password);
        }

        // 4、由 Security 框架进入认证，采用的自定义的 UserDetailsServiceImpl
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 5、认证通过，生成jwt（断点中查看到 authenticate 为 LoginUser 类型，由我们在自定义的 UserDetailsServiceImpl 里放入）
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 获取用户ID，使用 userId 生成一个 jwt token
        String userId = loginUser.getUser().getId().toString();
        // 生成jwt 默认有效时长 5 小时
        String jwt = JwtUtil.createJWT(userId);
        // 6、把用户信息存入 redis，userId 作为 key，过期时间为 5 小时
        redisCache.setCacheObject("login:" + userId, loginUser, 12, TimeUnit.HOURS);
        // 把 LoginUser 封装成 UserInfoVo
        final UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(loginUser.getUser().getId());
        userInfoVo.setUsername(loginUser.getUsername());
        userInfoVo.setAvatar(loginUser.getUser().getAvatar());
        // 把 UserInfoVo 封装成 UserLoginVo（ 把 token 和用户信息存放在一起 ）
        final UserLoginVo userLoginVo = new UserLoginVo(jwt, userInfoVo);
        // 7、多线程-更新用户最后登录时间、登录ip
        threadService.updateUserLoginIpAndLastLoginTime(request, sysUserMapper, userId);
        return Result.ok().data("user", userLoginVo).message("登录成功");
    }


    /**
     * 门户网站 - 退出登录
     * 后台管理 - 退出登录
     *
     * @return
     */
    @Override
    public Result logout() {
        // 获取 SecurityContextHolder 中的用户 id
        // 删除 redis 中的值
        redisCache.deleteObject("login:" + AuthUtil.getAuthUserId());
        return Result.ok().message("退出登录成功");
    }


    /**
     * 用户 - 根据用户ID，查询用户：ID、用户名、头像、赞赏码 - 被远程调用的接口
     *
     * @param userId
     * @return
     */
    @Override
    public UserMainInfoVo findUserMainInfo(Long userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        if (sysUser != null) {
            UserMainInfoVo userMainInfo = new UserMainInfoVo();
            userMainInfo.setId(userId);
            userMainInfo.setUsername(sysUser.getUsername());
            userMainInfo.setAvatar(sysUser.getAvatar());
            return userMainInfo;
        }
        return null;
    }


    /**
     * 门户网站 - 个人主页 - 根据路由用户ID - 获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public Result getUserInfo(Long userId) {
        final SysUser sysUser = baseMapper.selectById(userId);
        if (sysUser == null) {
            return Result.error().message("该用户不存在");
        }
        // 转为VO类型
        final SysUserVo vo = new SysUserVo();
        vo.setId(sysUser.getId());
        vo.setUsername(sysUser.getUsername());
        vo.setAvatar(sysUser.getAvatar());
        vo.setEmail(sysUser.getEmail());
        vo.setSign(sysUser.getSign());
        vo.setLastLoginTime(sysUser.getLastLoginTime());
        vo.setCreateTime(sysUser.getCreateTime());
        // 将手机替换成 137****9602
        vo.setPhone(handlingSensitive(sysUser.getPhone(), 3, 4));
        // 返回数据
        return Result.ok().data("user", vo);
    }


    /**
     * 屏蔽敏感信息
     * 子方法,过滤敏感数据,将用户手机号 137****9602
     *
     * @param value  待屏蔽的敏感字符串
     * @param before 前面保留位数  3
     * @param after  后面保留位数  4
     * @return java.lang.String
     */
    private String handlingSensitive(String value, Integer before, Integer after) {
        String res = "";
        if (!StringUtils.isEmpty(value)) {
            StringBuilder stringBuilder = new StringBuilder(value);
            try {
                res = stringBuilder.replace(before, value.length() - after, "****").toString();
            } catch (StringIndexOutOfBoundsException e) {
                res = value;
            }
        }
        return res;
    }


    /**
     * 检查用户名是否已被注册 - 修改用户名用
     *
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        Integer count = baseMapper.selectCount(new QueryWrapper<SysUser>().eq("username", username));
        return count > 0;
    }


    @Override
    public Result updateUsername(UsernameVo vo) {
        final String username = vo.getUsername();
        // 通过当前登录用户ID查出该用户信息
        final SysUser sysUser = baseMapper.selectById(AuthUtil.getAuthUserId());
        // 比对新用户名是否与旧用户名相同,相同则给出错误提示信息
        if (username.equals(sysUser.getUsername())) {
            return Result.setResult(ResponseEnum.NEW_USERNAME_OLD_USERNAME_IDENTICAL_ERROR);
        }
        // 检查新用户名是否被占用
        final boolean checkUsername = this.checkUsername(username);
        if (checkUsername) {
            return Result.error().message("该用户名已被占用");
        }
        sysUser.setUsername(username);
        sysUser.setUpdateTime(LocalDateTime.now());
        final int count = baseMapper.updateById(sysUser);
        if (count > 0) {
            return Result.ok().message("修改用户名成功");
        }
        return Result.error().message("修改用户名失败，请重试");
    }


    /**
     * 门户网站 - 私人空间 - 用户修改个性签名
     *
     * @param vo
     * @return
     */
    @Override
    public Result updateUserSign(UserSignVo vo) {
        final SysUser sysUser = baseMapper.selectById(AuthUtil.getAuthUserId());
        sysUser.setSign(vo.getSign());
        sysUser.setUpdateTime(LocalDateTime.now());
        final int count = baseMapper.updateById(sysUser);
        if (count > 0) {
            return Result.ok().message("修改个性签名成功");
        }
        return Result.error().message("修改个性签名失败");
    }


    /**
     * 门户网站 - 私人空间 - 用户修改密码
     *
     * @param vo
     * @return
     */
    @Override
    public Result userUpdatePassword(UserPasswordVo vo) {
        final SysUser sysUser = baseMapper.selectById(AuthUtil.getAuthUserId());
        Assert.notNull(sysUser, ResponseEnum.USER_NO_EXIST_ERROR);
        // 比对用户输入的旧密码与数据密码是否匹配
        if (!passwordEncoder.matches(vo.getPassword(), sysUser.getPassword())) {
            return Result.error().message("现用密码错误");
        }
        // 密码加密处理
        sysUser.setPassword(passwordEncoder.encode(vo.getNewPassword()));
        sysUser.setPwdUpdateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        final int count = baseMapper.updateById(sysUser);
        if (count > 0) {
            return Result.ok().message("修改密码成功");
        }
        return Result.error().message("修改密码失败，请重试");
    }


    /**
     * 门户网站 - 私人空间 - 用户修改头像
     * 删除赞助 1元、VIP 赞助的缓存
     *
     * @param vo
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "sponsor", allEntries = true),
            @CacheEvict(value = "patron", allEntries = true)
    })
    @Override
    public Result userUpdateAvatar(UseAvatarVo vo) {
        // 通过当前登录用户ID查出该用户信息
        final SysUser sysUser = baseMapper.selectById(AuthUtil.getAuthUserId());
        sysUser.setAvatar(vo.getAvatar());
        sysUser.setUpdateTime(LocalDateTime.now());
        final int result = baseMapper.updateById(sysUser);
        if (result > 0) {
            // 远程调用文章微服务，更新 article、comment、reply 表用户头像字段
            articleClient.userUpdateAvatar(vo.getAvatar(), AuthUtil.getAuthUserId());
            return Result.ok().message("修改头像成功");
        }
        return Result.error().message("修改头像失败，请重试");
    }


}
