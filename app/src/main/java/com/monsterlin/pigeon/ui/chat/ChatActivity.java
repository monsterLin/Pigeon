package com.monsterlin.pigeon.ui.chat;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.ChatMessageAdapter;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.ChatMessage;
import com.monsterlin.pigeon.utils.ChatRobotUtils;
import com.monsterlin.pigeon.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/10
 * @desc : 聊天中心
 */
public class ChatActivity extends BaseActivity {

    private Toolbar mToolBar;
    private ListView mLvMsg;
    private EditText mEdtMsg;
    private TextView mTvSend;

    private List<ChatMessage> mDatas;
    private ChatMessageAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.acivity_chat;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "和机器人聊天...", true);
        mLvMsg = (ListView) findViewById(R.id.chat_lv_msg);
        mEdtMsg = (EditText) findViewById(R.id.chat_edt_message);
        mTvSend = (TextView) findViewById(R.id.chat_tv_send);
    }

    @Override
    public void initListener() {
        setOnClick(mTvSend);
    }

    @Override
    public void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new ChatMessage("你好，我的主人(≧∇≦)", ChatMessage.Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mLvMsg.setAdapter(mAdapter);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.chat_tv_send:
                final String toMsg = mEdtMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    ToastUtils.showToast(ChatActivity.this, "发送消息不能为空！");
                    return;
                }


                ChatMessage toMessage = new ChatMessage();
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();
                mLvMsg.setSelection(mDatas.size() - 1);

                mEdtMsg.setText("");

                new Thread() {
                    public void run() {
                        ChatMessage fromMessage = ChatRobotUtils.sendMessage(toMsg);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        mHandler.sendMessage(m);
                    }

                }.start();

                break;
        }
    }

//    private void hideKeyBorad(View v) {
//        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {
//            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//        }
//    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 等待接收，子线程完成数据的返回
            ChatMessage fromMessge = (ChatMessage) msg.obj;
            mDatas.add(fromMessge);
            mAdapter.notifyDataSetChanged();
            mLvMsg.setSelection(mDatas.size() - 1);
        }

    };

}
