package com.github.marsor.mtaxi.account.view;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ICreatePasswordDialogView extends IView {

    /**
     * 显示注册成功
     */
    void showRegisterSuc();

    /**
     * 显示登录成功
     */
    void showLoginSuc();

    /**
     * 显示密码为空
     */
    void showPasswordNull();

    /**
     * 显示两次输入密码不一样
     */
    void showPasswordNotEqual();
}
