package com.zbw.fame.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 属性(分类和标签) Model
 *
 * @author zbw
 * @create 2017/8/28 23:04
 */
@ApiModel(description = "属性(分类和标签)Metas Entity")
public class Metas extends BaseEntity {

    /**
     * 属性名
     */
    @ApiModelProperty(value = "属性名", name = "name", example = "spring-boot")
    private String name;

    /**
     * 属性类型
     */
    @ApiModelProperty(value = "属性类型", name = "type", allowableValues = "tag,category", example = "tag")
    private String type;

    public Metas() {
    }

    public Metas(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Metas{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
