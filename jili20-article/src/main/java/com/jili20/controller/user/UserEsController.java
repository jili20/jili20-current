package com.jili20.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jili20.mapper.ArticleMapper;
import com.jili20.result.Result;
import com.jili20.vo.api.EsArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author bing_  @create 2022/5/15-16:05
 */
@Api(tags = "User - ES数据管理管理")
@RestController
public class UserEsController {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ObjectMapper objectMapper;


    /**
     * 导入所有文章数据
     *
     * @throws IOException
     */
    @ApiOperation("导入文章数据进ES")
    @PostMapping("/es/import-data")
    public Result importEsData() throws IOException {

        final List<EsArticleVo> esArticleVos = articleMapper.selectArticleListToEs();

        for (final EsArticleVo vo : esArticleVos) {
            IndexRequest request = new IndexRequest("article");
            request.id(vo.getId() + "");
            request.source(objectMapper.writeValueAsString(vo), XContentType.JSON);
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        }
        return Result.ok().message("数据导入ES完成");
    }


    @ApiOperation("删除es索引")
    @DeleteMapping("/es/delete-index")
    public Result deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("article");
        restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return Result.ok().message("删除es索引完成");
    }

}
