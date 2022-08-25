package com.jili20.controller.user;


import com.jili20.entity.Looper;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.LooperService;
import com.jili20.util.AuthUtil;
import com.jili20.util.RegexValidateUtils;
import com.jili20.vo.user.LooperIndexVo;
import com.jili20.vo.user.UserLooperEditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 轮播图表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "User - 轮播图管理")
@RestController
@RequestMapping("/looper")
public class UserLooperController {

    @Resource
    private LooperService looperService;


    @ApiOperation("门户网站 - 用户 - 新增轮播图片")
    @PostMapping("/add")
    public Result add(@RequestBody LooperIndexVo vo) {
        // 检查当前用户登录状态，未登录给错误提示
        Long authUserId = AuthUtil.getAuthUserId();
        Assert.notNull(authUserId, ResponseEnum.NEED_LOGIN);
        // 取值
        String title = vo.getTitle();
        String looperUrl = vo.getLooperUrl();
        String looperLink = vo.getLooperLink();
        String position = vo.getPosition();
        // 判空
        Assert.notEmpty(title, ResponseEnum.LOOP_TITLE_NULL_ERROR);
        Assert.notEmpty(looperUrl, ResponseEnum.LOOP_LOOPER_URL_NULL_ERROR);
        Assert.notEmpty(position, ResponseEnum.LOOP_POSITION_NULL_ERROR);
        // 限制长度
        Assert.isTrue(title.length() >= 1 && title.length() <= 30, ResponseEnum.LOOP_TITLE_LENGTH_ERROR);
        Assert.isTrue(looperUrl.length() >= 10 && looperUrl.length() <= 255, ResponseEnum.LOOP_LOOPER_URL_LENGTH_ERROR);
        if (StringUtils.isNotBlank(looperLink)) {
            Assert.isTrue(looperLink.length() >= 10 && looperLink.length() <= 255, ResponseEnum.LOOP_LOOPER_LINK_LENGTH_ERROR);
        }
        Assert.isTrue(RegexValidateUtils.checkLength1(position), ResponseEnum.LOOP_POSITION_LENGTH_ERROR);

        // 通过上面校验，去执行新增
        return looperService.add(vo);
    }


    @ApiOperation("门户网站 - 轮播图 - 根据id查询轮播图详情")
    @GetMapping("/{looperId}")
    public Result view(@PathVariable("looperId") Long looperId) {
        // 判空
        Assert.notNull(looperId, ResponseEnum.LOOP_ID_NULL_ERROR);
        final Looper looper = looperService.getById(looperId);
        return Result.ok().data("looper", looper);
    }


    @ApiOperation("门户网站 - 用户 - 编辑轮播图片")
    @PutMapping("/update")
    public Result update(@RequestBody UserLooperEditVo vo) {
        // 检查当前用户登录状态，未登录给错误提示
        Long authUserId = AuthUtil.getAuthUserId();
        Assert.notNull(authUserId, ResponseEnum.NEED_LOGIN);
        // 取值
        final Long id = vo.getId();
        String title = vo.getTitle();
        String looperUrl = vo.getLooperUrl();
        String looperLink = vo.getLooperLink();
        String position = vo.getPosition();
        // 判空
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        Assert.notEmpty(title, ResponseEnum.LOOP_TITLE_NULL_ERROR);
        Assert.notEmpty(looperUrl, ResponseEnum.LOOP_LOOPER_URL_NULL_ERROR);
        Assert.notEmpty(position, ResponseEnum.LOOP_POSITION_NULL_ERROR);
        // 限制长度
        Assert.isTrue(title.length() >= 1 && title.length() <= 30, ResponseEnum.LOOP_TITLE_LENGTH_ERROR);
        Assert.isTrue(looperUrl.length() >= 10 && looperUrl.length() <= 255, ResponseEnum.LOOP_LOOPER_URL_LENGTH_ERROR);
        if (StringUtils.isNotBlank(looperLink)) {
            Assert.isTrue(looperLink.length() >= 10 && looperLink.length() <= 255, ResponseEnum.LOOP_LOOPER_LINK_LENGTH_ERROR);
        }
        Assert.isTrue(RegexValidateUtils.checkLength1(position), ResponseEnum.LOOP_POSITION_LENGTH_ERROR);
        // 通过上面校验，去执行编辑
        return looperService.updateByLooperId(vo);
    }


    @ApiOperation("门户网站 - 私人空间 - 根据ID删除轮播图")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        Assert.notNull(id, ResponseEnum.ID_NULL_ERROR);
        return looperService.removeByLooperId(id);
    }

}


