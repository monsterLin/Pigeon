package com.monsterlin.pigeon.ui.family;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.monsterlin.pigeon.MainActivity;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Family;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.FamilyConfig;
import com.monsterlin.pigeon.utils.SPUtils;
import com.monsterlin.pigeon.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 创建家庭
 */
public class CreateFamilyActivity extends BaseActivity {

    private Toolbar mToolBar ;
    private TextInputLayout mFamilyNameWrappter ;
    private EditText mEdtFamilyName ;
    private User bmobUser;

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_family;
    }

    @Override
    public void initViews() {
        mToolBar=findView(R.id.common_toolbar);
        initToolBar(mToolBar,"创建家庭",true);
        mFamilyNameWrappter=findView(R.id.createFamily_NameWrappter);
        mFamilyNameWrappter.setHint("家庭名");
        mEdtFamilyName=findView(R.id.createFamily_edt_name);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        bmobUser=BmobUser.getCurrentUser(User.class);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_family, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_create_family:
                String familyName = mEdtFamilyName.getText().toString();
                if (!TextUtils.isEmpty(familyName)){
                    final Family family = new Family();
                    family.setFamilyName(familyName);
                    family.setFamilyCreator(bmobUser);
                    family.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                User user = new User();
                                user.setIsCreate(true);
                                user.setIsJoin(false);
                                user.setFamily(family);
                                user.update(bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null){
                                            SPUtils.putBoolean(FamilyConfig.SPEXIST,true);
                                            ToastUtils.showToast(CreateFamilyActivity.this, "家庭创建成功");
                                            AppManager.getAppManager().finishActivity(GuideFamilyActivity.class);
                                            AppManager.getAppManager().finishActivity();
                                            nextActivity(MainActivity.class);

                                        }else {
                                            ToastUtils.showToast(CreateFamilyActivity.this,"更新家庭信息失败："+e.getMessage());
                                        }
                                    }
                                });

                            } else {
                                ToastUtils.showToast(CreateFamilyActivity.this, "创建家庭失败：" + e.getMessage());
                            }
                        }
                    });
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
