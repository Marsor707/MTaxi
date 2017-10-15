package com.github.marsor.mtaxi.main.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.marsor.mtaxi.R;
import com.github.marsor.mtaxi.account.view.PhoneInputDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLoginState();
    }

    private void checkLoginState() {
        boolean tokenValid = false;
        if (!tokenValid) {
            showPhoneInputDialog();
        } else {

        }
    }

    private void showPhoneInputDialog() {
        PhoneInputDialog dialog = new PhoneInputDialog(this);
        dialog.show();
    }
}
