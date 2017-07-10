package com.monsterlin.pigeon.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;

/**
 * @author : danry
 * @version : 1.0
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/10
 * @desc :
 */
public class CheckUpgradeActivity extends BaseActivity {

    private TextView toolbar_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_upgrade;
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findView(R.id.common_toolbar);
        toolbar_title = findView(R.id.toolbar_title);
        toolbar_title.setText("检查新版本");
        toolbar.setNavigationIcon(R.mipmap.left);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
