package com.github.marsor.mtaxi.account.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dalimao.corelibrary.VerificationCodeInput;
import com.github.marsor.mtaxi.R;
import com.github.marsor.mtaxi.common.http.IHttpClient;
import com.github.marsor.mtaxi.common.http.IRequest;
import com.github.marsor.mtaxi.common.http.IResponse;
import com.github.marsor.mtaxi.common.http.api.API;
import com.github.marsor.mtaxi.common.http.biz.BaseBizResponse;
import com.github.marsor.mtaxi.common.http.impl.BaseRequest;
import com.github.marsor.mtaxi.common.http.impl.BaseResponse;
import com.github.marsor.mtaxi.common.http.impl.OkHttpClientImpl;
import com.github.marsor.mtaxi.common.util.ToastUtil;
import com.github.marsor.mtaxi.main.view.MainActivity;
import com.google.gson.Gson;

import java.lang.ref.SoftReference;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class SmsCodeDialog extends Dialog {

    private static final String TAG = "SmsCodeDialog";
    private static final int SMS_SEND_SUC = 1;
    private static final int SMS_SEND_FAIL = -1;
    private static final int SMS_CHECK_SUC = 2;
    private static final int SMS_CHECK_FAIL = -2;

    private String mPhone;
    private Button mResentBtn;
    private VerificationCodeInput mVerificationInput;
    private View mLoading;
    private View mErrorView;
    private TextView mPhoneTv;
    private MainActivity mainActivity;
    private IHttpClient mHttpClient;
    private MyHandler mHandler;

    /**
     * 验证码倒计时
     */
    private CountDownTimer mCountDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

            mResentBtn.setEnabled(false);
            mResentBtn.setText(String.format(getContext()
                    .getString(R.string.after_time_resend,
                            millisUntilFinished / 1000)));
        }

        @Override
        public void onFinish() {
            mResentBtn.setEnabled(true);
            mResentBtn.setText(getContext().getString(R.string.resend));
            cancel();
        }
    };

    static class MyHandler extends Handler {
        SoftReference<SmsCodeDialog> codeDialogRef;

        public MyHandler(SmsCodeDialog codeDialog) {
            codeDialogRef = new SoftReference<>(codeDialog);
        }

        @Override
        public void handleMessage(Message msg) {
            SmsCodeDialog dialog = codeDialogRef.get();
            if (dialog == null) {
                return;
            }
            switch (msg.what){
                case SmsCodeDialog.SMS_SEND_SUC:
                    dialog.mCountDownTimer.start();
                    break;
                case SmsCodeDialog.SMS_SEND_FAIL:
                    ToastUtil.show(dialog.getContext(),dialog.getContext().getString(R.string.sms_send_fail));
                    break;
                case SmsCodeDialog.SMS_CHECK_SUC:
                    break;
                case SmsCodeDialog.SMS_CHECK_FAIL:
                    break;
            }
        }
    }

    public SmsCodeDialog(Context context, String phone) {
        this(context, R.style.Dialog);
        // 上一个界面传来的手机号
        this.mPhone = phone;
        mHttpClient = new OkHttpClientImpl();
        mHandler = new MyHandler(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_smscode_input, null);
        setContentView(root);
        mPhoneTv = (TextView) findViewById(R.id.phone);
        String template = getContext().getString(R.string.sending);
        mPhoneTv.setText(String.format(template, mPhone));
        mResentBtn = (Button) findViewById(R.id.btn_resend);
        mVerificationInput = (VerificationCodeInput) findViewById(R.id.verificationCodeInput);
        mLoading = findViewById(R.id.loading);
        mErrorView = findViewById(R.id.error);
        mErrorView.setVisibility(View.GONE);
        initListeners();
        requestSendSmsCode();
    }

    /**
     * 请求下发验证码
     */
    private void requestSendSmsCode() {
        new Thread() {
            @Override
            public void run() {
                String url = API.Config.getDomain() + API.GET_SMS_CODE;
                IRequest request = new BaseRequest(url);
                request.setBody("phone", mPhone);
                IResponse response = mHttpClient.get(request, false);
                Log.d(TAG, response.getData());
                if (response.getCode() == BaseResponse.STATE_OK) {
                    BaseBizResponse bizRes =
                            new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        mHandler.sendEmptyMessage(SMS_SEND_SUC);
                    } else {
                        mHandler.sendEmptyMessage(SMS_SEND_FAIL);
                    }
                } else {
                    mHandler.sendEmptyMessage(SMS_SEND_FAIL);
                }
            }
        }.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public SmsCodeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SmsCodeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initListeners() {

        //  关闭按钮组册监听器
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 重发验证码按钮注册监听器
        mResentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend();
            }
        });

        // 验证码输入完成监听器
        mVerificationInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String code) {

                commit(code);
            }
        });
    }

    /**
     * 提交验证码
     *
     * @param code
     */
    private void commit(final String code) {
        showLoading();

        new Thread() {
            @Override
            public void run() {
                String url = API.Config.getDomain() + API.CHECK_SMS_CODE;
                IRequest request = new BaseRequest(url);
                request.setBody("phone", mPhone);
                request.setBody("code",code);
                IResponse response = mHttpClient.get(request, false);
                Log.d(TAG, response.getData());
                if (response.getCode() == BaseResponse.STATE_OK) {
                    BaseBizResponse bizRes =
                            new Gson().fromJson(response.getData(), BaseBizResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        mHandler.sendEmptyMessage(SMS_CHECK_SUC);
                    } else {
                        mHandler.sendEmptyMessage(SMS_CHECK_FAIL);
                    }
                } else {
                    mHandler.sendEmptyMessage(SMS_CHECK_FAIL);
                }
            }
        }.start();
    }

    private void resend() {

        String template = getContext().getString(R.string.sending);
        mPhoneTv.setText(String.format(template, mPhone));

    }

    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);

    }

}
