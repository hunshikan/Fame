package com.zbw.fame.dto;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页Bean
 *
 * @author zbw
 * @create 2017/10/23 11:44
 */
@ApiModel(description = "分页返回Model")
public class Pagination<T> {
    @ApiModelProperty(value = "当前页面", name = "pageNum", dataType = "int", example = "1")
    private int pageNum;
    @ApiModelProperty(value = "分页大小", name = "pageSize", dataType = "int", example = "1")
    private int pageSize;
    @ApiModelProperty(value = "总数量", name = "total", dataType = "long", example = "10")
    private long total;
    @ApiModelProperty(value = "分页数", name = "pages", dataType = "int", example = "3")
    private int pages;
    @ApiModelProperty(value = "查询是否包含count", name = "count", dataType = "boolean", example = "true")
    private boolean count;
    @ApiModelProperty(value = "排序字段", name = "orderBy", dataType = "String", example = "id")
    private String orderBy;
    @ApiModelProperty(value = "分页数据", name = "list", dataType = "List")
    private List<T> list;

    public Pagination() {
    }

    @SuppressWarnings("unchecked")
    public Pagination(Page page) {
        pageNum = page.getPageNum();
        pageSize = page.getPageSize();
        total = page.getTotal();
        pages = page.getPages();
        count = page.isCount();
        orderBy = page.getOrderBy();
        list = page.getResult();
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
