package com.app.hellstore.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.RemoteException;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.app.hellstore.R;
import com.app.hellstore.ScreenUtils;

public class LaunchActivity extends AppCompatActivity {

    private Button bonusButton;

    private View rootView;

    double screenWidth;
    double screenHeight;
    SharedPreferences mSP;
    private InstallReferrerClient mReferrerClient;
    private String refer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            Intent intent = new Intent(LaunchActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://hellstore.net/referral/?from=DCP666");
            // intent.putExtra("url", "http://ok.ru");
            startActivity(intent);
            finish();



        mReferrerClient = InstallReferrerClient.newBuilder(this).build();
        mReferrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        ReferrerDetails response = null;
                        try {
                            response = mReferrerClient.getInstallReferrer();
                            refer = response.getInstallReferrer();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // todo
            }
        });


    }
}