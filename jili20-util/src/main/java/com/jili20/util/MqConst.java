package com.jili20.util;

/**
 * RabbitMQ 常量
 *
 * @author bing_  @create 2022/3/11-11:12
 */
public class MqConst {

    /**
     * 构造函数私有化，外面不能 new
     */
    private MqConst() {}

    /**
     * 主题 - 系统通知
     * 1、交换机
     * 2、路由键
     * 3、消息队列
     */
    public static final String EXCHANGE_TOPIC_NOTICE = "exchange.topic.notice";
    public static final String ROUTING_NOTICE_ITEM = "routing.notice.item";
    public static final String QUEUE_NOTICE_ITEM = "queue.notice.item";


    /**
     * 主题 - 新增文章 - 编辑上传图片-新增文章图片表记录-type 0
     * 1、交换机
     * 2、路由键
     * 3、消息队列
     */
    public static final String EXCHANGE_TOPIC_PICTURE = "exchange.topic.picture";
    public static final String ROUTING_PICTURE_ITEM = "routing.picture.item";
    public static final String QUEUE_PICTURE_ITEM = "queue.picture.item";


    /**
     * 主题 - 新增文章 - 保存时-新增文章图片表数据-type 1
     * 1、交换机
     * 2、路由键
     * 3、消息队列
     */
    public static final String EXCHANGE_TOPIC_USE_PICTURE = "exchange.topic.use.picture";
    public static final String ROUTING_USE_PICTURE_ITEM = "routing.use.picture.item";
    public static final String QUEUE_USE_PICTURE_ITEM = "queue.use.picture.item";


    /**
     * 主题 - 编辑文章 - 更新文章图片 - type 1
     * 1、交换机
     * 2、路由键
     * 3、消息队列
     */
    public static final String EXCHANGE_TOPIC_UPDATE_PICTURE = "exchange.topic.update.picture";
    public static final String ROUTING_UPDATE_PICTURE_ITEM = "routing.update.picture.item";
    public static final String QUEUE_UPDATE_PICTURE_ITEM = "queue.update.picture.item";


    /**
     * 主题 - 删除文章 - 删除该文章所有相关的系统通知
     * 1、交换机
     * 2、路由键
     * 3、消息队列
     */
    public static final String EXCHANGE_TOPIC_DEL_ARTICLE = "exchange.topic.del.article";
    public static final String ROUTING_DEL_ARTICLE = "routing.del.article";
    public static final String QUEUE_DEL_ARTICLE = "queue.del.article";


    /**
     * 主题 - 删除留言或回复 - 删除相关的系统通知
     * 1、交换机
     * 2、路由键
     * 3、消息队列
     */
    public static final String EXCHANGE_TOPIC_DEL_COMMENT = "exchange.topic.del.comment";
    public static final String ROUTING_DEL_COMMENT = "routing.del.comment";
    public static final String QUEUE_DEL_COMMENT = "queue.del.comment";

}