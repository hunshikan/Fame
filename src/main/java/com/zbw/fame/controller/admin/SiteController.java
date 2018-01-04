package com.zbw.fame.controller.admin;

import com.github.pagehelper.Page;
import com.zbw.fame.controller.BaseController;
import com.zbw.fame.dto.Pagination;
import com.zbw.fame.dto.RestResponse;
import com.zbw.fame.dto.SiteStatic;
import com.zbw.fame.model.Logs;
import com.zbw.fame.service.LogsService;
import com.zbw.fame.service.SiteService;
import com.zbw.fame.util.FameConsts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 网站信息 Controller
 *
 * @author zbw
 * @create 2017/10/12 20:27
 */
@Api(value = "Site Controller", description = "后台网站信息管理接口", tags = "后台网站信息管理")
@RestController
@RequestMapping("/api/admin/site")
public class SiteController extends BaseController {

    @Autowired
    private LogsService logsService;

    @Autowired
    private SiteService siteService;

    @ApiOperation(value = "获取日志列表", notes = "分页显示日志列表", response = Pagination.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面", defaultValue = "1", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limit", value = "每页显示数目", defaultValue = "10", dataTypeClass = Integer.class)
    })
    @GetMapping("logs")
    public RestResponse getLogs(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = FameConsts.PAGE_SIZE) Integer limit) {
        Page<Logs> logs = logsService.getLogs(page, limit);
        return RestResponse.ok(new Pagination<Logs>(logs));
    }

    /**
     * 获取网站设置缓存
     *
     * @return
     */
    @ApiOperation(value = "获取网站设置缓存", notes = "获取网站设置缓存(重启服务器后失效)", response = SiteStatic.class)
    @GetMapping("static")
    public RestResponse getSiteStatic() {
        return RestResponse.ok(siteService.getSiteStatic());
    }

    /**
     * 保存网站设置缓存
     *
     * @param title
     * @param description
     * @param keywords
     * @return
     */
    @ApiOperation(value = "保存网站设置缓存", notes = "保存网站设置缓存", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "网站title", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "description", value = "网站description", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "keywords", value = "网站keywords", required = true, dataTypeClass = String.class),
    })
    @PostMapping("static")
    public RestResponse getSiteStatic(@RequestParam String title, @RequestParam String description, @RequestParam String keywords) {
        siteService.saveSiteStatic(title, description, keywords);
        return RestResponse.ok();
    }


}
