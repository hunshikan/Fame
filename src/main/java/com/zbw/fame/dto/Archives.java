package com.zbw.fame.dto;

import com.zbw.fame.model.Articles;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 归档 Dto
 *
 * @author zbw
 * @create 2017/9/21 11:24
 */
@ApiModel(description = "归档Archives Dto")
public class Archives {

    @ApiModelProperty(value = "归档分类(年)", name = "dateStr", example = "2018")
    private String dateStr;

    @ApiModelProperty(value = "归档时间", name = "date")
    private Date date;

    @ApiModelProperty(value = "归档文章数目", name = "count", example = "8")
    private Integer count;

    @ApiModelProperty(value = "归档文章列表", name = "articles")
    private List<Articles> articles;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
        return "Archives{" +
                "dateStr='" + dateStr + '\'' +
                ", date=" + date +
                ", count='" + count + '\'' +
                ", articles=" + articles +
                '}';
    }
}
