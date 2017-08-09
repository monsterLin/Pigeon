package com.monsterlin.pigeon.ui.setting;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Sticker;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/8
 * @desc : 新建亲情帖
 */
public class NewStickerActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextInputLayout mContentWrapper;
    private EditText mEdtContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_sticker;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "新建", true);
        mContentWrapper = findView(R.id.contentWrapper);
        mContentWrapper.setHint("贴纸内容");
        mEdtContent = findView(R.id.sticker_edt_content);
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
                User user = BmobUser.getCurrentUser(User.class);

                if (!TextUtils.isEmpty(contentStr)){
                    Sticker sticker = new Sticker();
                    sticker.setContent(contentStr);
                    sticker.setUser(user);
                    sticker.setFamily(user.getFamily());
                    sticker.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                AppManager.getAppManager().finishActivity();
                                ToastUtils.showToast(NewStickerActivity.this,"新建成功");
                            } else {
                                ToastUtils.showToast(NewStickerActivity.this,"SaveSticker: "+e.getMessage());
                            }
                        }
                    });

                }else {
                    ToastUtils.showToast(NewStickerActivity.this,"请正确填写信息");
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
