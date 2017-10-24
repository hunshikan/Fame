package com.zbw.fame.controller;

import com.zbw.fame.exception.TipException;
import com.zbw.fame.util.ErrorCode;
import com.zbw.fame.util.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 常见异常处理并返回相应错误码
 * SpringMVC自定义异常对应的status code
 * Exception                               HTTP Status Code
 * ConversionNotSupportedException         500 (Internal Server Error)
 * HttpMessageNotWritableException         500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 * NoSuchRequestHandlingMethodException    404 (Not Found)
 * TypeMismatchException                   400 (Bad Request)
 * HttpMessageNotReadableException         400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 */
/**
 * 全局异常处理 Controller
 *
 * @author zbw
 * @create 2017/8/30 12:25
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    /**
     * Tip异常返回
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = TipException.class)
    public RestResponse tipErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return RestResponse.fail(e.getMessage());
    }

    /**
     * 运行时异常
     *
     * @param req
     * @param rep
     * @param re
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public RestResponse runtimeExceptionHandler(HttpServletRequest req, HttpServletResponse rep, RuntimeException re) {
        logger.error("---RuntimeException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), re.getMessage());
        re.printStackTrace();
        rep.setStatus(ErrorCode.RUNTIME.getCode());
        return RestResponse.fail(ErrorCode.RUNTIME.getCode(), ErrorCode.RUNTIME.getMsg());
    }

    /**
     * 空指针异常
     *
     * @param req
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public RestResponse nullPointerExceptionHandler(HttpServletRequest req, HttpServletResponse rep, NullPointerException ex) {
        logger.error("---NullPointerException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCode.NULL_POINTER.getCode());
        return RestResponse.fail(ErrorCode.NULL_POINTER.getCode(), ErrorCode.NULL_POINTER.getMsg());
    }

    /**
     * 类型转换异常
     *
     * @param req
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    public RestResponse classCastExceptionHandler(HttpServletRequest req, HttpServletResponse rep, ClassCastException ex) {
        logger.error("---classCastException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCode.CLASS_CAST.getCode());
        return RestResponse.fail(ErrorCode.CLASS_CAST.getCode(), ErrorCode.CLASS_CAST.getMsg());
    }

    /**
     * IO异常
     *
     * @param req
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler(IOException.class)
    public RestResponse classCastExceptionHandler(HttpServletRequest req, HttpServletResponse rep, IOException ex) {
        logger.error("---classCastException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCode.IO.getCode());
        return RestResponse.fail(ErrorCode.IO.getCode(), ErrorCode.IO.getMsg());
    }

    /**
     * 未知方法异常
     *
     * @param req
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public RestResponse noSuchMethodExceptionHandler(HttpServletRequest req, HttpServletResponse rep, NoSuchMethodException ex) {
        logger.error("---noSuchMethodException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCode.NO_SUCH_METHOD.getCode());
        return RestResponse.fail(ErrorCode.NO_SUCH_METHOD.getCode(), ErrorCode.NO_SUCH_METHOD.getMsg());
    }

    /**
     * 数组越界异常
     *
     * @param req
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public RestResponse indexOutOfBoundsExceptionHandler(HttpServletRequest req, HttpServletResponse rep, IndexOutOfBoundsException ex) {
        logger.error("---indexOutOfBoundsException Handler---Host {}, invokes url {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage());
        ex.printStackTrace();
        rep.setStatus(ErrorCode.INDEX_OUTOF_BOUNDS.getCode());
        return RestResponse.fail(ErrorCode.INDEX_OUTOF_BOUNDS.getCode(), ErrorCode.INDEX_OUTOF_BOUNDS.getMsg());
    }

    /**
     * 400相关异常
     *
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class, MissingServletRequestParameterException.class})
    public RestResponse request400(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCode.BAD_REQUEST.getCode());
        return RestResponse.fail(ErrorCode.BAD_REQUEST.getCode(), ex.getMessage());
    }

    /**
     * 405相关异常
     *
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public RestResponse request405(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCode.METHOD_BOT_ALLOWED.getCode());
        return RestResponse.fail(ErrorCode.METHOD_BOT_ALLOWED.getCode(), ex.getMessage());
    }

    /**
     * 406相关异常
     *
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public RestResponse request406(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCode.NOT_ACCEPTABLE.getCode());
        return RestResponse.fail(ErrorCode.NOT_ACCEPTABLE.getCode(), ex.getMessage());
    }

    /**
     * 500相关异常
     *
     * @param rep
     * @param ex
     * @return
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public RestResponse server500(HttpServletResponse rep, Exception ex) {
        rep.setStatus(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        return RestResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
    }

    /**
     * 全局异常返回
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public RestResponse defaultErrorHandler(HttpServletRequest req, HttpServletResponse rep, Exception e) throws Exception {
        logger.error("---DefaultException Handler---Host {}, invokes url {}, ERROR TYPE: {},  ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getClass(), e.getMessage());
        e.printStackTrace();
        return RestResponse.fail(e.getMessage());
    }
}
