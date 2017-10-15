package com.github.marsor.mtaxi.common.http.impl;

import com.github.marsor.mtaxi.common.http.IResponse;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class BaseResponse implements IResponse {

    public static final int STATE_UNKNOWN_ERROR = 100001;
    public static final int STATE_OK = 200;
    // 状态码
    private int code;
    // 响应数据
    private String data;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }
}
