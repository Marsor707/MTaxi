package com.github.marsor.mtaxi.account.view;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public interface ISmsCodeDialogView extends IView {

    /**
     * 显示倒计时
     */
    void showCountDownTimer();


    /**
     * 显示验证状态
     *
     * @param b
     */
    void showSmsCodeCheckState(boolean b);


    /**
     * 用户是否存在
     *
     * @param b
     */
    void showUserExist(boolean b);
}
