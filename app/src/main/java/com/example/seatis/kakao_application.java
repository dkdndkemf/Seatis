package com.example.seatis;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class kakao_application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "0ee0994026f39146afc8e662ce6faab8");

    }
}
