package com.github.marsor.mtaxi.common.http;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface IResponse {

    // 状态码
    int getCode();

    // 数据体
    String getData();
}
