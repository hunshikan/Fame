package com.zbw.fame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zbw.fame.model.Articles;
import com.zbw.fame.model.Metas;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 属性 Dto
 *
 * @author zbw
 * @create 2017/8/30 15:15
 */
@ApiModel(description = "属性MetaDto Dto", parent = Metas.class)
public class MetaDto extends Metas {

    @ApiModelProperty(value = "属性文章数目", name = "count")
    private Integer count;

    @ApiModelProperty(value = "属性文章列表", name = "articles", allowEmptyValue = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Articles> articles;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "MetaDto{" +
                "count=" + count +
                ", articles=" + articles +
                '}';
    }
}
