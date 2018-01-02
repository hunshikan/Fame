package com.zbw.fame.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.OrderBy;
import java.util.Date;

/**
 * 文章 Model
 *
 * @author zbw
 * @create 2017/7/8 9:29
 */
@ApiModel(description = "文章Article Entity")
public class Articles extends BaseEntity {

    /**
     * 内容标题
     */
    @ApiModelProperty(value = "文章标题", name = "title", example = "Hello Word")
    private String title;

    /**
     * 内容生成时间
     */
    @ApiModelProperty(value = "创建时间", name = "created")
    @OrderBy("desc")
    private Date created;

    /**
     * 内容修改时间
     */
    @ApiModelProperty(value = "修改时间", name = "modified")
    private Date modified;

    /**
     * 内容
     */
    @ApiModelProperty(value = "文章内容", name = "content", example = "This is Your first Post!")
    private String content;

    /**
     * 内容所属用户id
     */
    @ApiModelProperty(value = "内容所属用户id", name = "authorId", example = "1")
    private Integer authorId;

    /**
     * 点击量
     */
    @ApiModelProperty(value = "点击量", name = "hits", example = "100")
    private Integer hits;

    /**
     * 标签列表
     */
    @ApiModelProperty(value = "标签列表", name = "tags", example = "java,spring,mybatis")
    private String tags;

    /**
     * 文章分类
     */
    @ApiModelProperty(value = "文章分类", name = "category", example = "Fame博客系列")
    private String category;

    /**
     * 内容状态
     */
    @ApiModelProperty(value = "内容状态", name = "status", allowableValues = "publish,draft", example = "publish")
    private String status;

    /**
     * 内容类别
     */
    @ApiModelProperty(value = "内容类别", name = "type", allowableValues = "post,page", example = "post")
    private String type;

    /**
     * 是否允许评论
     */
    @ApiModelProperty(value = "是否允许评论", name = "allowComment", example = "true")
    private Boolean allowComment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "title='" + title + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", hits=" + hits +
                ", tags='" + tags + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", allowComment=" + allowComment +
                '}';
    }
}
