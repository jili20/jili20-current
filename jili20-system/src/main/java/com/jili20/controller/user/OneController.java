package com.jili20.controller.user;


import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.OneService;
import com.jili20.util.AuthUtil;
import com.jili20.util.RegexValidateUtils;
import com.jili20.vo.user.OneAddVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 投放诗语表（个人网站版） 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-04-29
 */
@Api(tags = "User - 投放诗语管理（个人网站版）")
@RestController
@RequestMapping("/one")
public class OneController {

    @Resource
    private OneService oneService;

    @ApiOperation("门户网站 - 首页 - 新增 - 投放诗语")
    @PostMapping("/add")
    public Result add(@RequestBody OneAddVo vo) {
        // 检查登录状态
        final Long authUserId = AuthUtil.getAuthUserId();
        if (null == authUserId) {
            return Result.error().message("请登录后再赞助");
        }
        // 取值
        final String avatar = vo.getAvatar();
        final String username = vo.getUsername();
        final String message = vo.getMessage();
        final String link = vo.getLink();
        // 判空
        Assert.notEmpty(avatar, ResponseEnum.AVATAR_LENGTH_ERROR);
        Assert.notEmpty(username, ResponseEnum.USERNAME_NULL_ERROR);
        Assert.notEmpty(message, ResponseEnum.MESSAGE_NULL_ERROR);
        // 限制长度
        Assert.isTrue(avatar.length() >= 10 && avatar.length() <= 255, ResponseEnum.AVATAR_LENGTH_ERROR);
        Assert.isTrue(RegexValidateUtils.checkUsername(username), ResponseEnum.USERNAME_LENGTH_ERROR);
        Assert.isTrue(message.length() >= 1 && message.length() <= 200, ResponseEnum.MESSAGE_LENGTH_ERROR);
        if (StringUtils.isNotBlank(link)) {
            Assert.isTrue(link.length() > 10 && link.length() <= 50,ResponseEnum.LINK_LENGTH_ERROR);
        }
        // 校验通过，执行新增
        return oneService.add(vo);
    }


    @ApiOperation("门户网站 - 通知详情页面 - 根据ID删除")
    @DeleteMapping("/user/remove/{id}")
    public Result removeById(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        return oneService.removeByOneId(id);
    }


}

