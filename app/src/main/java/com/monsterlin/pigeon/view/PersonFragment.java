package com.monsterlin.pigeon.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseFragment;
import com.monsterlin.pigeon.bean.User;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/9
 * @desc : 个人中心
 */

public class PersonFragment extends BaseFragment {

    private CircleImageView mCivUserPhoto;
    private TextView mTvName, mTvID;
    private User mCurrentUser;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initViews() {
        mCivUserPhoto = findView(R.id.person_civ_userPhoto);
        mTvName = findView(R.id.person_tv_userName);
        mTvID = findView(R.id.person_tv_userID);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        mCurrentUser = BmobUser.getCurrentUser(User.class);
        String userPhotoUrl = mCurrentUser.getUserPhoto().getFileUrl();
        String nick = mCurrentUser.getNick();
        String objectId = mCurrentUser.getObjectId();


        if (!TextUtils.isEmpty(nick)) {
            mTvName.setText(nick);
        }

        if (!TextUtils.isEmpty(objectId)) {
            mTvID.setText("飞鸽号："+objectId);
        }

        if (TextUtils.isEmpty(userPhotoUrl)){
            mCivUserPhoto.setImageResource(R.drawable.test_photo_03);
        }else {
            Picasso.with(getContext()).load(userPhotoUrl).into(mCivUserPhoto);
        }

    }

    @Override
    public void processClick(View v) {

    }
}
