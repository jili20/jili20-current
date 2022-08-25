package com.jili20.result;

import lombok.Getter;

/**
 * 全局统一返回状态码
 *
 * @author bing_  @create 2022/1/6-17:39
 */
@Getter
public enum ResponseEnum {

    // 请求响应
    SUCCESS(200, "请求成功"),
    FAIL(400, "服务器内部错误"),
    UN_KNOW_EXCEPTION(401, "系统未知异常"),
    VALIDATE_EXCEPTION(402, "参数格式校验失败"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    NEED_LOGIN_AGAIN(405, "您的身份已过期，请重新登录"),
    NEED_LOGIN(406, "您的身份已过期，请重新登录后再操作"),
    RUN_TIME_ERROR(407, "粗问题啦"),
    SYSTEM_ERROR(500, "出现错误"),
    LOGIN_ERROR(505, "认证失败，请查询登录"),
    PASSWORD_ERROR(505, "密码错误"),
    USER_ACCOUNT_STOP_ERROR(506, "此账号已被永久封禁"),

    // 请求参数错误
    REQUEST_ID_LENGTH_ERROR(100, "ID长度为19位数以内"),
    USER_ID_LENGTH_ERROR(100, "用户ID有误"),
    ENTITY_ID_LENGTH_ERROR(1001, "实体ID长度不能超过19位数"),
    ENTITY_USERID_ID_LENGTH_ERROR(1002, "实体用户ID长度不能超过19位数"),
    TYPE_ID_LENGTH_ERROR(1003, "实体类型长度为1个字符"),
    REQUEST_PARAMETER_LENGTH_ERROR(101, "最大支持上传2MB的图片"),
    REQUEST_TYPE_ERROR(102, "请求参数格式错误"),

    // 服务器错误
    BAD_SQL_GRAMMAR_ERROR(102, "sql 语法错误"),
    //SERVLET_ERROR(102, "servlet 请求异常"),
    SERVLET_ERROR(103, "请求异常"),
    UPLOAD_ERROR(104, "文件上传错误"),
    EXPORT_DATA_ERROR(105, "数据导出失败"),

    // 用户注册 - 参数校验
    USERNAME_NULL_ERROR(201, "用户名不能为空"),
    PHONE_NULL_ERROR(202, "手机号码不能为空"),
    EMAIL_NULL_ERROR(203, "邮箱地址不能为空"),
    EMAIL_EXIST_ERROR(204, "邮箱已被占用"),
    USERNAME_FORMAT_ERROR(205, "用户名长度为 1 至 20 个字符，支持中英文、数字、中或下划线"),
    USERNAME_LENGTH_ERROR(206, "用户名长度为 2 至 20 个字符"),
    PASSWORD_FORMAT_ERROR(207, "密码长度为 6 至 20 个字符，支持字母、数字、中或下划线"),
    NEW_PASSWORD_FORMAT_ERROR(2070, "新密码长度为 6 至 20 个字符，支持字母、数字、中或下划线"),
    CONFIRM_PASSWORD_FORMAT_ERROR(208, "确认密码 6 至 20 字符、支持字母、数字、中或下划线"),

    PHONE_ERROR(210, "手机号码格式不正确"),
    PHONE_EXIST_ERROR(211, "手机号已被注册"),
    CODE_NULL_ERROR(212, "验证码不能为空"),
    PASSWORD_NULL_ERROR(213, "密码不能为空"),
    CONFIRM_PASSWORD_NULL_ERROR(214, "确认密码不能为空"),
    CONFIRM_PASSWORD_NOT_DIFFERENT(215, "两次输入的密码不一致"),
    CODE_ERROR(216, "验证码错误"),
    USERNAME_EXIST_ERROR(217, "该用户名已被注册"),

    // 用户修改资料
    NEW_USERNAME_OLD_USERNAME_IDENTICAL_ERROR(218, "新用户名不能和原用户名相同"),
    SIGN_NULL_ERROR(219, "个性签名不能为空"),
    SIGN_LENGTH_ERROR(220, "个性签名长度为 1 至 30 个字符"),
    STATUS_NULL_ERROR(221, "状态不能为空"),
    STATUS_LENGTH_ERROR(221, "状态长度为 1 个字符"),

    // 阿里云短信
    ALIYUN_RESPONSE_ERROR(17000, "阿里云短信服务响应失败"),
    // 业务限流
    ALIYUN_SMS_LIMIT_CONTROL_ERROR(17001, "短信发送过于频繁"),
    // 其他失败
    ALIYUN_SMS_ERROR(17002, "短信发送失败"),

    // 用户登录
    USERNAME_OR_PASSWORD_ERROR(220, "用户名或者密码错误"),
    // Jwt 认证过滤器
    USER_NO_LOGIN(221, "用户未登录"),
    // 获取用户权限列表
    USER_ID_NULL_ERROR(222, "用户ID不能为空"),

    // 用户访问需要登录认证的接口
    ACCOUNT_NOT_LOGIN(223, "请登录后再操作"),

    // 用户手机短信找回密码
    PHONE_NO_REGISTER_ERROR(240, "该手机号未注册"),
    NEW_PASSWORD_NULL_ERROR(241, "新密码不能为空"),
    USER_NO_EXIST_ERROR(242, "该用户不存在"),

    // 用户邮箱修改密码
    EMAIL_NO_EXIST_ERROR(250, "系统不存在此邮箱"),
    EMAIL_ADDRESS_ERROR(251, "邮箱地址不正确"),

    // 权限菜单
    // 判空（有默认值不用判断）
    MENU_NAME_NULL_ERROR(261, "菜单名不能为空"),
    MENU_CODE_NULL_ERROR(262, "授权标识符不能为空"),
    MENU_REMARK_NULL_ERROR(263, "菜单备注不能为空"),
    // 校验长度
    MENU_PARENT_ID_LENGTH_ERROR(264, "父菜单ID为 1 至 3 位数"),
    MENU_NAME_LENGTH_ERROR(265, "菜单名长度为2-10个字符"),
    MENU_TYPE_LENGTH_ERROR(266, "菜单类型为 1 个字符"),
    MENU_CODE_LENGTH_ERROR(267, "授权标识符长度为 2 至 20 个字符"),
    MENU_SORT_LENGTH_ERROR(268, "排序为数字 1 至 1000"),
    MENU_REMARK_LENGTH_ERROR(269, "备注长度为 2 至 50 个字符"),
    // 编辑菜单
    MENU_NULL_ERROR(271, "菜单不存在"),

    // 角色
    ROLE_NAME_NULL_ERROR(280, "角色名称不能为空"),
    ROLE_DESCRIPTION_NULL_ERROR(281, "角色简介不能为空"),
    // 校验长度
    ROLE_NAME_LENGTH_ERROR(282, "角色名称长度为 2 至 10 个字符"),
    ROLE_DESCRIPTION_LENGTH_ERROR(282, "角色简介长度为 2 至 50 个字符"),

    // 分类
    CATEGORY_NAME_NULL_ERROR(290, "分类名称不能为空"),
    // 校验长度
    CATEGORY_NAME_LENGTH_ERROR(291, "分类名称长度为 2 至 10 个字符"),
    // 是否存在
    CATEGORY_NAME_EXIST_ERROR(292, "分类名称已存在"),
    CATEGORY_EXIST_ERROR(293, "该分类不存在"),

    // 校验用户是否被禁言
    USER_STATUS_FORBIDDEN_ARTICLE(294, "抱歉，您已被禁言，不能发布帖子"),
    USER_STATUS_FORBIDDEN_COMMENT(295, "抱歉，您已被禁言，不能发表留言"),

    // 文章
    ARTICLE_TITLE_NULL_ERROR(300, "文章标题不能为空"),
    ARTICLE_CONTENT_NULL_ERROR(301, "文章内容能为空"),
    ARTICLE_REFUSE_NULL_ERROR(301, "可否转载不能为空"),
    ARTICLE_CATEGORY_ID_NULL_ERROR(304, "请选择二级分类"),
    ARTICLE_CATEGORY_PID_NULL_ERROR(305, "请选择一级分类"),
    ARTICLE_ID_NULL_ERROR(306, "文章ID不能为空"),
    ARTICLE_CATEGORY_NAME_NULL_ERROR(307, "分类名不能为空"),
    // 判断是否存在
    ARTICLE_TITLE_EXIST_ERROR(308, "文章标题已存在"),
    ARTICLE_NULL_ERROR(309, "该文章不存在"),
    // 限制长度
    ARTICLE_TITLE_LENGTH_ERROR(310, "文章标题长度为 2 至 30 个字符"),
    ARTICLE_CONTENT_LENGTH_ERROR(311, "内容长度为 10 至 2 万个字符"),
    ARTICLE_MASTER_URL_LENGTH_ERROR(312, "经历主人头像格式错误"),
    ARTICLE_MASTER_NAME_LENGTH_ERROR(313, "经历主人名称长度为 1 至 20 个字符"),
    ARTICLE_CATEGORY_ID_LENGTH_ERROR(314, "分类ID长度为数值 1 至 65535"),
    ARTICLE_CATEGORY_PID_LENGTH_ERROR(315, "父分类ID长度为数值 1 至 65535"),
    ARTICLE_ORIGINAL_LENGTH_ERROR(316, "是否原创长度为 1 个字符"),
    ARTICLE_REFUSE_LENGTH_ERROR(317, "可否转载长度为 1 个字符"),
    ARTICLE_PUBLISH_LENGTH_ERROR(318, "发布状态长度为 1 个字符"),

    // 上传图片至阿里云 OSS
    IMG_MAX_SIZE_ERROR(320, "最大支持上传2MB的图片"),

    // 留言
    COMMENT_CONTENT_NULL_ERROR(321, "留言内容不能为空"),
    REPLY_CONTENT_NULL_ERROR(321, "回复内容不能为空"),
    COMMENT_TO_USER_ID_NULL_ERROR(322, "回复的目标用户ID不能为空"),
    COMMENT_ROOT_ID_NULL_ERROR(323, "根回复ID不能为空"),
    COMMENT_ID_NULL_ERROR(324, "留言ID不能为空"),
    // 限制长度
    COMMENT_CONTENT_LENGTH_ERROR(325, "留言的长度为1至1000个字符"),
    COMMENT_ID_LENGTH_ERROR(326, "留言ID的长度为 1 至 19 位数"),
    COMMENT_ARTICLE_ID_LENGTH_ERROR(329, "文章ID长度为 1 至 19 位数"),

    // 回复
    REPLY_ID_LENGTH_ERROR(323, "回复ID长度为 1 至 19 位数"),
    REPLY_ROOT_ID_LENGTH_ERROR(323, "根回复ID长度为 1 至 19 位数"),
    REPLY_TO_USER_ID_LENGTH_ERROR(322, "回复目标用户ID长度为 1 至 19 位数"),
    REPLY_CONTENT_LENGTH_ERROR(325, "回复内容的长度为 1 至 1000 个字符"),

    // 点赞
    TYPE_NELL_ERROR(330, "类型不能为空"),
    THUMB_ENTITY_ID_NELL_ERROR(331, "点赞的实体ID不能为空"),
    THUMB_ENTITY_USER_ID_NELL_ERROR(332, "点赞的实体用户ID不能为空"),

    // 私信
    MESSAGE_TO_NAME_NULL_ERROR(340, "收信人名不能为空"),
    MESSAGE_CONTENT_NULL_ERROR(341, "私信内容不能为空"),
    MESSAGE_CONVERSATION_ID_NULL_ERROR(342, "会话ID不能为空"),
    MESSAGE_TARGET_NULL_ERROR(343, "收信人不存在本系统"),
    // 限制长度
    MESSAGE_CONTENT_LENGTH_ERROR(344, "私信内容为 1 至 500 个字符"),
    MESSAGE_CONVERSATION_ID_LENGTH_ERROR(345, "会话ID长度为 40 个字符以内"),

    // 系统通知
    NOTICE_TYPE_NULL_ERROR(346, "通知类型不能为空"),
    NOTICE_TYPE_LENGTH_ERROR(346, "通知类型长度为 1 个字符"),

    // 轮播图，判空
    LOOP_ID_NULL_ERROR(350, "轮播图ID不能为空"),
    LOOP_LOOPER_URL_NULL_ERROR(351, "图片地址不能为空"),
    LOOP_TITLE_NULL_ERROR(352, "轮播图标题不能为空"),
    LOOP_LOOPER_LINK_NULL_ERROR(353, "跳转链接地址不能为空"),
    LOOP_POSITION_NULL_ERROR(354, "轮播图位置不能为空"),
    LOOP_PAY_NUMBER_NULL_ERROR(355, "赞助单号不能为空"),
    LOOP_AMOUNT_NULL_ERROR(356, "金额不能为空"),
    // 限制长度
    LOOP_TITLE_LENGTH_ERROR(357, "标题长度为 1 至 30 个字符"),
    LOOP_LOOPER_URL_LENGTH_ERROR(358, "图片地址长度为 10 至 255 个字符"),
    LOOP_LOOPER_LINK_LENGTH_ERROR(359, "跳转链接地址长度为 10 至 255 个字符"),
    LOOP_POSITION_LENGTH_ERROR(360, "位置长度为1个字符"),
    LOOP_PAY_NUMBER_LENGTH_ERROR(362, "赞助单号长度为 6 至 60 个数字"),
    LOOP_AMOUNT_LENGTH_ERROR(363, "金额长度为 1 至 5 位数"),
    // 已存在
    LOOP_PAY_NUMBER_EXIST_ERROR(365, "赞助单号已存在"),

    // 轮播图背景图
    BACKGROUND_URL_NULL_ERROR(366, "背景图片地址不能为空"),
    BACKGROUND_URL_LENGTH_ERROR(367, "图片地址长度为 10 至 255 个字符"),

    // 统一
    ID_NULL_ERROR(370, "ID不能为空"),
    ENTITY_USER_ID_NULL_ERROR(3701, "实体用户ID不能为空"),
    CONTENT_NULL_ERROR(371, "内容不能为空"),
    CONTENT_LENGTH_ERROR(372, "内容长度为 1 至 255 个字符"),
    AUTHOR_LENGTH_ERROR(373, "作者姓名长度为 1 至 20 个字符"),
    AUTHOR_ID_NULL_ERROR(373, "作者ID不能为空"),

    // 投放诗语
    AMOUNT_NULL_ERROR(380, "赞助金额不能为空"),
    PAY_NUMBER_NULL_ERROR(380, "赞助账单号不能为空"),
    MESSAGE_NULL_ERROR(380, "赞助致辞不能为空"),
    ONE_AMOUNT_LENGTH_ERROR(3886, "赞助金额为 1 至 4 位数"),
    // 限制长度
    AMOUNT_LENGTH_ERROR(380, "赞助金额为 1 位数"),
    PAY_NUMBER_LENGTH_ERROR(380, "赞助单号长度为 6 至 50 个数字"),
    MESSAGE_LENGTH_ERROR(380, "赞助致辞长度为 1 至 50 个字符"),
    LINK_LENGTH_ERROR(381,"链接地址为 10 至 50 个字符"),

    // VIP赞助人 限制长度
    SPONSOR_AMOUNT_LENGTH_ERROR(391, "赞助金额为 3 至 8 位数字"),

    // 举报
    REPORT_ENTITY_TYPE_NULL_ERROR(392, "举报类型不能为空"),
    REPORT_ENTITY_ID_NULL_ERROR(393, "举报目标ID不能为空"),
    REPORT_TYPE_NULL_ERROR(394, "举报原因不能为空"),
    REPORT_ENTITY_USER_ID_NULL_ERROR(3941, "举报的目标用户ID不能为空"),
    RESULT_NULL_ERROR(395, "处理结果说明不能为空"),
    // 限制长度
    REPORT_ENTITY_TYPE_LENGTH_ERROR(392, "举报类型长度为 1 个字符"),
    REPORT_ENTITY_ID_LENGTH_ERROR(393, "举报目标ID长度不能超过 19 位数"),
    REPORT_ENTITY_USER_ID_LENGTH_ERROR(393, "举报目标用户ID长度不能超过 19 位数"),
    REPORT_TYPE_LENGTH_ERROR(394, "举报原因不能为空"),
    REPORT_CONTENT_LENGTH_ERROR(395, "举报内容长度为 1 至 1000 个字符"),
    REPORT_DESCRIPTION_LENGTH_ERROR(396, "补充说明长度为 10 至 500 个字符"),
    RESULT_LENGTH_ERROR(3961, "处理结果说明长度为 10 至 2554 个字符"),
    STATUS_MUST_ONE(3962, "请选择状态为已处理"),

    // 公共
    ENTITY_TYPE_NULL_ERROR(397, "类型不能为空"),
    AWARD_AMOUNT_NULL_ERROR(398, "金额不能为空"),
    // 限制长度
    ENTITY_TYPE_LENGTH_ERROR(397, "类型长度为 1 个字符"),
    AWARD_AMOUNT_LENGTH_ERROR(398, "奖励金额为 1 至 5 位数字"),
    AWARD_PAY_NUMBER_LENGTH_ERROR(399, "奖励账单号为 6 至 50 个数字"),

    // 修改头像和赞赏码
    AVATAR_NULL_ERROR(3990, "头像不能为空"),
    AVATAR_LENGTH_ERROR(3991, "头像长度为 10 至 255 个字符"),
    REWARD_URL_NULL_ERROR(3992, "赞赏码不能为空"),
    REWARD_LENGTH_NULL_ERROR(3993, "赞赏码长度为 10 至 255 个字符"),

    // 关注
    FOLLOW_USER_ID_NULL_ERROR(410, "关注人ID不能为空"),

    // 公告
    ANNOUNCEMENT_CONTENT_LENGTH_ERROR(4001, "公告内容长度为 10 至 255 个字符"),
    ANNOUNCEMENT_NULL_ERROR(4002, "该公告不存在");


    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}