package com.zbw.fame.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 网站设置 Dto
 *
 * @author zbw
 * @create 2017/10/15 21:48
 */
@ApiModel(description = "网站设置SiteStatic Dto")
public class SiteStatic {

    /**
     * 网站title
     */
    @ApiModelProperty(value = "网站title", name = "title", example = "Fame Blog")
    private String title;

    /**
     * 网站描述
     */
    @ApiModelProperty(value = "网站描述", name = "description", example = "A Spring Boot Backend Blog")
    private String description;

    /**
     * 网站keywords
     */
    @ApiModelProperty(value = "网站keywords", name = "keywords", example = "spring-boot,vue,java")
    private String keywords;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
