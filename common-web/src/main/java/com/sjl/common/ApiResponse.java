package com.sjl.common;

/**
 * api接口统一响应
 *
 * @author Kelly
 * @version 1.0.0
 * @filename ApiResponse.java
 * @time 2019年4月23日 上午11:27:16
 * @copyright(C) 2019 song
 */
public class ApiResponse<T> extends BaseResponse {
    private T data;


    private ApiResponse(int code, String msg) {
        super(code, msg);
    }

    private ApiResponse(int code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResponse build(int code, String msg) {
        return new ApiResponse(code, msg);
    }

    public static ApiResponse build(int code, String msg, Object data) {
        return new ApiResponse(code, msg, data);
    }

    public static ApiResponse success(String msg) {
        return success(msg, null);
    }

    public static ApiResponse success(String msg, Object data) {
        return build(ErrorCode.ERROR_OK, msg, data);
    }

    public static ApiResponse empty(String msg) {
        return build(ErrorCode.ERROR_NO_DATA, msg);
    }

    public static ApiResponse fail(String msg) {
        return build(ErrorCode.ERROR_FAIL, msg);
    }


}
