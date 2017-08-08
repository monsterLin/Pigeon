package com.monsterlin.pigeon.ui.app;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Advice;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.utils.ToastUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/8
 * @desc : 建议页面
 */
public class AdviceActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextInputLayout mContentWrapper, mTelWrapper;
    private EditText mEdtContent, mEdtTel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_advice;
    }

    @Override
    public void initViews() {
        mContentWrapper = findView(R.id.adviceContentWrapper);
        mTelWrapper = findView(R.id.adviceTelWrapper);

        mContentWrapper.setHint("建议");
        mTelWrapper.setHint("联系方式");

        mEdtContent = findView(R.id.advice_edt_content);
        mEdtTel = findView(R.id.advice_edt_tel);


        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "建议", true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_operate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ok:
                String contentStr = mEdtContent.getText().toString();
                String telStr = mEdtTel.getText().toString();

                if (!TextUtils.isEmpty(contentStr) && !TextUtils.isEmpty(telStr)) {
                    Advice advice = new Advice();
                    advice.setContent(contentStr);
                    advice.setTel(telStr);
                    advice.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast(AdviceActivity.this, "我们已经收到你的反馈，谢谢");
                                AppManager.getAppManager().finishActivity();
                            } else {
                                ToastUtils.showToast(AdviceActivity.this, "发送反馈出现异常：" + e.getMessage());
                            }
                        }
                    });

                } else {
                    ToastUtils.showToast(AdviceActivity.this, "请正确填写信息");
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
