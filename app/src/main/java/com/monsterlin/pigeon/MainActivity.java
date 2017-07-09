package com.monsterlin.pigeon;

import android.support.annotation.IdRes;
import android.view.View;

import com.monsterlin.pigeon.base.BaseActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * @author : monsterLin
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/5
 * @desc : MainActivity
 * @version: 1.0
 */
public class MainActivity extends BaseActivity {

    private BottomBar mBottomBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        mBottomBar = findView(R.id.main_bottombar);
    }

    @Override
    public void initListener() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        break;
                    case R.id.tab_chat:
                        break;
                    case R.id.tab_tools:
                        break;
                    case R.id.tab_person:
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }


}
