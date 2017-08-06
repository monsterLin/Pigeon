package com.monsterlin.pigeon.ui.family;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/6
 * @desc : 编辑家庭信息
 */
public class EditFamilyActivity extends BaseActivity {

    private Toolbar mToolBar ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_edit;
    }

    @Override
    public void initViews() {
        mToolBar=findView(R.id.common_toolbar);
        initToolBar(mToolBar,"编辑家庭",true);
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
