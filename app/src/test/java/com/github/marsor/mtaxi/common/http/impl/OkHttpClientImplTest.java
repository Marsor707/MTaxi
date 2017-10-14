package com.github.marsor.mtaxi.common.http.impl;

import com.github.marsor.mtaxi.common.http.IHttpClient;
import com.github.marsor.mtaxi.common.http.IRequest;
import com.github.marsor.mtaxi.common.http.IResponse;
import com.github.marsor.mtaxi.common.http.api.API;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class OkHttpClientImplTest {
    IHttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        httpClient = new OkHttpClientImpl();
        API.Config.setDebug(false);
    }

    @Test
    public void get() throws Exception {
        String url = API.Config.getDomain() + API.TEST_GET;
        IRequest request = new BaseRequest(url);
        request.setBody("uid", "123456");
        request.setHeader("testHeader", "test header");
        IResponse response = httpClient.get(request, false);
        System.out.println("stateCode= " + response.getCode());
        System.out.println("body= " + response.getData());
    }

    @Test
    public void post() throws Exception {
        String url = API.Config.getDomain() + API.TEST_POST;
        IRequest request = new BaseRequest(url);
        request.setBody("uid", "123456");
        request.setHeader("testHeader", "test header");
        IResponse response = httpClient.post(request, false);
        System.out.println("stateCode= " + response.getCode());
        System.out.println("body= " + response.getData());
    }

}