package com.zbw.fame.controller.admin;

import com.github.pagehelper.Page;
import com.zbw.fame.controller.BaseController;
import com.zbw.fame.dto.Pagination;
import com.zbw.fame.model.Articles;
import com.zbw.fame.model.Users;
import com.zbw.fame.service.ArticlesService;
import com.zbw.fame.service.LogsService;
import com.zbw.fame.util.FameConsts;
import com.zbw.fame.util.FameUtil;
import com.zbw.fame.dto.RestResponse;
import com.zbw.fame.util.Types;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 自定义页面管理 Controller
 *
 * @author zbw
 * @create 2017/10/17 12:28
 */
@Api(value = "Page Controller", description = "后台自定义页面管理接口", tags = "后台自定义页面管理")
@RestController
@RequestMapping("/api/admin/page")
public class PageController extends BaseController {

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private LogsService logsService;

    /**
     * 自定义页面列表
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "获取自定义页面列表", notes = "分页显示所有自定义页面列表列表", response = Pagination.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limit", value = "每页显示数目", defaultValue = "10", dataTypeClass = Integer.class)
    })
    @GetMapping
    public RestResponse index(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = FameConsts.PAGE_SIZE) Integer limit) {
        Page<Articles> pages = articlesService.getPages(page,limit);
        return RestResponse.ok(new Pagination<Articles>(pages));
    }

    /**
     * 获取自定义页面信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取自定义页面信息", notes = "获取自定义页面详细信息", response = Articles.class)
    @ApiImplicitParam(name = "id", value = "文章id", required = true, example = "1", dataTypeClass = Integer.class, paramType = "path")
    @GetMapping("{id}")
    public RestResponse showPage(@PathVariable Integer id) {
        Articles page = articlesService.getPage(id);
        if (null == page) {
            return this.error404();
        }
        return RestResponse.ok(page);
    }

    /**
     * 保存自定义页面
     *
     * @param id
     * @param title
     * @param content
     * @param status
     * @return
     */
    @ApiOperation(value = "新建或者修改自定义页面", notes = "新建或者修改自定义页面", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自定义页面id,新增自定义页面则为空", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "title", value = "自定义页面标题", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "content", value = "自定义页面内容(md格式)", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "status", value = "自定义页面状态", allowableValues = "[publish,draft]",defaultValue = "draft", dataTypeClass = String.class),
    })
    @PostMapping
    public RestResponse savePage(@RequestParam(value = "id", required = false) Integer id,
                                 @RequestParam(value = "title") String title,
                                 @RequestParam(value = "content") String content,
                                 @RequestParam(value = "status", defaultValue = Types.DRAFT) String status) {
        Users user = this.user();
        if (null == user) {
            return RestResponse.fail("未登陆，请先登陆");
        }
        Articles page = new Articles();
        if (!StringUtils.isEmpty(id)) {
            page.setId(id);
        }
        page.setTitle(title);
        page.setContent(content);
        page.setStatus(status);
        page.setAuthorId(user.getId());
        articlesService.savePage(page);
        return RestResponse.ok("保存文章成功");
    }

    /**
     * 删除自定义页面
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除自定义页面", notes = "根据id删除自定义页面", response = String.class)
    @ApiImplicitParam(name = "id", value = "自定义页面id", required = true, example = "1", dataTypeClass = Integer.class, paramType = "path")
    @DeleteMapping("{id}")
    public RestResponse deletePage(@PathVariable Integer id) {
        if (articlesService.deletePage(id)) {
            logsService.save(Types.LOG_ACTION_DELETE, "id:" + id, Types.LOG_MESSAGE_DELETE_PAGE, Types.LOG_TYPE_OPERATE, FameUtil.getIp());
            return RestResponse.ok("删除自定义页面成功");
        } else {
            return RestResponse.fail();
        }
    }
}
