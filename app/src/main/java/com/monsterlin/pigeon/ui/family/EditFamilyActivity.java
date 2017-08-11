package com.monsterlin.pigeon.ui.family;

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

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Family;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private String mCurrentPhotoStr;
    private Bitmap mPhotoBitmap;
    private static final int REQUEST_SELECT_PICTURE = 0x01;


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
        dialog.showDialog();
        mCurrentUser = BmobUser.getCurrentUser(User.class);
        familyQuery = new BmobQuery<>();
        familyQuery.getObject(mCurrentUser.getFamily().getObjectId(), new QueryListener<Family>() {
            @Override
            public void done(Family family, BmobException e) {
                if (e == null) {
                    familyName = family.getFamilyName();
                    if (!TextUtils.isEmpty(familyName)) {
                        mEdtFamilyName.setText(familyName);

                        BmobFile familyIconFile = family.getFamilyIcon();

                        if (familyIconFile!=null) {
                            Picasso.with(EditFamilyActivity.this).load(familyIconFile.getFileUrl()).into(mFamilyPhoto);
                        }else {
                            mFamilyPhoto.setImageResource(R.drawable.ic_default);
                        }

                        dialog.dismissDialog();

                    } else {
                        dialog.dismissDialog();
                    }
                } else {
                    dialog.dismissDialog();
                    ToastUtils.showToast(EditFamilyActivity.this, "GetFamilyInfo : " + e.getMessage());
                }
            }
        });

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.editFamily_civ_photo:
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
                                                dialog.dismissDialog();
                                                ToastUtils.showToast(EditFamilyActivity.this, "更新家庭信息成功");
                                                AppManager.getAppManager().finishActivity();
                                            } else {
                                                dialog.dismissDialog();
                                                ToastUtils.showToast(EditFamilyActivity.this, "更新家庭信息失败：" + e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    dialog.dismissDialog();
                                    ToastUtils.showToast(EditFamilyActivity.this, "请正确填写信息");
                                }

                            } else {
                                //上传失败
                                dialog.dismissDialog();
                                ToastUtils.showToast(EditFamilyActivity.this, e.getMessage());
                            }
                        }
                    });


                } else if (familyName.equals(mEdtFamilyName.getText().toString())) {
                    dialog.dismissDialog();
                    ToastUtils.showToast(EditFamilyActivity.this, "无信息更新");
                } else if (!familyName.equals(mEdtFamilyName.getText().toString())) {
                    Family family = new Family();
                    family.setFamilyName(mEdtFamilyName.getText().toString());
                    family.update(mCurrentUser.getFamily().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                dialog.dismissDialog();
                                ToastUtils.showToast(EditFamilyActivity.this, "更新家庭信息成功");
                                AppManager.getAppManager().finishActivity();
                            } else {
                                dialog.dismissDialog();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    ToastUtils.showToast(EditFamilyActivity.this, "类型不匹配");
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

        uCrop.start(EditFamilyActivity.this);
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            mCurrentPhotoStr = resultUri.getPath();
            mPhotoBitmap = BitmapFactory.decodeFile(resultUri.getPath());
            mFamilyPhoto.setImageBitmap(mPhotoBitmap);
        }


    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            ToastUtils.showToast(EditFamilyActivity.this, cropError.getMessage());
        } else {
            ToastUtils.showToast(EditFamilyActivity.this, "handleCropError");
        }
    }


}