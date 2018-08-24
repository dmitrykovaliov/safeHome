package com.dk.safehome1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dk.safehome1.db.RouterViewModel;
import com.dk.safehome1.entity.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dk on 6/4/18.
 */

public class RouterActivity extends AppCompatActivity {

    public static final int ENTER_ACTIVITY_REQUEST_CODE = 3;
    public static final String TAG = "routeractivity.log.tag";

    private RouterViewModel mRouterViewModel;

    long enterId;

    TextView mTitle1;
    TextView mSignal1;
    CheckBox mCheckBox1;

    TextView mTitle2;
    TextView mSignal2;
    CheckBox mCheckBox2;

    TextView mTitle3;
    TextView mSignal3;
    CheckBox mCheckBox3;

    TextView mTitle4;
    TextView mSignal4;
    CheckBox mCheckBox4;

    List<Router> routerList = new ArrayList<>();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);

        mTitle1 = findViewById(R.id.text_router_title1);
        mSignal1 = findViewById(R.id.text_router_signal1);
        mCheckBox1 = findViewById(R.id.checkbox_router1);

        mTitle2 = findViewById(R.id.text_router_title2);
        mSignal2 = findViewById(R.id.text_router_signal2);
        mCheckBox2 = findViewById(R.id.checkbox_router2);

        mTitle3 = findViewById(R.id.text_router_title3);
        mSignal3 = findViewById(R.id.text_router_signal3);
        mCheckBox3 = findViewById(R.id.checkbox_router3);

        mTitle4 = findViewById(R.id.text_router_title4);
        mSignal4 = findViewById(R.id.text_router_signal4);
        mCheckBox4 = findViewById(R.id.checkbox_router4);


        enterId = (long)getIntent().getSerializableExtra(EnterActivity.EXTRA_ENTER_ID);

        mRouterViewModel = ViewModelProviders.of(this).get(RouterViewModel.class);

        mRouterViewModel.findAllByEnterId(enterId).observe(this, new Observer<List<Router>>() {
            @Override
            public void onChanged(@Nullable final List<Router> routers) {

                   setRouter(routers);
            }
        });

        Router router1 = new Router();
        router1.setTitle("HUAWEI-xTfp");
        router1.setEnterId(enterId);
        router1.setMain(true);
        router1.setSignalStrength(37);
        mRouterViewModel.insert(router1);

        Router router2 = new Router();
        router2.setTitle("HUAWEI-sw2g");
        router2.setEnterId(enterId);
        router2.setMain(true);
        router2.setSignalStrength(42);
        mRouterViewModel.insert(router2);

        Router router3 = new Router();
        router3.setTitle("IL2");
        router3.setEnterId(enterId);
        router3.setSignalStrength(18);
        mRouterViewModel.insert(router3);

        Router router4 = new Router();
        router4.setTitle("KV-1");
        router4.setEnterId(enterId);
        router4.setSignalStrength(14);
        mRouterViewModel.insert(router4);

        Log.i(TAG, String.valueOf(routerList));


        mTitle1.setText(routerList.get(0).getTitle());
        mSignal1.setText(routerList.get(0).getSignalStrength());
        mCheckBox1.setChecked(routerList.get(0).isMain());

        mTitle2.setText(routerList.get(1).getTitle());
        mSignal2.setText(routerList.get(1).getSignalStrength());
        mCheckBox2.setChecked(routerList.get(1).isMain());

        mTitle3.setText(routerList.get(2).getTitle());
        mSignal3.setText(routerList.get(2).getSignalStrength());
        mCheckBox3.setChecked(routerList.get(2).isMain());

        mTitle4.setText(routerList.get(3).getTitle());
        mSignal4.setText(routerList.get(3).getSignalStrength());
        mCheckBox4.setChecked(routerList.get(3).isMain());
    }

    void setRouter(List<Router> routers){
        routerList = routers;
    }
}
