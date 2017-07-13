package com.monsterlin.pigeon;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.monsterlin.pigeon.view.HomeFragment;
import com.monsterlin.pigeon.view.PersonFragment;
import com.monsterlin.pigeon.view.ToolsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import cn.bmob.v3.BmobUser;

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
public class MainActivity extends BaseActivity {

    private int curIndex = -1;
    private BottomBar mBottomBar;
    private String[] tags = {"homeFragment", "toolsFragment", "personFragment"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        mBottomBar = findView(R.id.main_bottombar);
    }

    @Override
    public void initListener() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        setSelect(0);
                        break;
                    case R.id.tab_chat:
                        //跳转到Activity
                        break;
                    case R.id.tab_tools:
                        setSelect(1);
                        break;
                    case R.id.tab_person:
                        setSelect(2);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        ToastUtils.showToast(this, BmobUser.getCurrentUser().getObjectId());
    }

    @Override
    public void processClick(View v) {

    }


    private void setSelect(int i) {
        if (curIndex == i)
            return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tags[i]);
        if (fragment == null) {
            switch (i) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new ToolsFragment();
                    break;
                case 2:
                    fragment = new PersonFragment();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.main_contentContainer, fragment, tags[i]).commit();
        }

        for (int j = 0; j < 3; j++) {
            Fragment f = getSupportFragmentManager().findFragmentByTag(tags[j]);
            if (f != null) {
                getSupportFragmentManager().beginTransaction().hide(f).commit();
            }
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

    }
}
