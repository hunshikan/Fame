package com.zbw.fame.service;

import com.zbw.fame.dto.MetaDto;

import java.util.List;

/**
 * 属性 Service 接口
 *
 * @author zbw
 * @create 2017/8/28 23:32
 */
public interface MetasService {
    /**
     * 根据属性以及属性下的已发布文章
     *
     * @param type 属性类型
     * @return List<MetaDto>
     */
    List<MetaDto> getMetaDtos(String type);

    /**
     * 根据属性以及属性下的文章
     *
     * @param type 属性类型
     * @return List<MetaDto>
     */
    List<MetaDto> getMetaDto(String type);

    /**
     * 删除属性(同时删除关联文章的属性)
     *
     * @param name 属性名
     * @param type 属性类型
     * @return boolean
     */
    boolean deleteMeta(String name, String type);

    /**
     * 保存属性
     *
     * @param name 属性名
     * @param type 属性类型
     * @return boolean
     */
    boolean saveMeta(String name, String type);

    /**
     * 更新属性(同时更新关联文章的属性)
     *
     * @param id   属性id
     * @param name 属性名
     * @param type 属性类型
     * @return boolean
     */
    boolean updateMeta(Integer id, String name, String type);

    /**
     * 添加或者删除属性(同时添加或者删除关联文章的属性)
     *
     * @param names     属性名
     * @param type      属性类型
     * @param articleId 关联文章id
     * @return boolean
     */
    boolean saveOrRemoveMetas(String names, String type, Integer articleId);

}
