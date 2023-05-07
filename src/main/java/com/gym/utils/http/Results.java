package com.gym.utils.http;

/**
 * @Author: 71made
 * @Date: 2023/05/02 23:25
 * @ProductName: IntelliJ IDEA
 * @Description:
 */
public final class Results {
    private Results() {}

    //
    // 业务调用成功
    // ----------------------------------------------------------------------------------------------------
    public static Result success() {
        return new Result();
    }

    public static Result success(String msg) {
        return new Result(true, Status.SUCCESS.getCode(), msg);
    }

    public static Result success(String code, String msg) {
        return new Result(true, code, msg);
    }

    public static Result successWithStatus(Integer status) {
        return new Result(true, status);
    }

    public static Result successWithStatus(Integer status, String msg) {
        return new Result(true, status, Status.SUCCESS.getCode(), msg);
    }

    public static Result successWithStatus(Status status, String msg) {
        return new Result(true, status.getStatus(), status.getCode(), msg);
    }

    public static Result successWithStatus(Status status) {
        return new Result(true, status.getStatus(), status.getCode(), status.getMsg());
    }

    public static Result successWithData(Object data) {
        return new Result(true, Status.SUCCESS.getCode(), Status.SUCCESS.getMsg(), data);
    }

    public static Result successWithData(String msg, Object data) {
        return new Result(true, Status.SUCCESS.getCode(), msg, data);
    }

    public static Result successWithData(Status status, Object data) {
        return new Result(true, status.getStatus(), status.getCode(), status.getMsg(), data);
    }

    public static Result successWithData(Status status, String msg, Object data) {
        return new Result(true, status.getStatus(), status.getCode(), msg, data);
    }

    public static Result successWithData(String code, String msg, Object data) {
        return new Result(true, code, msg, data);
    }

    //
    // 业务调用失败
    // ----------------------------------------------------------------------------------------------------
    public static Result failure() {
        return new Result(false);
    }

    public static Result failure(String msg) {
        return new Result(false, null, msg);
    }

    public static Result failure(String code, String msg) {
        return new Result(false, code, msg);
    }

    public static Result failureWithStatus(Integer status) {
        return new Result(false, status);
    }

    public static Result failureWithStatus(Status status, String msg) {
        return new Result(false, status.getStatus(), status.getCode(), msg);
    }

    public static Result failureWithStatus(Integer status, String msg) {
        return new Result(false, status, Status.FAILURE.getCode(), msg);
    }

    public static Result failureWithData(Object data) {
        return new Result(false, Status.FAILURE.getCode(), Status.FAILURE.getMsg(), data);
    }

    public static Result failureWithStatus(Status status) {
        return new Result(false, status.getStatus(), status.getCode(), status.getMsg());
    }

    public static Result failureWithData(Object data, String msg) {
        return new Result(false, Status.FAILURE.getCode(), msg, data);
    }

    public static Result failureWithData(Status status, Object data) {
        return new Result(false, status.getStatus(), status.getCode(), status.getMsg(), data);
    }
    public static Result failureWithData(Status status, String msg, Object data) {
        return new Result(false, status.getStatus(), status.getCode(), msg, data);
    }

    public static Result failureWithData(String code, String msg, Object data) {
        return new Result(false, code, msg, data);
    }


    //
    // 封装响应状态
    // ----------------------------------------------------------------------------------------------------
    public enum Status {
        SUCCESS(Result.Status.SUCCESS, "SUCCESS"),
        FAILURE(Result.Status.ACCEPTED, "FAILURE"),
        BAD_REQUEST(Result.Status.BAD_REQUEST, "BAD REQUEST", "参数或者语法错误"),
        UNAUTHORIZED(Result.Status.UNAUTHORIZED, "UNAUTHORIZED", "认证失败"),
        LOGIN_ERROR(Result.Status.UNAUTHORIZED, "UNAUTHORIZED", "登陆失败, 账号或密码无效"),
        FORBIDDEN(Result.Status.FORBIDDEN, "FORBIDDEN", "权限不足, 禁止访问"),
        NOT_FOUND(Result.Status.NOT_FOUND, "NOT FOUND", "请求的资源不存在"),
        OPERATE_ERROR(Result.Status.BAD_METHOD, "BAD METHOD", "操作失败, 请求操作的资源不存在"),
        TIME_OUT(Result.Status.TIME_OUT, "TIME OUT", "请求超时"),
        SERVER_ERROR(Result.Status.ERROR, "ERROR", "服务器内部错误"),
        REGISTER_SUCCESS(Result.Status.SUCCESS, "SUCCESS", "帐号注册成功"),
        REGISTER_FAIL(Result.Status.ERROR, "ERROR", "帐号注册失败"),
        REGISTER_REPEAT(Result.Status.ACCEPTED, "REPEAT", "账号已被注册"),
        CAPTCHA_MISMATCH(Result.Status.UNAUTHORIZED, "MISMATCH", "验证码错误"),
        LOGIN_SUCCESS(Status.SUCCESS, "登录成功"),
        LOGIN_MISSING(Status.FAILURE, "暂未登录"),
        RECORD_NOT_EXIST(Status.FAILURE, "记录不存在"),
        ;

        /**
         * 返回码
         */
        private final String code;

        /**
         * 状态码
         */
        private final int status;

        /**
         * 状态说明
         */
        private final String msg;

        Status(int status, String code) {
            this.status = status;
            this.code = code;
            this.msg = null;
        }

        Status(int status, String code, String msg) {
            this.status = status;
            this.code = code;
            this.msg = msg;
        }
        Status(Status resultStatus, String msg) {
            this.status = resultStatus.getStatus();
            this.code = resultStatus.getCode();
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }
    }

}
