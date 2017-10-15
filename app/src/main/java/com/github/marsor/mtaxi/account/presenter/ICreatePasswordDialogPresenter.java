package com.github.marsor.mtaxi.account.presenter;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ICreatePasswordDialogPresenter {

    /**
     * 校验密码输入合法性
     */
    boolean checkPw(String pw, String pw1);

    /**
     * 提交注册
     */
    void requestRegister(String phone, String pw);

    /**
     * 登录
     */
    void requestLogin(String phone, String pw);
}
