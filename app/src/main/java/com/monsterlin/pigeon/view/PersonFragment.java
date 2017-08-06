package com.monsterlin.pigeon.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.base.BaseFragment;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.ui.user.UserInfoActivity;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
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
    private RelativeLayout mRLUser ;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initViews() {
        mCivUserPhoto = findView(R.id.person_civ_userPhoto);
        mTvName = findView(R.id.person_tv_userName);
        mTvID = findView(R.id.person_tv_userID);
        mRLUser=findView(R.id.person_rl);
    }

    @Override
    public void initListener() {
        setOnClick(mRLUser);
    }

    @Override
    public void initData() {
        mCurrentUser = BmobUser.getCurrentUser(User.class);
        BmobFile userPhotoFile = mCurrentUser.getUserPhoto();
        String nick = mCurrentUser.getNick();
        String objectId = mCurrentUser.getObjectId();


        if (!TextUtils.isEmpty(nick)) {
            mTvName.setText(nick);
        }else {
            mTvName.setText("用户"+objectId);
        }

        if (!TextUtils.isEmpty(objectId)) {
            mTvID.setText("飞鸽号："+objectId);
        }

        if (userPhotoFile==null){
            mCivUserPhoto.setImageResource(R.drawable.ic_default);
        }else {
            Picasso.with(getContext()).load(userPhotoFile.getFileUrl()).into(mCivUserPhoto);
        }

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.person_rl:
                String objectId = mCurrentUser.getObjectId();
                if (!TextUtils.isEmpty(objectId)){
                    Intent userInfoIntent = new Intent(getContext(), UserInfoActivity.class);
                    userInfoIntent.putExtra("objectId",objectId);
                    startActivity(userInfoIntent);
                }else {
                    ToastUtils.showToast(getContext(),"无用户ID");
                }
                break;
        }
    }
}
