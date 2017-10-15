package com.github.marsor.mtaxi.account.presenter;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ISmsCodeDialogPresenter {

    /**
     * 请求下发验证码
     */
    void requestSendSmsCode(String phone);

    /**
     * 请求校验验证码
     */
    void requestCheckSmsCode(String phone, String smsCode);

    /**
     * 用户是否存在
     */
    void requestCheckUserExist(String phone);
}
