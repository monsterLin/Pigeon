package com.monsterlin.pigeon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.constant.ApiConfig;
import com.monsterlin.pigeon.ui.brower.BrowerActivity;
import com.monsterlin.pigeon.ui.tools.TopNewsActivity;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/9
 * @desc : 工具板块
 */

public class ToolsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView mTvTopNews, mTvHealthy, mTvRumour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tools, container, false);
        initView();
        initEvents();
        return view;
    }

    private void initEvents() {
        mTvTopNews.setOnClickListener(this);
        mTvHealthy.setOnClickListener(this);
        mTvRumour.setOnClickListener(this);
    }

    private void initView() {
        mTvTopNews = (TextView) view.findViewById(R.id.tools_tv_topNews);
        mTvHealthy= (TextView) view.findViewById(R.id.tools_tv_healthy);
        mTvRumour= (TextView) view.findViewById(R.id.tools_tv_rumour);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tools_tv_topNews:
                startActivity(new Intent(getContext(), TopNewsActivity.class));
                break;
            case R.id.tools_tv_healthy:

                break;
            case R.id.tools_tv_rumour:
                Intent rumourIntent = new Intent(getContext(), BrowerActivity.class);
                rumourIntent.putExtra("url", ApiConfig.RUMOUR_SMASH_WEBSITE);
                startActivity(rumourIntent);
                break;
        }
    }
}
