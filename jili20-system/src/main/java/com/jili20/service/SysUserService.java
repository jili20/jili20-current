package com.jili20.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.client.vo.UserMainInfoVo;
import com.jili20.entity.SysUser;
import com.jili20.result.Result;
import com.jili20.vo.user.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-01-06
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 门户网站 - 登录 - 用户名和密码登录
     *
     * @param vo       用户
     * @param request
     * @param response
     * @return
     */
    Result login(LoginVo vo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户 - 退出登录
     * @return
     */
    Result logout();


    /**
     * 用户 - 根据用户ID，查询用户：ID、用户名、头像、赞赏码 - 被远程调用的接口
     * @param userId
     * @return
     */
    UserMainInfoVo findUserMainInfo(Long userId);


    /**
     * 门户网站 - 个人主页 - 根据路由用户ID - 获取用户信息
     * @param userId
     * @return
     */
    Result getUserInfo(Long userId);

    /**
     * 检查用户名是否已被注册
     * @param username
     * @return
     */
    boolean checkUsername(String username);


    /**
     * 门户网站 - 私人空间 - 用户修改用户名
     * 涉及到远程调用，同时更新四张表：sys_user、article、comment、looper
     * @param vo
     * @return
     */
    Result updateUsername(UsernameVo vo);


    /**
     * 门户网站 - 私人空间 - 用户修改个性签名
     * @param vo
     * @return
     */
    Result updateUserSign(UserSignVo vo);


    /**
     * 门户网站 - 私人空间 - 用户修改密码
     * @param vo
     * @return
     */
    Result userUpdatePassword(UserPasswordVo vo);


    /**
     * 门户网站 - 私人空间 - 用户修改头像
     * @param vo
     * @return
     */
    Result userUpdateAvatar(UseAvatarVo vo);


}