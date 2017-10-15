package com.github.marsor.mtaxi.account.view;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface IView {

    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 显示错误
     */
    void showError(int Code, String msg);
}
