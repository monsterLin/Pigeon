package com.monsterlin.pigeon.ui.family;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.monsterlin.pigeon.MainActivity;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Family;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.FamilyConfig;
import com.monsterlin.pigeon.utils.SPUtils;
import com.monsterlin.pigeon.utils.ToastUtils;

import java.util.List;

import cc.duduhuo.dialog.smartisan.NormalDialog;
import cc.duduhuo.dialog.smartisan.SmartisanDialog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

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
    private Button mBtnShare;
    private TextInputLayout mFamilyIdWrapper;
    private EditText mEdtSearch;
    private TextView mTvCreate, mTvUserId;
    private User currentUser;

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_guide;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "飞鸽", false);
        mBtnShare = findView(R.id.familyGuide_btn_share);
        mFamilyIdWrapper = findView(R.id.familyGuide_familyIdWrapper);
        mFamilyIdWrapper.setHint("创建者飞鸽号");
        mEdtSearch = findView(R.id.familyGuide_edt_searchFamily);
        mTvCreate = findView(R.id.familyGuide_tv_create);
        mTvUserId = findView(R.id.familyGuide_tv_pigeonId);
    }

    @Override
    public void initListener() {
        setOnClick(mTvCreate);
        setOnClick(mBtnShare);

        mEdtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = mEdtSearch.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > mEdtSearch.getWidth()
                        - mEdtSearch.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {

                    String searchCreateId = mEdtSearch.getText().toString();
                    if (!TextUtils.isEmpty(searchCreateId)) {
                        BmobQuery<Family> queryFamily = new BmobQuery<>();
                        queryFamily.addWhereEqualTo("familyCreator", searchCreateId);
                        queryFamily.findObjects(new FindListener<Family>() {
                            @Override
                            public void done(List<Family> list, BmobException e) {
                                if (e == null) {
                                    if (list.size() != 0) {
                                        final Family family = list.get(0);
                                        if (null != family) {
                                            showJoinFamilyDialog(family);
                                        }
                                    } else {
                                        ToastUtils.showToast(GuideFamilyActivity.this, "未查询到此家庭");
                                    }
                                } else {
                                    ToastUtils.showToast(GuideFamilyActivity.this, e.getMessage());
                                }
                            }
                        });
                    } else {
                        ToastUtils.showToast(GuideFamilyActivity.this, "请输入创建者ID");
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void initData() {
        currentUser = BmobUser.getCurrentUser(User.class);
        mTvUserId.setText(currentUser.getObjectId());
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.familyGuide_tv_create:
                BmobQuery<Family> queryFamily = new BmobQuery<>();
                queryFamily.addWhereEqualTo("familyCreator", currentUser);
                queryFamily.include("familyCreator");
                queryFamily.findObjects(new FindListener<Family>() {
                    @Override
                    public void done(List<Family> list, BmobException e) {
                        if (e == null) {
                            if (list.size() == 0) {
                                nextActivity(CreateFamilyActivity.class);
                            } else {
                                ToastUtils.showToast(GuideFamilyActivity.this, "你已经创建过家庭");
                            }
                        } else {
                            ToastUtils.showToast(GuideFamilyActivity.this, e.getMessage());
                        }
                    }
                });
                break;
            case R.id.familyGuide_btn_share:
                String userId = mTvUserId.getText().toString();
                if (!TextUtils.isEmpty(userId)) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, userId);
                    startActivity(Intent.createChooser(shareIntent, "分享"));
                }
                break;
        }
    }

    /**
     * 展示加入家庭对话框
     *
     * @param family 家庭
     */
    private void showJoinFamilyDialog(final Family family) {
        final NormalDialog dialog = SmartisanDialog.createNormalDialog(GuideFamilyActivity.this);
        dialog.setTitle("查询结果")
                .setMsg(family.getFamilyName())
                .setMsgGravity(Gravity.CENTER)
                .setLeftBtnText("取消")
                .setRightBtnText("加入")
                .show();

        dialog.setOnSelectListener(new NormalDialog.OnSelectListener() {
            @Override
            public void onLeftSelect() {
                dialog.dismiss();
            }

            @Override
            public void onRightSelect() {
                User user = new User();
                user.setIsJoin(true);
                user.setIsCreate(false);
                user.setFamily(family);
                user.update(currentUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null) {
                            SPUtils.putBoolean(FamilyConfig.SPEXIST,true);
                            ToastUtils.showToast(GuideFamilyActivity.this,"加入家庭成功");
                            AppManager.getAppManager().finishActivity();
                            nextActivity(MainActivity.class);
                            dialog.dismiss();
                        }else {
                            ToastUtils.showToast(GuideFamilyActivity.this,e.getErrorCode()+"");
                            dialog.dismiss();
                        }
                    }
                });


                dialog.dismiss();
            }
        });
    }
}
