package com.monsterlin.pigeon.ui.family;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Family;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/6
 * @desc : 编辑家庭信息
 */
public class EditFamilyActivity extends BaseActivity {

    private Toolbar mToolBar;
    private TextInputLayout mFamilyNameWrapper;
    private EditText mEdtFamilyName;
    private BmobQuery<Family> familyQuery;
    private User mCurrentUser;

    private CircleImageView mFamilyPhoto;

    private static final int PICK_CODE = 0x001;
    private static final int CROP_PICTURE = 0x002;
    private static final int NORMAL_PICK_CODE = 0x003;

    private String mCurrentPhotoStr;
    private Bitmap mPhotoBitmap;

    private String familyName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_edit;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "编辑家庭", true);
        mFamilyNameWrapper = findView(R.id.familyNameWrapper);
        mFamilyNameWrapper.setHint("家庭名");
        mEdtFamilyName = findView(R.id.editFamily_edt_name);
        mFamilyPhoto = findView(R.id.editFamily_civ_photo);
    }

    @Override
    public void initListener() {
        setOnClick(mFamilyPhoto);
    }

    @Override
    public void initData() {
        mCurrentUser = BmobUser.getCurrentUser(User.class);
        familyQuery = new BmobQuery<>();
        familyQuery.getObject(mCurrentUser.getFamily().getObjectId(), new QueryListener<Family>() {
            @Override
            public void done(Family family, BmobException e) {
                if (e == null) {
                    familyName = family.getFamilyName();
                    if (!TextUtils.isEmpty(familyName)) {
                        mEdtFamilyName.setText(familyName);
                        String photoUrl = family.getFamilyIcon().getFileUrl();
                        if (!TextUtils.isEmpty(photoUrl)) {
                            Picasso.with(EditFamilyActivity.this).load(photoUrl).into(mFamilyPhoto);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.editFamily_civ_photo:
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
                if (!TextUtils.isEmpty(mCurrentPhotoStr)) {

                    final BmobFile famiyIconFile = new BmobFile(new File(mCurrentPhotoStr));
                    famiyIconFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //上传成功
                                String familyName = mEdtFamilyName.getText().toString();
                                if (!TextUtils.isEmpty(familyName)) {
                                    Family family = new Family();
                                    family.setFamilyName(familyName);
                                    family.setFamilyIcon(famiyIconFile);
                                    family.update(mCurrentUser.getFamily().getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                ToastUtils.showToast(EditFamilyActivity.this, "更新家庭信息成功");
                                                AppManager.getAppManager().finishActivity();
                                            } else {
                                                ToastUtils.showToast(EditFamilyActivity.this, "更新家庭信息失败：" + e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    ToastUtils.showToast(EditFamilyActivity.this, "请正确填写信息");
                                }

                            } else {
                                //上传失败
                                ToastUtils.showToast(EditFamilyActivity.this, e.getMessage());
                            }
                        }
                    });


                } else if (familyName.equals(mEdtFamilyName.getText().toString())) {
                    ToastUtils.showToast(EditFamilyActivity.this, "无信息更新");
                } else if (!familyName.equals(mEdtFamilyName.getText().toString())) {
                    Family family = new Family();
                    family.setFamilyName(mEdtFamilyName.getText().toString());
                    family.update(mCurrentUser.getFamily().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                ToastUtils.showToast(EditFamilyActivity.this, "更新家庭信息成功");
                                AppManager.getAppManager().finishActivity();
                            } else {
                                ToastUtils.showToast(EditFamilyActivity.this, "更新家庭信息失败：" + e.getMessage());
                            }
                        }
                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case PICK_CODE:
                if (intent != null) {
                    Uri uri = intent.getData();
                    cropImage(uri, 500, 500, CROP_PICTURE);
                }
                break;

            case CROP_PICTURE:
                if (intent != null) {
                    Uri photoUri = intent.getData();
                    //TODO 有问题，无法找到mCurrentPhotoStr
                    if (photoUri != null) {
                        mPhotoBitmap = BitmapFactory.decodeFile(photoUri.getPath());
                    }

                    if (mPhotoBitmap == null) {
                        Bundle extra = intent.getExtras();
                        if (extra != null) {
                            mPhotoBitmap = (Bitmap) extra.get("data");
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            mPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        }
                    }

                    mFamilyPhoto.setImageBitmap(mPhotoBitmap);
                }
                break;

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

                    mFamilyPhoto.setImageBitmap(mPhotoBitmap);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }


    /**
     * 裁剪图片
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
        //裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        //裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }

}