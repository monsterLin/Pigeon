package com.monsterlin.pigeon.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private String nickStr, ageString;
    private int typeInt;

    private Bitmap mPhotoBitmap;
    private String mCurrentPhotoStr;
    private static final int REQUEST_SELECT_PICTURE = 0x01;


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
        if (userPhotoFile != null) {
            Picasso.with(UpdateUserInfoActivity.this).load(userPhotoFile.getFileUrl()).into(mCivPhoto);
        } else {
            mCivPhoto.setImageResource(R.drawable.ic_default);
        }

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.updateInfo_civ_photo:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
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
                dialog.showDialog();
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
                                        dialog.dismissDialog();
                                        AppManager.getAppManager().finishActivity();
                                        ToastUtils.showToast(UpdateUserInfoActivity.this, "更新个人资料成功");
                                    } else {
                                        dialog.dismissDialog();
                                        ToastUtils.showToast(UpdateUserInfoActivity.this, "UpdateUserInfo : " + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            dialog.dismissDialog();
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
                            dialog.dismissDialog();
                            AppManager.getAppManager().finishActivity();
                            ToastUtils.showToast(UpdateUserInfoActivity.this, "更新个人资料成功");
                        } else {
                            dialog.dismissDialog();
                            ToastUtils.showToast(UpdateUserInfoActivity.this, "UpdateUserInfo : " + e.getMessage());
                        }
                    }
                });

            }
        } else {
            dialog.dismissDialog();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    ToastUtils.showToast(UpdateUserInfoActivity.this,"类型不匹配");
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(d);

        String destinationFileName = s + ".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

        uCrop = uCrop.useSourceImageAspectRatio();

        uCrop.start(UpdateUserInfoActivity.this);
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            mCurrentPhotoStr = resultUri.getPath();
            mPhotoBitmap = BitmapFactory.decodeFile(resultUri.getPath());
            mCivPhoto.setImageBitmap(mPhotoBitmap);
        }


    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            ToastUtils.showToast(UpdateUserInfoActivity.this,cropError.getMessage());
        } else {
            ToastUtils.showToast(UpdateUserInfoActivity.this,"handleCropError");
        }
    }

}
