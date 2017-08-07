package com.monsterlin.pigeon.ui.user;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.User;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/7
 * @desc : 更新资料
 */
public class UpdateUserInfoActivity extends BaseActivity {

    private TextInputLayout mNickWrapper, mAgeWrapper, mPhoneWrapper;
    private EditText mEdtNick, mEdtAge, mEdtPhone;
    private CircleImageView mCivPhoto;
    private RadioButton mRRChild, mRRParent;

    private Toolbar mToolBar;

    private User user;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_userinfo;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "我的资料", true);

        mNickWrapper = findView(R.id.nickWrapper);
        mNickWrapper.setHint("昵称");
        mAgeWrapper = findView(R.id.ageWrapper);
        mAgeWrapper.setHint("年龄");
        mPhoneWrapper = findView(R.id.phoneWrapper);
        mPhoneWrapper.setHint("电话（不可修改）");


        mEdtNick = findView(R.id.updateUser_edt_nick);
        mEdtAge = findView(R.id.updateUser_edt_age);
        mEdtPhone = findView(R.id.updateUser_edt_phone);

        mCivPhoto = findView(R.id.updateInfo_civ_photo);

        mRRChild = findView(R.id.updateInfo_rr_child);
        mRRParent = findView(R.id.updateInfo_rr_parent);



    }

    @Override
    public void initListener() {

    }


    @Override
    public void initData() {
        user = BmobUser.getCurrentUser(User.class);

        mEdtNick.setText(user.getNick());
        if (user.getAge()==0){
            mEdtAge.setText("");
        }else {
            mEdtAge.setText(user.getAge());
        }
        mEdtPhone.setText(user.getMobilePhoneNumber());

        int type = user.getType();

        if (type==0){
            //子女
            mRRChild.setChecked(true);
            mRRParent.setChecked(false);
        }else if (type==1){
            //父母
            mRRChild.setChecked(false);
            mRRParent.setChecked(true);
        }

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
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
