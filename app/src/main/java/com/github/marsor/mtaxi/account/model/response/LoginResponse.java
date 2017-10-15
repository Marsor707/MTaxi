package com.github.marsor.mtaxi.account.model.response;

import com.github.marsor.mtaxi.common.http.biz.BaseBizResponse;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class LoginResponse extends BaseBizResponse {

    Account data;

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
}
