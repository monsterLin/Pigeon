package com.monsterlin.pigeon.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/11
 * @desc : 家庭引导
 */
public class GuideFamilyActivity extends BaseActivity {

    private Toolbar mToolBar;
    private Button mBtnCopy;
    private TextInputLayout mFamilyIdWrapper;
    private EditText mEdtSearch;
    private TextView mTvCreate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_guide;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "飞鸽", false);
        mBtnCopy = findView(R.id.familyGuide_btn_copy);
        mFamilyIdWrapper = findView(R.id.familyGuide_familyIdWrapper);
        mFamilyIdWrapper.setHint("创建者飞鸽号");
        mEdtSearch = findView(R.id.familyGuide_edt_searchFamily);
        mTvCreate = findView(R.id.familyGuide_tv_create);
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
