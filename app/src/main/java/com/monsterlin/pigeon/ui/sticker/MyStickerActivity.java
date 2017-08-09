package com.monsterlin.pigeon.ui.sticker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.StickerAdapter;
import com.monsterlin.pigeon.base.BaseActivity;
import com.monsterlin.pigeon.bean.Sticker;
import com.monsterlin.pigeon.bean.User;
import com.monsterlin.pigeon.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import cc.duduhuo.dialog.smartisan.NormalDialog;
import cc.duduhuo.dialog.smartisan.SmartisanDialog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/9
 * @desc :
 */
public class MyStickerActivity extends BaseActivity {

    private RecyclerView mStickerRv;
    private Toolbar mToolBar;
    private StickerAdapter stickerAdapter;
    private FloatingActionButton mFabNew;
    private RefreshLayout mRefreshLayout;
    private static boolean isFirstEnter = true;
    private BmobQuery<Sticker> stickerQuery;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sticker;
    }

    @Override
    public void initViews() {
        mToolBar = findView(R.id.common_toolbar);
        initToolBar(mToolBar, "我的贴纸", true);
        mStickerRv = findView(R.id.sticker_rv);
        mFabNew = findView(R.id.sticker_fab_new);
        mFabNew.setVisibility(View.GONE);
        mRefreshLayout = findView(R.id.refreshLayout);

        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void initListener() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    @Override
    public void initData() {
        //TODO 分页查询
        stickerQuery = new BmobQuery<>();
        stickerQuery.include("user,family");
        stickerQuery.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
        stickerQuery.order("-updatedAt");
        stickerQuery.findObjects(new FindListener<Sticker>() {
            @Override
            public void done(final List<Sticker> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        stickerAdapter = new StickerAdapter(MyStickerActivity.this, list);
                        mStickerRv.setLayoutManager(new LinearLayoutManager(MyStickerActivity.this));
                        mStickerRv.setItemAnimator(new DefaultItemAnimator());
                        mStickerRv.setAdapter(stickerAdapter);


                        stickerAdapter.setOnItemClickListener(new StickerAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(int position, View view) {

                            }

                            @Override
                            public void OnItemLongClick(final int position, View view) {
                                final NormalDialog dialog = SmartisanDialog.createNormalDialog(MyStickerActivity.this);
                                dialog.setTitle("查询结果")
                                        .setMsg("确定要删除么？")
                                        .setMsgGravity(Gravity.CENTER)
                                        .setLeftBtnText("取消")
                                        .setRightBtnText("确定")
                                        .show();
                                dialog.setOnSelectListener(new NormalDialog.OnSelectListener() {
                                    @Override
                                    public void onLeftSelect() {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onRightSelect() {
                                        Sticker sticker = new Sticker();
                                        sticker.setObjectId(list.get(position).getObjectId());
                                        sticker.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e==null){
                                                    dialog.dismiss();
                                                    initData();
                                                    ToastUtils.showToast(MyStickerActivity.this,"删除成功");
                                                }else {
                                                    dialog.dismiss();
                                                    ToastUtils.showToast(MyStickerActivity.this,"删除失败："+e.getMessage());
                                                }
                                            }
                                        });
                                    }
                                });


                            }
                        });

                    } else {
                        ToastUtils.showToast(MyStickerActivity.this, "无数据显示");
                    }
                } else {
                    ToastUtils.showToast(MyStickerActivity.this, "FindSticker：" + e.getMessage());
                }
            }
        });


    }

    @Override
    public void processClick(View v) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
