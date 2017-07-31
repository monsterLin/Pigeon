package com.monsterlin.pigeon.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.ui.Sticker.StickerActivity;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/9
 * @desc : 主页
 */

public class HomeFragment extends Fragment {

    private View view;
    private TextView mHomeTvFamily, mHomeTvSticker, mHomeTvWeather, mHomeTvRebot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeTvSticker= (TextView) view.findViewById(R.id.home_tv_sticker);
        mHomeTvSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StickerActivity.class));
            }
        });
        return view;

    }
}
