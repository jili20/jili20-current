package com.jili20.controller.user;

import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.SysUserService;
import com.jili20.util.RegexValidateUtils;
import com.jili20.vo.user.UseAvatarVo;
import com.jili20.vo.user.UserPasswordVo;
import com.jili20.vo.user.UserSignVo;
import com.jili20.vo.user.UsernameVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author bing_  @create 2022/1/15-13:24
 */
@Api(tags = "User - 用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private SysUserService sysUserService;


    @ApiOperation("用户 - 退出登录")
    @DeleteMapping("/logout")
    public Result logout() {
        return sysUserService.logout();
    }


    /**
     * 涉及到远程调用，同时更新四张表：sys_user、article、comment、looper
     *
     * @param vo
     * @return
     */
    @ApiOperation("门户网站 - 私人空间 - 用户修改用户名")
    @PostMapping("/update/username")
    public Result updateUsername(@RequestBody UsernameVo vo) {
        Assert.notEmpty(vo.getUsername(), ResponseEnum.USERNAME_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkUsername(vo.getUsername()), ResponseEnum.USERNAME_FORMAT_ERROR);
        return sysUserService.updateUsername(vo);
    }


    /**
     * 门户网站 - 私人空间 - 用户修改个性签名
     *
     * @param vo UserSignVo
     * @return
     */
    @ApiOperation("门户网站 - 私人空间 - 用户修改个性签名")
    @PostMapping("/update/sign")
    public Result updateUserSign(@RequestBody UserSignVo vo) {
        Assert.notEmpty(vo.getSign(), ResponseEnum.SIGN_NULL_ERROR);
        Assert.isTrue(vo.getSign().length() >= 1 && vo.getSign().length() <= 30, ResponseEnum.SIGN_LENGTH_ERROR);
        return sysUserService.updateUserSign(vo);
    }


    @ApiOperation("门户网站 - 私人空间 - 用户修改密码")
    @PostMapping("/updatePassword")
    public Result userUpdatePassword(@RequestBody UserPasswordVo vo) {
        // 判空
        final String password = vo.getPassword();
        final String newPassword = vo.getNewPassword();
        final String confirmPassword = vo.getConfirmPassword();
        // 判空
        Assert.notNull(password, ResponseEnum.PASSWORD_NULL_ERROR);
        Assert.notEmpty(newPassword, ResponseEnum.NEW_PASSWORD_NULL_ERROR);
        Assert.notEmpty(confirmPassword, ResponseEnum.CONFIRM_PASSWORD_NULL_ERROR);
        // 限制长度
        Assert.isTrue(password.length() >= 6 && password.length() <= 20, ResponseEnum.PASSWORD_FORMAT_ERROR);
        Assert.isTrue(newPassword.length() >= 6 && newPassword.length() <= 20, ResponseEnum.NEW_PASSWORD_FORMAT_ERROR);
        Assert.isTrue(confirmPassword.length() >= 6 && confirmPassword.length() <= 20, ResponseEnum.CONFIRM_PASSWORD_FORMAT_ERROR);
        // 去执行修改
        return sysUserService.userUpdatePassword(vo);
    }


    /**
     * 涉及到远程调用，同时更新三张表：sys_user、article、comment
     *
     * @param vo
     * @return
     */
    @ApiOperation("门户网站 - 私人空间 - 用户修改头像")
    @PostMapping("/update/avatar")
    public Result userUpdateAvatar(@RequestBody UseAvatarVo vo) {
        Assert.notEmpty(vo.getAvatar(), ResponseEnum.AVATAR_NULL_ERROR);
        Assert.isTrue(vo.getAvatar().length() >= 10 && vo.getAvatar().length() <= 255, ResponseEnum.AVATAR_LENGTH_ERROR);
        return sysUserService.userUpdateAvatar(vo);
    }


}
