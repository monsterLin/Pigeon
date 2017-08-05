package com.monsterlin.pigeon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * @ author ：monsterLin
 * @ date : 2017/1/15
 * @ github : https://github.com/monsterLin
 * @ blog : http://monsterlin.com/
 * @ describe :
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}