package com.monsterlin.pigeon.ui.user;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
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
    private User mCurrentUser;

    private Bitmap mPhotoBitmap;

    private String nickStr, ageString;
    private int typeInt;
    private String mCurrentPhotoStr;

    private static final int NORMAL_PICK_CODE = 0x003;

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
        setOnClick(mCivPhoto);
    }


    @Override
    public void initData() {
        mCurrentUser = BmobUser.getCurrentUser(User.class);

        if (!TextUtils.isEmpty(mCurrentUser.getNick())) {
            mEdtNick.setText(mCurrentUser.getNick());
        } else {
            mEdtNick.setText("用户" + mCurrentUser.getObjectId());
        }

        if (mCurrentUser.getAge() == 0) {
            mEdtAge.setText("");
        } else {
            mEdtAge.setText(String.valueOf(mCurrentUser.getAge()));
        }
        mEdtPhone.setText(mCurrentUser.getMobilePhoneNumber());

        typeInt = mCurrentUser.getType();

        if (typeInt == 0) {
            //子女
            mRRChild.setChecked(true);
            mRRParent.setChecked(false);
        } else if (typeInt == 1) {
            //父母
            mRRChild.setChecked(false);
            mRRParent.setChecked(true);
        }

        BmobFile userPhotoFile = mCurrentUser.getUserPhoto();
        if (userPhotoFile!=null){
            Picasso.with(UpdateUserInfoActivity.this).load(userPhotoFile.getFileUrl()).into(mCivPhoto);
        }else {
            mCivPhoto.setImageResource(R.drawable.ic_default);
        }

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.updateInfo_civ_photo:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, NORMAL_PICK_CODE);
                break;
        }
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
                boolean isSelectPhoto = isValue(mCurrentPhotoStr);
                updateUserInfo(isSelectPhoto);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUserInfo(boolean isSelectPhoto) {
        nickStr = mEdtNick.getText().toString();
        ageString = mEdtAge.getText().toString();

        if (mRRParent.isChecked()) {
            typeInt = 1;
        } else if (mRRChild.isChecked()) {
            typeInt = 0;
        }

        if (isValue(nickStr) && isValue(ageString)) {
            if (isSelectPhoto) {
                //选择了图片
                final BmobFile photoFile = new BmobFile(new File(mCurrentPhotoStr));
                photoFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            //upload ok
                            User user = new User();
                            user.setNick(nickStr);
                            user.setAge(Integer.parseInt(ageString));
                            user.setType(typeInt);
                            user.setUserPhoto(photoFile);
                            user.update(mCurrentUser.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        AppManager.getAppManager().finishActivity();
                                        ToastUtils.showToast(UpdateUserInfoActivity.this, "更新个人资料成功");
                                    } else {
                                        ToastUtils.showToast(UpdateUserInfoActivity.this, "UpdateUserInfo : " + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            ToastUtils.showToast(UpdateUserInfoActivity.this, "UploadFile : " + e.getMessage());
                        }
                    }
                });

            } else {
                //没有选择图片
                User user = new User();
                user.setNick(nickStr);
                user.setAge(Integer.parseInt(ageString));
                user.setType(typeInt);
                user.update(mCurrentUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            AppManager.getAppManager().finishActivity();
                            ToastUtils.showToast(UpdateUserInfoActivity.this, "更新个人资料成功");
                        } else {
                            ToastUtils.showToast(UpdateUserInfoActivity.this, "UpdateUserInfo : " + e.getMessage());
                        }
                    }
                });

            }
        } else {
            ToastUtils.showToast(UpdateUserInfoActivity.this, "请正确填写信息");
        }

    }

    private boolean isValue(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {

            case NORMAL_PICK_CODE:
                //TODO 临时的方法，可能产生内存溢出
                if (intent != null) {
                    Uri uri = intent.getData();
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();

                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    mCurrentPhotoStr = cursor.getString(idx);

                    cursor.close();

                    mPhotoBitmap = BitmapFactory.decodeFile(mCurrentPhotoStr);

                    mCivPhoto.setImageBitmap(mPhotoBitmap);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}
