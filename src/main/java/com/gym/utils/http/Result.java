package com.gym.utils.http;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @Author: 71made
 * @Date: 2023/05/02 23:28
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 2466394609815990909L;

    /**
     * 成功与否标志
     */
    private boolean success = true;
    /**
     * 返回状态码，为空则默认200. 前端需要拦截一些常见的状态码如403、404、500等
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
    /**
     * 业务编码，可用于前端处理多语言，不需要则不用返回编码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    /**
     * 相关消息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    /**
     * 相关数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;


    public Result() {
    }

    public Result(boolean success) {
        this.success = success;
        this.status = 200;
    }

    public Result(boolean success, Integer status) {
        this.success = success;
        this.status = null == status ? 200 : status;
    }

    public Result(boolean success, String code, String msg) {
        this(success);
        this.code = code;
        this.msg = msg;
    }

    public Result(boolean success, Integer status, String code, String msg) {
        this(success, status);
        this.code = code;
        this.msg = msg;
    }

    public Result(boolean success, String code, String msg, Object data) {
        this(success, code, msg);
        this.data = data;
    }

    public Result(boolean success, Integer status, String code, String msg, Object data) {
        this(success, status, code, msg);
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 常见响应状态码
     */
    public static final class Status {
        private Status() {}
        /**
         * 操作成功
         */
        public static final int SUCCESS = 200;

        /**
         * 对象创建成功
         */
        public static final int CREATED = 201;

        /**
         * 请求已经被接受
         */
        public static final int ACCEPTED = 202;

        /**
         * 操作已经执行成功，但是没有返回数据
         */
        public static final int NO_CONTENT = 204;

        /**
         * 资源已被移除
         */
        public static final int MOVED_PERM = 301;

        /**
         * 重定向
         */
        public static final int SEE_OTHER = 303;

        /**
         * 资源没有被修改
         */
        public static final int NOT_MODIFIED = 304;

        /**
         * 参数列表错误（缺少，格式不匹配）
         */
        public static final int BAD_REQUEST = 400;

        /**
         * 未授权
         */
        public static final int UNAUTHORIZED = 401;

        /**
         * 访问受限，授权过期
         */
        public static final int FORBIDDEN = 403;

        /**
         * 资源，服务未找到
         */
        public static final int NOT_FOUND = 404;

        /**
         * 不允许的http方法
         */
        public static final int BAD_METHOD = 405;
        /**
         * 请求超时
         */
        public static final int TIME_OUT = 408;

        /**
         * 资源冲突，或者资源被锁
         */
        public static final int CONFLICT = 409;

        /**
         * 不支持的数据，媒体类型
         */
        public static final int UNSUPPORTED_TYPE = 415;

        /**
         * 系统内部错误
         */
        public static final int ERROR = 500;

        /**
         * 接口未实现
         */
        public static final int NOT_IMPLEMENTED = 501;
    }
}
