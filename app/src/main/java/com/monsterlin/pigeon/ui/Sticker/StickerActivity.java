package com.monsterlin.pigeon.ui.Sticker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.adapter.RecycleAdapter;
import com.monsterlin.pigeon.bean.NoteEntity;
import com.monsterlin.pigeon.constant.NotesConfig;
import com.monsterlin.pigeon.database.NotesDB;
import com.monsterlin.pigeon.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class StickerActivity extends NBaseActivity {

    /*工具栏*/
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    /*编辑按钮*/
    @ViewInject(R.id.fab)
    FloatingActionButton fab;

    /*抽屉*/
    @ViewInject(R.id.dl_drawer)
    DrawerLayout dl_drawer;

    /*导航视图*/
    @ViewInject(R.id.nv_main_menu)
    NavigationView nv_main_menu;

    /*抽屉切换*/
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    /*搜索按钮*/
//    @ViewInject(R.id.search_view)
//    MaterialSearchView searchView;

    /**
     * RecycleView显示记事本列表
     */
    @ViewInject(R.id.rv_list)
    RecyclerView rv_list;

    List<NoteEntity> mNoteEntityList = new ArrayList<NoteEntity>();
    private RecycleAdapter mAdapter;

    private int currentSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        ViewUtils.inject(this);/*注入viewInject*/
        initBar();/*初始化标题栏*/
        getDbInstance();/*获取数据库读写实例*/
        getDatabaseData();/*为list初始化*/
        initRecyclerView();/*初始化recycle*/
        initEvent();

    }


    /**
     * 初始化RecyclerView所需的数据源
     */
    private void initRecyclerView() {

        getDatabaseData();/*为mNoteEntityList赋值*/

        mAdapter = new RecycleAdapter(mNoteEntityList, StickerActivity.this);/*实例化adapter*/
        rv_list.setAdapter(mAdapter);/*设置适配器*/

        rv_list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_list.setItemAnimator(new DefaultItemAnimator());/*设置动画效果*/
        mAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(NotesConfig.COLUMN_NAME_ID, mNoteEntityList.get(position).getId());
                bundle.putInt(NotesConfig.COLUMN_NAME_NOTE_SORT, mNoteEntityList.get(position).getSort());
                nextActivityForResult(NoteActivity.class, bundle, NotesConfig.NOTE_REQUEST_CODE);
            }

            MaterialDialog mMaterialDialog;

            @Override
            public void onItemLongClickListener(View view, final int position) {

                mMaterialDialog = new MaterialDialog(StickerActivity.this)
                        .setTitle("提示！")
                        .setMessage("真的要删除吗？真的要删除吗？真的要删除吗？重要的事情说三遍~~")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();

                                int deleteId = mNoteEntityList.get(position).getId();

                                try {
                                    dbWrite.delete(NotesConfig.TABLE_NAME_NOTES, NotesConfig.COLUMN_NAME_ID + "=?", new String[]{Integer.toString(deleteId)});
                                } catch (Exception e) {
                                    ToastUtils.showToast(StickerActivity.this,"删除记事失败~");
                                }
                                getDatabaseData();
                                mAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showToast(StickerActivity.this,"你点击了取消");
                                mMaterialDialog.dismiss();
                            }
                        });

                mMaterialDialog.show();

            }
        });
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        nv_main_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.item_all:
                        toolbar.setTitle("全部");
                        currentSort = 0;
                        break;
                    case R.id.item_work:
                        toolbar.setTitle("工作");
                        currentSort = 1;
                        break;
                    case R.id.item_study:
                        toolbar.setTitle("学习");
                        currentSort = 2;
                        break;
                    case R.id.item_life:
                        toolbar.setTitle("生活");
                        currentSort = 3;
                        break;
                }

                getDatabaseData();/*更新显示数据*/
                mAdapter.notifyDataSetChanged();

                dl_drawer.closeDrawers();
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(NotesConfig.COLUMN_NAME_NOTE_SORT, currentSort);/*当前页面分类*/
                nextActivityForResult(NoteActivity.class, bundle, NotesConfig.NOTE_REQUEST_CODE);
            }
        });
    }

    /**
     * 从编辑日志返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            getDatabaseData();
            mAdapter.notifyDataSetChanged();

        }
    }

    /**
     * 根据当前显示的页面显示需要获取的数据
     */
    private void getDatabaseData() {
        Cursor cursorRead;

        //根据传入的id获取信息
        if (0 == currentSort) {
            cursorRead = dbRead.query(NotesConfig.TABLE_NAME_NOTES, null, null, null, null, null, null);
        } else {
            cursorRead = dbRead.query(NotesConfig.TABLE_NAME_NOTES, null, NotesConfig.COLUMN_NAME_NOTE_SORT + "=?", new String[]{currentSort + ""}, null, null, null);
        }

        mNoteEntityList.clear();
        while (cursorRead.moveToNext()) {
            int noteId = cursorRead.getInt(cursorRead.getColumnIndex(NotesConfig.COLUMN_NAME_ID));
            String noteTitle = cursorRead.getString(cursorRead.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_TITLE));
            String noteContent = cursorRead.getString(cursorRead.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_CONTENT));
            String createDate = cursorRead.getString(cursorRead.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_DATE));
            int sort = cursorRead.getInt(cursorRead.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_SORT));

            NoteEntity tempNote = new NoteEntity();
            tempNote.setId(noteId);
            tempNote.setNoteTitle(noteTitle);
            tempNote.setNoteContent(noteContent);
            tempNote.setCreateDate(createDate);
            tempNote.setSort(sort);

            mNoteEntityList.add(tempNote);/* 后续处理*/
        }

    }

    private NotesDB db;
    private SQLiteDatabase dbRead, dbWrite;

    /**
     * 获取数据库实例
     */
    private void getDbInstance() {
        db = new NotesDB(this);//获取数据库实例
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();
    }

    /**
     * 初始化ToolBar
     */
    private void initBar() {
        toolbar.setTitle("全部");
        setSupportActionBar(toolbar);/*设置toolbar作为该Activity的ActionBar*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);/*设置为true点击菜单按钮时，会显示出返回箭头*/
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, dl_drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();/*刷新【菜单>>箭头】的相互转化*/
//        dl_drawer.setDrawerListener(mActionBarDrawerToggle);
        /*设置监听器,谷歌官方用addDrawerListener代替了setDrawerListener，因为后者在新的api中可能会报空指针 */
        /*【菜单+箭头】的监听器，与mActionBarDrawerToggle.syncState()方法配合使用，才能实现【菜单与箭头】的转换*/
        dl_drawer.addDrawerListener(mActionBarDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // https://github.com/MiguelCatalan/MaterialSearchView
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
//            case R.id.action_search:
//
//                break;
//            case R.id.action_setting:
//                break;
//            case R.id.action_sync:
//                break;
//            case R.id.action_about:
//                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
//        if (searchView.isSearchOpen()) {
//            searchView.closeSearch();
//        } else {
            super.onBackPressed();
//        }
    }

    @Override
    protected void onDestroy() {
        dbRead.close();
        dbWrite.close();
        super.onDestroy();
    }
}
