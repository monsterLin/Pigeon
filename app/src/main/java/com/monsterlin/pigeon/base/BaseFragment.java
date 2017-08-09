package com.monsterlin.pigeon.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author : Hensen_
 * @desc : 内置懒加载的基类
 * @url : http://blog.csdn.net/qq_30379689/article/details/58034750
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;

    public View convertView;
    private SparseArray<View> mViews;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            //设置已经不是可见的
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(), container, false);
        initViews();

        isInitView = true;
        lazyLoad();
        return convertView;
    }

    //懒加载
    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            //如果不是第一次加载、不是可见的、不是初始化View，则不加载数据
            return;
        }
        //加载数据
        initListener();
        initData();
        //设置已经不是第一次加载
        isFirstLoad = false;
    }

    public <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    public  <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }
}

