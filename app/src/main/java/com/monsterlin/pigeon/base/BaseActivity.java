package com.monsterlin.pigeon.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;

import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.widget.LoadingDialog;

/**
 * @author : Hensen_
 * @desc : Activity的基类
 * @url : http://blog.csdn.net/qq_30379689/article/details/58034750
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private SparseArray<View> mViews;

    public LoadingDialog dialog;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View v);

    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<>();
        setContentView(getLayoutId());
        AppManager.getAppManager().addActivity(this); //添加Activity到堆栈中
        dialog = new LoadingDialog(this);
        initViews();
        initListener();
        initData();
    }

    public <E extends View> E findView(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    /**
     * View设置OnClick事件
     */
    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }


    /**
     * 初始化ToolBar
     *
     * @param toolbar ToolBar的实例
     * @param title 标题
     * @param isBack 是否出现返回按钮
     */
    public void initToolBar(Toolbar toolbar, String title, boolean isBack) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (isBack) {
            try {
                ActionBar actionBar = getSupportActionBar();
                if (null != actionBar)
                    actionBar.setDisplayHomeAsUpEnabled(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

    }

    /**
     * 跳转Activity
     *
     * @param cls 要跳转的Activity
     */
    public void nextActivity(Class cls) {
        nextActivity(cls, null);
    }

    /**
     * 带数据包的跳转
     *
     * @param cls 要跳转的Activity
     * @param bundle 数据包
     */
    public void nextActivity(Class cls, Bundle bundle) {
        Intent i = new Intent(this, cls);
        if (bundle != null)
            i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);//销毁当前堆栈
    }
}
