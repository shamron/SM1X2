package com.shamron.sm1x2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment = new TipsFragment();
                    startNewFragment(fragment);
                    return true;
                case R.id.navigation_gs:
                    Fragment gsFragment = new GSFragment();
                    startNewFragment(gsFragment);
                    return true;
                case R.id.navigation_history:
                    Fragment historyFragment = new TipsHistoryFragment();
                    startNewFragment(historyFragment);
                    return true;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*init ads*/
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest.Builder builder = new  AdRequest.Builder().addTestDevice("8FDD82E63227F07AA718B4270284042A");
        mAdView.loadAd(builder.build());

        /*Set Default Fragment*/
        Fragment fragment = new TipsFragment();
        startNewFragment(fragment);

        /*Bottom Navigation*/
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void startNewFragment(Fragment fragment)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }

}
