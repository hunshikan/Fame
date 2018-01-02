package com.zbw.fame.controller;

import com.github.pagehelper.Page;
import com.zbw.fame.dto.Archives;
import com.zbw.fame.dto.MetaDto;
import com.zbw.fame.dto.Pagination;
import com.zbw.fame.model.Articles;
import com.zbw.fame.service.ArticlesService;
import com.zbw.fame.service.MetasService;
import com.zbw.fame.util.FameConsts;
import com.zbw.fame.util.FameUtil;
import com.zbw.fame.dto.RestResponse;
import com.zbw.fame.util.Types;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 博客前台 Controller
 *
 * @author zbw
 * @create 2017/7/15 18:29
 */
@Api(value = "Home Controller", description = "博客前台相关操作的接口", tags = "博客前台接口")
@RestController
@RequestMapping("/api")
public class HomeController extends BaseController {

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private MetasService metasService;

    /**
     * 文章列表
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "获取文章列表", notes = "分页显示文章列表", response = Pagination.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limit", value = "每页显示数目", defaultValue = "10", dataTypeClass = Integer.class)
    })
    @GetMapping("article")
    public RestResponse home(@RequestParam(required = false, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = FameConsts.PAGE_SIZE) Integer limit) {
        Page<Articles> articles = articlesService.getContents(page, limit);
        for (Articles a : articles) {
            this.transformPreView(a);
        }
        return RestResponse.ok(new Pagination<Articles>(articles));
    }

    /**
     * 文章内容页
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取文章内容", notes = "获取文章详细内容", response = Articles.class)
    @ApiImplicitParam(name = "id", value = "文章id", required = true, example = "1", dataTypeClass = Integer.class)
    @GetMapping("article/{id}")
    public RestResponse content(@PathVariable Integer id) {
        Articles article = articlesService.get(id);
        if (null == article || Types.DRAFT.equals(article.getStatus())) {
            return this.error404();
        }
        this.transformContent(article);
        this.updateHits(article.getId(), article.getHits());
        return RestResponse.ok(article);
    }

    /**
     * 点击量添加
     *
     * @param articleId
     * @param hits
     */
    private void updateHits(Integer articleId, Integer hits) {
        Integer cHits = cache.get(FameConsts.CACHE_ARTICLE_HITS, articleId.toString());
        cHits = null == cHits ? 1 : cHits + 1;
        if (cHits >= FameConsts.CACHE_ARTICLE_HITS_SAVE) {
            Articles temp = new Articles();
            temp.setId(articleId);
            temp.setHits(hits + cHits);
            articlesService.updateArticle(temp);
            cache.put(FameConsts.CACHE_ARTICLE_HITS, articleId.toString(), 1);
        } else {
            cache.put(FameConsts.CACHE_ARTICLE_HITS, articleId.toString(), cHits);
        }
    }


    /**
     * 标签页
     *
     * @return
     */
    @ApiOperation(value = "获取标签列表", notes = "获取标签列表以及标签下的文章", response = MetaDto.class, responseContainer = "List")
    @GetMapping("tag")
    public RestResponse tag() {
        List<MetaDto> metaDtos = metasService.getMetaDtos(Types.TAG);
        return RestResponse.ok(metaDtos);
    }

    /**
     * 分类页
     *
     * @return
     */
    @ApiOperation(value = "获取分类列表", notes = "获取分类列表以及分类下的文章", response = MetaDto.class, responseContainer = "List")
    @GetMapping("/category")
    public RestResponse category() {
        List<MetaDto> metaDtos = metasService.getMetaDtos(Types.CATEGORY);
        return RestResponse.ok(metaDtos);
    }

    /**
     * 归档页
     *
     * @return
     */
    @ApiOperation(value = "获取归档列表", notes = "获取归档列表以及归档下的文章", response = Archives.class, responseContainer = "List")
    @GetMapping("archive")
    public RestResponse archive() {
        Integer maxLimit = 9999;
        List<Articles> articles = articlesService.getContents(1, maxLimit);
        List<Archives> archives = new ArrayList<>();
        String current = "";
        for (Articles article : articles) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(article.getCreated());
            String dateStr = cal.get(Calendar.YEAR) + "";
            if (dateStr.equals(current)) {
                Archives arc = archives.get(archives.size() - 1);
                arc.getArticles().add(article);
                arc.setCount(arc.getArticles().size());
            } else {
                current = dateStr;
                Archives arc = new Archives();
                arc.setDateStr(dateStr);
                arc.setCount(1);
                List<Articles> arts = new ArrayList<>();
                arts.add(article);
                arc.setArticles(arts);
                archives.add(arc);
            }
        }
        return RestResponse.ok(archives);
    }

    /**
     * 自定义页面
     *
     * @param title
     * @return
     */
    @ApiOperation(value = "获取自定义页面内容", notes = "获取自定义页面详细内容", response = Articles.class)
    @ApiImplicitParam(name = "title", value = "自定义页面title", required = true, example = "About", dataTypeClass = String.class)
    @GetMapping("page/{title}")
    public RestResponse page(@PathVariable String title) {
        Articles page = articlesService.getPage(title);
        if (null == page) {
            return error404();
        }
        transformContent(page);
        return RestResponse.ok(page);
    }


    /**
     * 文章内容转为html
     *
     * @param article
     */
    private void transformContent(Articles article) {
        String html = FameUtil.mdToHtml(article.getContent());
        article.setContent(html);
    }

    /**
     * 文章内容转为预览html
     *
     * @param article
     */
    private void transformPreView(Articles article) {
        String html = FameUtil.mdToHtml(FameUtil.getPreView(article.getContent()));
        article.setContent(html);
    }


}
