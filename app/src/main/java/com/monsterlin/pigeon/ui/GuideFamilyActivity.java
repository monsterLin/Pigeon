package com.monsterlin.pigeon.ui;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.monsterlin.pigeon.utils.SPUtils;
import com.monsterlin.pigeon.utils.ToastUtils;

import java.util.List;

import cc.duduhuo.dialog.smartisan.NormalDialog;
import cc.duduhuo.dialog.smartisan.SmartisanDialog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
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
    private Button mBtnCopy;
    private TextInputLayout mFamilyIdWrapper;
    private EditText mEdtSearch;
    private TextView mTvCreate, mTvUserId;
    private View viewCreate;
    private User currentUser;

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
        mTvUserId = findView(R.id.familyGuide_tv_pigeonId);
    }

    @Override
    public void initListener() {
        setOnClick(mTvCreate);
        setOnClick(mBtnCopy);

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

                    //TODO 此部分代码需要封装和重构
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
                                showCreateFamilyDialog();
                            } else {
                                ToastUtils.showToast(GuideFamilyActivity.this, "你已经创建过家庭");
                            }
                        } else {
                            ToastUtils.showToast(GuideFamilyActivity.this, e.getMessage());
                        }
                    }
                });
                break;
            case R.id.familyGuide_btn_copy:
                String userId = mTvUserId.getText().toString();
                if (!TextUtils.isEmpty(userId)) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(userId);
                    ToastUtils.showToast(GuideFamilyActivity.this, "复制成功，快分享给你的家庭吧");
                }
                break;
        }
    }

    /**
     * 创建家庭弹出框
     */
    private void showCreateFamilyDialog() {
        viewCreate = LayoutInflater.from(this).inflate(R.layout.view_simple_create_family, null);
        final EditText mEdtFamily = (EditText) viewCreate.findViewById(R.id.family_edt_create);
        AlertDialog dialog = new AlertDialog
                .Builder(this)
                .setTitle("创建家庭")
                .setView(viewCreate)
                .setPositiveButton("创建", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String familyName = mEdtFamily.getText().toString();
                        if (!TextUtils.isEmpty(familyName)) {
                            final Family family = new Family();
                            family.setFamilyName(familyName);
                            family.setFamilyCreator(currentUser);
                            family.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        User user = new User();
                                        user.setIsCreate(true);
                                        user.setFamily(family);
                                        user.update(currentUser.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e==null){
                                                    SPUtils.putBoolean("isCreateFamily",true);
                                                    ToastUtils.showToast(GuideFamilyActivity.this, "创建成功，快去邀请你的家庭成员吧");
                                                    nextActivity(MainActivity.class);
                                                    AppManager.getAppManager().finishActivity();
                                                }else {
                                                    ToastUtils.showToast(GuideFamilyActivity.this,"更新用户信息失败："+e.getMessage());
                                                }
                                            }
                                        });

                                    } else {
                                        ToastUtils.showToast(GuideFamilyActivity.this, "创建家庭失败：" + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
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
                final Family updateFamily = new Family();
                updateFamily.setObjectId(family.getObjectId());
                updateFamily.addUnique("familyList", currentUser);
                updateFamily.update(family.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            User user = new User();
                            user.setIsJoin(true);
                            user.setFamily(updateFamily);
                            user.update(currentUser.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        ToastUtils.showToast(GuideFamilyActivity.this, "加入家庭成功");
                                        SPUtils.putBoolean("isJoinFamily",true);
                                    }  else {
                                        ToastUtils.showToast(GuideFamilyActivity.this,"更新用户信息失败："+e.getMessage());
                                    }
                                }
                            });
                        } else {
                            ToastUtils.showToast(GuideFamilyActivity.this, "加入家庭失败：" + e.getMessage());
                        }
                    }
                });


                dialog.dismiss();
            }
        });
    }
}
