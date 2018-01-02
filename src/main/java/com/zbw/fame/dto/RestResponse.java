package com.zbw.fame.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * json通用返回类
 *
 * @author zbw
 * @create 2017/7/12 19:59
 */
@ApiModel(description = "全局返回Model")
public class RestResponse<T> {

    @ApiModelProperty(value = "返回码", name = "code", dataType = "int", example = "0")
    private int code;
    @ApiModelProperty(value = "返回信息", name = "msg", dataType = "String", example = "修改文章成功")
    private String msg;
    @ApiModelProperty(value = "返回数据", name = "data", dataType = "object")
    private T data;
    @ApiModelProperty(value = "操作结果", name = "success", dataType = "boolean", example = "true")
    private boolean success;

    private RestResponse() {
    }

    private RestResponse(boolean success) {
        this.success = success;
    }

    private RestResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    private RestResponse(boolean success, T data, int code) {
        this.success = success;
        this.data = data;
        this.code = code;
    }

    private RestResponse(boolean success, int code) {
        this.success = success;
        this.code = code;
    }

    private RestResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }


    private RestResponse(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public static <T> RestResponse ok() {
        return new RestResponse(true);
    }

    public static <T> RestResponse ok(T data) {
        return new RestResponse<T>(true, data);
    }

    public static <T> RestResponse ok(T data, int code) {
        return new RestResponse<T>(true, data, code);
    }

    public static RestResponse fail() {
        return new RestResponse(false);
    }

    public static RestResponse fail(int code) {
        return new RestResponse(false, code);
    }

    public static RestResponse fail(String msg) {
        return new RestResponse(false, msg);
    }

    public static RestResponse fail(int code, String msg) {
        return new RestResponse(false, code, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
