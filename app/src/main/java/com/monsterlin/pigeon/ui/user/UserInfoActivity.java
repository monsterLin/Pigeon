package com.monsterlin.pigeon.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 用户信息
 */
public class UserInfoActivity extends BaseActivity {

    private Toolbar mToolBar;
    private String objectId;

    private CircleImageView mCivUserPhoto;
    private TextView mTvNick, mTvAge, mTvPhone, mTvFamily, mTvType;
    private BmobQuery<User> query;
    private boolean isMenu = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectId = getIntent().getStringExtra("objectId");
        if (objectId.equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
            isMenu = true;
        } else {
            isMenu = false;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "个人资料", true);
        mCivUserPhoto = findView(R.id.userinfo_civ_photo);
        mTvNick = findView(R.id.userinfo_tv_nick);
        mTvAge = findView(R.id.userinfo_tv_age);
        mTvPhone = findView(R.id.userinfo_tv_mobile);
        mTvFamily = findView(R.id.userinfo_tv_family);
        mTvType = findView(R.id.userinfo_tv_type);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        dialog.showDialog();
        objectId = getIntent().getStringExtra("objectId");
        if (!TextUtils.isEmpty(objectId)) {
            initUserInfo(objectId);
        } else {
            initUserInfo(BmobUser.getCurrentUser(User.class).getObjectId());
        }

    }

    private void initUserInfo(final String objectId) {
        query = new BmobQuery<>();
        query.include("family");
        query.getObject(objectId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    dialog.dismissDialog();

                    BmobFile userPhotoFile = user.getUserPhoto();
                    String nickString = user.getNick();
                    int ageInt = user.getAge();
                    String phoneString = user.getMobilePhoneNumber();
                    String familyNameString = user.getFamily().getFamilyName();
                    int typeInt = user.getType();

                    if (userPhotoFile != null) {
                        Picasso.with(UserInfoActivity.this).load(userPhotoFile.getFileUrl()).into(mCivUserPhoto);
                    } else {
                        mCivUserPhoto.setImageResource(R.drawable.ic_default);
                    }

                    if (!TextUtils.isEmpty(nickString)) {
                        mTvNick.setText("昵         称：" + nickString);
                    }

                    if (ageInt <= 0) {
                        mTvAge.setText("年         龄：" + "请更新个人信息");
                    } else {
                        mTvAge.setText("年         龄：" + ageInt);
                    }

                    if (!TextUtils.isEmpty(phoneString)) {
                        mTvPhone.setText("手机号码：" + phoneString);
                    } else {
                        mTvPhone.setText("手机号码：" + "null");
                    }

                    if (!TextUtils.isEmpty(familyNameString)) {
                        mTvFamily.setText("家庭信息：" + familyNameString);
                    } else {
                        mTvFamily.setText("家庭信息：暂未加入家庭");
                    }

                    if (typeInt == 0) {
                        mTvType.setText("关         系： 子女");
                    } else if (typeInt == 1) {
                        mTvType.setText("关         系： 父母");
                    }


                } else {
                    dialog.dismissDialog();
                    ToastUtils.showToast(UserInfoActivity.this, "" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isMenu) {
            getMenuInflater().inflate(R.menu.menu_user_edit, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                startActivity(new Intent(UserInfoActivity.this, UpdateUserInfoActivity.class));
                break;
            case R.id.action_user_chanage_pass:
                Bundle bundle = new Bundle();
                bundle.putInt("sign", 1);
                nextActivity(RegisterOrResetActivity.class, bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
