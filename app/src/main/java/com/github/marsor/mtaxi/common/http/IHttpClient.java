package com.github.marsor.mtaxi.common.http;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

//HttpClient 抽象接口
public interface IHttpClient {

    IResponse get(IRequest request, boolean forceCache);

    IResponse post(IRequest request, boolean forceCache);
}
