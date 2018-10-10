package com.konkuk.dna.user;

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

import com.konkuk.dna.helpers.BaseActivity;
import com.konkuk.dna.helpers.InitHelpers;
import com.konkuk.dna.R;
import com.konkuk.dna.map.MapFragment;

public class UserSettingActivity extends BaseActivity {
    protected DrawerLayout menuDrawer;
    private MapFragment mapFragment;
    private SeekBar radiusSeekbar;
    private TextView radiusText;
    private SwitchCompat isAnonymity, isFindable;
    private int radius;
    private double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        init();
    }

    public void init() {
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        InitHelpers.initDrawer(this, menuDrawer, 2);

        radius = 500; // TODO 반경, 위치 초기값 설정해줘야 합니다!
        longitude = 127.07934279999995;
        latitude = 37.5407625;

        // TODO switch 메뉴들 기존 값으로 초기화해줘야 합니다.
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.chatMapFragment);
        isAnonymity = (SwitchCompat) findViewById(R.id.isAnonymity);
        isFindable = (SwitchCompat) findViewById(R.id.isFindable);

        radiusText = (TextView) findViewById(R.id.radiusText);
        radiusText.setText(radius +"");

        radiusSeekbar = (SeekBar) findViewById(R.id.radiusSeekbar);
        radiusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                radius = i;
                radiusText.setText(radius + "");
                mapFragment.updateRadiusCircle(longitude, latitude, radius);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                finish();
                break;

            case R.id.menuBtn: // 메뉴 버튼 클릭
                if (!menuDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    menuDrawer.openDrawer(Gravity.RIGHT);
                }
                break;

            case R.id.settingSaveBtn: // 저장 버튼 클릭
                Log.d("test", "저장 버튼 클릭");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.initMapCenter(longitude, latitude, radius);
    }
}
