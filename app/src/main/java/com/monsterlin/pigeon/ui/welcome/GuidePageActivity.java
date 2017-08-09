package com.monsterlin.pigeon.ui.welcome;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.GuidePageAdapter;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.constant.AppConfig;
import com.monsterlin.pigeon.ui.user.LoginActivity;
import com.monsterlin.pigeon.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/7
 * @desc : 引导页
 */
public class GuidePageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合

    private ImageView mFabStart;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initViews() {
        mFabStart = findView(R.id.guide_iv_start);
        initViewPager();
    }

    @Override
    public void initListener() {
        setOnClick(mFabStart);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.guide_iv_start:
                AppManager.getAppManager().finishActivity();
                SPUtils.putBoolean(AppConfig.SPFIST, false);
                nextActivity(LoginActivity.class);
                break;
        }
    }


    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        //实例化图片资源
        imageIdArray = new int[]{R.drawable.bg_guide_family, R.drawable.bg_guide_notes, R.drawable.bg_guide_weather, R.drawable.bg_guide_rebot};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        vp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
            mFabStart.setVisibility(View.VISIBLE);
        } else {
            mFabStart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
