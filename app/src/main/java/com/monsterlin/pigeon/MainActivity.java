package com.monsterlin.pigeon;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.monsterlin.pigeon.adapter.ViewPagerAdapter;
import com.monsterlin.pigeon.view.HomeFragment;
import com.monsterlin.pigeon.view.PersonFragment;
import com.monsterlin.pigeon.view.ToolsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * __----~~~~~~~~~~~------___
 * .  .   ~~//====......          __--~ ~~
 * -.            \_|//     |||\\  ~~~~~~::::... /~
 * ___-==_       _-~o~  \/    |||  \\            _/~~-
 * __---~~~.==~||\=_    -_--~/_-~|-   |\\   \\        _/~
 * _-~~     .=~    |  \\-_    '-~7  /-   /  ||    \      /
 * .~       .~       |   \\ -_    /  /-   /   ||      \   /
 * /  ____  /         |     \\ ~-_/  /|- _/   .||       \ /
 * |~~    ~~|--~~~~--_ \     ~==-/   | \~--===~~        .\
 * '         ~-|      /|    |-~\~~       __--~~
 * |-~~-_/ |    |   ~\_   _-~            /\
 * /  \     \__   \/~                \__
 * _--~ _/ | .-~~____--~-/                  ~~==.
 * ((->/~   '.|||' -_|    ~~-/ ,              . _||
 * -_     ~\      ~~---l__i__i__i--~~_/
 * _-~-__   ~)  \--______________--~~
 * //.-~~~-~_--~- |-------~~~~~~~~
 * //.-~~~--\
 * 神兽保佑
 * 代码无BUG!
 */

/**
 * @author : monsterLin
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/5
 * @desc : ChatActivity
 * @version: 1.0
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar ;
    private BottomBar mBottomBar;
    private ViewPager mVpContent;
    private ViewPagerAdapter adapter;

    private List<Fragment> fragmentList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
