package com.monsterlin.pigeon;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.monsterlin.pigeon.adapter.ViewPagerAdapter;
import com.monsterlin.pigeon.common.AppManager;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.monsterlin.pigeon.view.HomeFragment;
import com.monsterlin.pigeon.view.PersonFragment;
import com.monsterlin.pigeon.view.ToolsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *                                                    __----~~~~~~~~~~~------___
 *                                   .  .   ~~//====......          __--~ ~~
 *                   -.            \_|//     |||\\  ~~~~~~::::... /~
 *                ___-==_       _-~o~  \/    |||  \\            _/~~-
 *        __---~~~.==~||\=_    -_--~/_-~|-   |\\   \\        _/~
 *    _-~~     .=~    |  \\-_    '-~7  /-   /  ||    \      /
 *  .~       .~       |   \\ -_    /  /-   /   ||      \   /
 * /  ____  /         |     \\ ~-_/  /|- _/   .||       \ /
 * |~~    ~~|--~~~~--_ \     ~==-/   | \~--===~~        .\
 *          '         ~-|      /|    |-~\~~       __--~~
 *                      |-~~-_/ |    |   ~\_   _-~            /\
 *                           /  \     \__   \/~                \__
 *                       _--~ _/ | .-~~____--~-/                  ~~==.
 *                      ((->/~   '.|||' -_|    ~~-/ ,              . _||
 *                                 -_     ~\      ~~---l__i__i__i--~~_/
 *                                 _-~-__   ~)  \--______________--~~
 *                               //.-~~~-~_--~- |-------~~~~~~~~
 *                                      //.-~~~--\
 *                               神兽保佑
 *                              代码无BUG!
 */



/**
 * @author : monsterLin
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/5
 * @desc : MainActivity
 * @version: 1.0
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar ;
    private BottomBar mBottomBar;
    private ViewPager mVpContent;
    private ViewPagerAdapter adapter;

    private List<Fragment> fragmentList;
    private long exitTime = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(MainActivity.this);
        initView();
        initViewPager();

    }

    private void initView() {
        mToolBar= (Toolbar) findViewById(R.id.common_toolbar);
        mBottomBar= (BottomBar) findViewById(R.id.bottomBar );
        mVpContent = (ViewPager) findViewById(R.id.vp_main_content);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        mToolBar.setTitle("飞鸽");
                        mVpContent.setCurrentItem(0);
                        break;
                    case R.id.tab_tools:
                        mToolBar.setTitle("发现");
                        mVpContent.setCurrentItem(1);
                        break;
                    case R.id.tab_person:
                        mToolBar.setTitle("我");
                        mVpContent.setCurrentItem(2);
                        break;

                }
            }
        });
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ToolsFragment());
        fragmentList.add(new PersonFragment());

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mVpContent.setAdapter(adapter);
        mVpContent.setOffscreenPageLimit(2);

        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {

            ToastUtils.showToast(MainActivity.this,"再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
