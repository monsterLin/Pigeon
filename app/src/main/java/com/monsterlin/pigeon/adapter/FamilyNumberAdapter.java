package com.monsterlin.pigeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.ui.user.UserInfoActivity;
import com.monsterlin.pigeon.vholder.FamilyNumberVHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 家庭成员适配器
 */
public class FamilyNumberAdapter extends RecyclerView.Adapter<FamilyNumberVHolder> {
    private Context mContext;
    private List<User> numberList;
    private LayoutInflater mInflater;

    public FamilyNumberAdapter(Context mContext, List<User> numberList) {
        this.mContext = mContext;
        this.numberList = numberList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public FamilyNumberVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_family_number, parent, false);
        FamilyNumberVHolder familyNumberVHolder = new FamilyNumberVHolder(view);
        return familyNumberVHolder;
    }

    @Override
    public void onBindViewHolder(FamilyNumberVHolder holder, final int position) {

        BmobFile userPhotoFile = numberList.get(position).getUserPhoto();

        if (userPhotoFile!=null){
            Picasso.with(mContext).load(numberList.get(position).getUserPhoto().getFileUrl()).into(holder.mIvIcon);
        }else {
            holder.mIvIcon.setImageResource(R.drawable.ic_default);

        }

        String nick = numberList.get(position).getNick();

        if (!TextUtils.isEmpty(nick)){
            holder.mTvNick.setText(numberList.get(position).getNick());
        }

        int type = numberList.get(position).getType();
        if (type == 0) {
            //子女
            holder.mIvType.setImageResource(R.drawable.ic_family_parent);
        } else if (type == 1) {
            //父母
            holder.mIvType.setImageResource(R.drawable.ic_family_child);
        }

        holder.mIvUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userInfoIntent = new Intent(mContext, UserInfoActivity.class);
                userInfoIntent.putExtra("objectId",numberList.get(position).getObjectId());
                mContext.startActivity(userInfoIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numberList.size();
    }
}
