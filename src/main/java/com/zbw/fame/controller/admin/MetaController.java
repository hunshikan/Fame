package com.zbw.fame.controller.admin;

import com.zbw.fame.controller.BaseController;
import com.zbw.fame.dto.MetaDto;
import com.zbw.fame.dto.RestResponse;
import com.zbw.fame.service.MetasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 属性(标签和分类)管理 Controller
 *
 * @author zbw
 * @create 2017/8/28 23:16
 */
@Api(value = "Meta Controller", description = "后台属性管理接口", tags = "后台属性管理")
@RestController
@RequestMapping("/api/admin/meta")
public class MetaController extends BaseController {

    @Autowired
    private MetasService metasService;

    /**
     * 获取所有属性
     *
     * @return
     */
    @ApiOperation(value = "获取某个属性列表", notes = "获取某个属性列表", response = MetaDto.class, responseContainer = "List")
    @ApiImplicitParam(name = "type", value = "属性类型", required = true, allowableValues = "[tag,category]", example = "tag", dataTypeClass = String.class)
    @GetMapping
    public RestResponse getAll(@RequestParam String type) {
        return RestResponse.ok(metasService.getMetaDto(type));
    }

    /**
     * 根据name删除分类
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "根据name删除分类", notes = "根据name删除分类", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "属性名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "type", value = "属性类型", required = true, allowableValues = "[tag,category]", example = "tag", dataTypeClass = String.class)
    })
    @DeleteMapping
    public RestResponse deleteMeta(@RequestParam String name, @RequestParam String type) {
        if (metasService.deleteMeta(name, type)) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    /**
     * 添加一个分类
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "添加一个分类", notes = "添加一个分类", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "属性名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "type", value = "属性类型", required = true, allowableValues = "[tag,category]", example = "tag", dataTypeClass = String.class)
    })
    @PostMapping
    public RestResponse saveMeta(@RequestParam String name, @RequestParam String type) {
        if (metasService.saveMeta(name, type)) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    /**
     * 根据id修改分类
     *
     * @param id
     * @param name
     * @param type
     * @return
     */
    @ApiOperation(value = "根据id修改分类", notes = "根据id修改分类", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "属性id", required = true, example = "1", dataTypeClass = Integer.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "属性名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "type", value = "属性类型", required = true, allowableValues = "[tag,category]", example = "tag", dataTypeClass = String.class)
    })
    @PostMapping("{id}")
    public RestResponse updateMeta(@PathVariable Integer id, @RequestParam String name, @RequestParam String type) {
        if (metasService.updateMeta(id, name, type)) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }
}
