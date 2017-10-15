package com.github.marsor.mtaxi.main.view;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.marsor.mtaxi.MTaxiApplication;
import com.github.marsor.mtaxi.R;
import com.github.marsor.mtaxi.account.model.AccountManagerImpl;
import com.github.marsor.mtaxi.account.model.IAccountManager;
import com.github.marsor.mtaxi.account.view.PhoneInputDialog;
import com.github.marsor.mtaxi.common.http.IHttpClient;
import com.github.marsor.mtaxi.common.http.impl.OkHttpClientImpl;
import com.github.marsor.mtaxi.common.storage.SharedPreferenceDao;
import com.github.marsor.mtaxi.common.util.ToastUtil;
import com.github.marsor.mtaxi.main.presenter.IMainPresenter;
import com.github.marsor.mtaxi.main.presenter.MainPresenterImpl;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements IMainView {

    private final static String TAG = "MainActivity";
    private IMainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IHttpClient httpClient = new OkHttpClientImpl();
        SharedPreferenceDao dao =
                new SharedPreferenceDao(MTaxiApplication.getInstance(),
                        SharedPreferenceDao.FILE_ACCOUNT);
        IAccountManager manager = new AccountManagerImpl(httpClient, dao);
        mPresenter = new MainPresenterImpl(this, manager);
        mPresenter.loginByToken();
        readPhoneStateWithCheck();
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void readPhoneState() {

    }

    private void readPhoneStateWithCheck() {
        MainActivityPermissionsDispatcher.readPhoneStateWithPermissionCheck(this);
    }

    /**
     * 自动登录成功
     */

    @Override
    public void showLoginSuc() {
        ToastUtil.show(this, getString(R.string.login_suc));
    }

    /**
     * 显示 loading
     */
    @Override
    public void showLoading() {
        // TODO: 17/5/14   显示加载框
    }

    /**
     * 错误处理
     *
     * @param code
     * @param msg
     */

    @Override
    public void showError(int code, String msg) {
        switch (code) {
            case IAccountManager.TOKEN_INVALID:
                // 登录过期
                ToastUtil.show(this, getString(R.string.token_invalid));
                showPhoneInputDialog();
                break;
            case IAccountManager.SERVER_FAIL:
                // 服务器错误
                showPhoneInputDialog();
                break;

        }
    }

    /**
     * 显示手机输入框
     */
    private void showPhoneInputDialog() {
        PhoneInputDialog dialog = new PhoneInputDialog(this);
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
