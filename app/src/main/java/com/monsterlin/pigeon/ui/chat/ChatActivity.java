package com.monsterlin.pigeon.ui.chat;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.ChatAdapter;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.ChatModel;
import com.monsterlin.pigeon.bean.ItemModel;

import java.util.ArrayList;

/**
 * @author : danry
 * @version : 1.0
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/18
 * @desc : 聊天中心
 */
public class ChatActivity extends BaseActivity {
    private Toolbar mToolBar;
    private RecyclerView mLvMsg;
    private ChatAdapter mAdapter;
    private EditText mEdtMessage;
    private TextView mTvSend;
    private String content;

    @Override
    public int getLayoutId() {
        return R.layout.acivity_chat;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "聊天", true);
        mLvMsg = (RecyclerView) findViewById(R.id.chat_lv_msg);
        mEdtMessage = (EditText) findViewById(R.id.chat_edt_message);
        mTvSend = (TextView) findViewById(R.id.chat_tv_send);

        mLvMsg.setHasFixedSize(true);
        mLvMsg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLvMsg.setAdapter(mAdapter = new ChatAdapter());
        mAdapter.replaceAll(getTestAdData());
    }

    @Override
    public void initListener() {
        setOnClick(mTvSend);

        mEdtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.chat_tv_send:
                ArrayList<ItemModel> data = new ArrayList<>();
                ChatModel model = new ChatModel();
                model.setIcon("http://img4.imgtn.bdimg.com/it/u=3804423306,3596665915&fm=214&gp=0.jpg");
                model.setContent(content);
                data.add(new ItemModel(ItemModel.CHAT_RIGHT, model));
                mAdapter.addAll(data);
                mAdapter.notifyDataSetChanged();
                mEdtMessage.setText("");
                hideKeyBorad(mEdtMessage);
                break;
        }
    }

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    private ArrayList<ItemModel> getTestAdData() {
        ArrayList<ItemModel> models = new ArrayList<>();
        ChatModel model = new ChatModel();
        model.setContent("嗨喽~在干嘛来着？");
        model.setIcon("http://scimg.jb51.net/touxiang/201705/2017053121043719.jpg");
        models.add(new ItemModel(ItemModel.CHAT_LEFT, model));
        ChatModel model2 = new ChatModel();
        model2.setContent("农药啊!快A它！我去~");
        model2.setIcon("http://img4.imgtn.bdimg.com/it/u=3804423306,3596665915&fm=214&gp=0.jpg");
        models.add(new ItemModel(ItemModel.CHAT_RIGHT, model2));
        ChatModel model3 = new ChatModel();
        model3.setContent("农药有啥好玩的，陪我出去逛逛街~");
        model3.setIcon("http://scimg.jb51.net/touxiang/201705/2017053121043719.jpg");
        models.add(new ItemModel(ItemModel.CHAT_LEFT, model3));
        ChatModel model4 = new ChatModel();
        model4.setContent("等会，就5分钟。。。");
        model4.setIcon("http://img4.imgtn.bdimg.com/it/u=3804423306,3596665915&fm=214&gp=0.jpg");
        models.add(new ItemModel(ItemModel.CHAT_RIGHT, model4));
        ChatModel model5 = new ChatModel();
        model5.setContent("你确定就5分钟？");
        model5.setIcon("http://scimg.jb51.net/touxiang/201705/2017053121043719.jpg");
        models.add(new ItemModel(ItemModel.CHAT_LEFT, model5));
        ChatModel model6 = new ChatModel();
        model6.setContent("傻叉！别抢我蓝buff啊！");
        model6.setIcon("http://img4.imgtn.bdimg.com/it/u=3804423306,3596665915&fm=214&gp=0.jpg");
        models.add(new ItemModel(ItemModel.CHAT_RIGHT, model6));
        return models;
    }
}
