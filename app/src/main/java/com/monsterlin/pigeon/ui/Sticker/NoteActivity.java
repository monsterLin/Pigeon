package com.monsterlin.pigeon.ui.Sticker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.monsterlin.pigeon.R;
import com.monsterlin.pigeon.constant.NotesConfig;
import com.monsterlin.pigeon.database.NotesDB;
import com.monsterlin.pigeon.utils.MyDateUtils;

/**
 * 类名：新建日记
 * 功能：实现增加日记的功能
 * Created by Pluto on 2016/5/3.
 */
public class NoteActivity extends NBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;


    @ViewInject(R.id.tv_sort_item)
    private TextView tvSortItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);  //关闭软键盘
        ViewUtils.inject(this);

        noteId = getIntent().getIntExtra(NotesConfig.COLUMN_NAME_ID, -1);/* 数据传入*/
        sort = getIntent().getIntExtra(NotesConfig.COLUMN_NAME_NOTE_SORT, 0);
        if (noteId<0){
            initToolBar(toolbar, "新的笔记", true);
        }else {
            initToolBar(toolbar, "编辑笔记", true);
        }

        /*初始化popupWindow*/
        initPopupWindow();
        /*获取记事本的数据*/
        getNote();

        tvSortItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                } else {
                    int popupWidth = view.getMeasuredWidth();
                    int popupHeight = view.getMeasuredHeight();
                    int[] location = new int[2];

                    // 允许点击外部消失
                    popWindow.setFocusable(true);
                    popWindow.setBackgroundDrawable(new BitmapDrawable());
                    popWindow.setOutsideTouchable(true);

                    v.getLocationOnScreen(location);
                    popWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
                    popWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);

//                    int screenHeight = NoteActivity.this.getWindowManager().getDefaultDisplay().getHeight();
////                    float viewY = v.getLayoutDirection();
//                    int[] mLocation = new int[2];
//                    v.getLocationOnScreen(mLocation);
//                    int viewY = mLocation[1];
//                    if (viewY <= (screenHeight * 3 / 4)) {/*View的位置在屏幕的1/3之上*//*软键盘弹出状态*/
//                        Toast.makeText(NoteActivity.this, "View的纵坐标：" + viewY, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    View view;
    PopupWindow popWindow;

    /*用于获取选中item的文本内容*/
    TextView tvSortAll, tvSortWork, tvSortStudy, tvSortLife;
    /*显示选中状态的imageView*/
    ImageView ivAllCheck, ivWorkCheck,ivStudyCheck,ivLifeCheck;


    private void initPopupWindow() {
        /*获取popupWindow布局*/
        view = this.getLayoutInflater().inflate(R.layout.popup_window, null);

        /*实例化popWindow，并为其设置布局属性*/
        popWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        /*设置是否可点击*/
//        popWindow.setOutsideTouchable(false);

        View linearlayoutAll = view.findViewById(R.id.item_all_sort);
        View linearlayoutWork = view.findViewById(R.id.item_work_sort);
        View linearlayoutStudy = view.findViewById(R.id.item_study_sort);
        View linearlayoutLife = view.findViewById(R.id.item_life_sort);

        ivAllCheck = (ImageView) linearlayoutAll.findViewById(R.id.iv_sort_current);
        ivWorkCheck = (ImageView) linearlayoutWork.findViewById(R.id.iv_sort_current);
        ivStudyCheck = (ImageView) linearlayoutStudy.findViewById(R.id.iv_sort_current);
        ivLifeCheck = (ImageView) linearlayoutLife.findViewById(R.id.iv_sort_current);

        tvSortAll = (TextView) linearlayoutAll.findViewById(R.id.tv_sort_pop);
        tvSortWork = (TextView) linearlayoutWork.findViewById(R.id.tv_sort_pop);
        tvSortStudy = (TextView) linearlayoutStudy.findViewById(R.id.tv_sort_pop);
        tvSortLife = (TextView) linearlayoutLife.findViewById(R.id.tv_sort_pop);

        tvSortAll.setText(sortName[0]);
        tvSortWork.setText(sortName[1]);
        tvSortStudy.setText(sortName[2]);
        tvSortLife.setText(sortName[3]);

        linearlayoutAll.setOnClickListener(this);
        linearlayoutWork.setOnClickListener(this);
        linearlayoutStudy.setOnClickListener(this);
        linearlayoutLife.setOnClickListener(this);
    }



    /**
     * 创建菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newnote, menu);
        return true;
    }


    /**
     * 菜单栏的完成按钮被点击
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_done:/*点击完成*/
                if (saveNote()) {/*数据存入数据库*/
                    Intent resultItent = this.getIntent();
                    setResult(RESULT_OK, resultItent);
                    finish();
                    break;
                } else {
                    Toast.makeText(NoteActivity.this, "标题和内容不可为空", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }


    private String[] sortName = new String[]{"全部", "工作", "学习", "生活"};

    /**
     * 数据库
     */
    private int noteId = -1;//判断数据库中是否存在该数据
    private int sort = 0;/*用于存储sort信息*/
    private NotesDB db;
    private SQLiteDatabase dbRead, dbWrite;


    @ViewInject(R.id.et_title)
    private EditText etTitle;

    @ViewInject(R.id.et_content)
    private EditText etContent;

    @ViewInject(R.id.tv_crete_date)
    private TextView tvCreateDate;

    @ViewInject(R.id.tv_sort_item)
    private TextView tvSort;

    @ViewInject(R.id.iv_sort_icon)
    private ImageView ivSortIcon;

    /**
     * 获取数据库中的数据
     */
    private void getNote() {
        db = new NotesDB(this);//获取数据库实例
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();


        if (noteId > -1) {/*如果数据库中存有该数据*/
            //根据传入的id获取信息
            Cursor cInput = dbRead.query(NotesConfig.TABLE_NAME_NOTES, null, NotesConfig.COLUMN_NAME_ID + "=?", new String[]{noteId + ""}, null, null, null);
            if (cInput.moveToFirst()) {
                String title = cInput.getString(cInput.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_TITLE));
                etTitle.setText(title);
                String content = cInput.getString(cInput.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_CONTENT));
                etContent.setText(content);
                String creteTime = cInput.getString(cInput.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_DATE));
                tvCreateDate.setText("修改时间  " + MyDateUtils.getStringForFormat(creteTime));
                sort = cInput.getInt(cInput.getColumnIndex(NotesConfig.COLUMN_NAME_NOTE_SORT));
                tvSortItem.setText(sortName[sort]);
            }
        } else {/*如果数据库中不存在该数据*/
            String currentTime = MyDateUtils.subTime(MyDateUtils.getTimeAsString(MyDateUtils.getTimeAsDate()));
            tvCreateDate.setText("创建时间  " + currentTime);
            tvSort.setText(sortName[getIntent().getIntExtra(NotesConfig.COLUMN_NAME_NOTE_SORT, 0)]);
        }

        /*显示选中状态图标*/
        checkWhat(sort);

    }

    /**
     * 保存笔记
     *
     * @return true:数据更改
     */
    public boolean saveNote() {

        String mTitle = etTitle.getText().toString();
        String mContent = etContent.getText().toString();
        ContentValues cv = new ContentValues();

        //判定title,content是否为空 为空则提示,否则就存储
        if (!mTitle.isEmpty() && !mContent.isEmpty()) {

            cv.put(NotesConfig.COLUMN_NAME_NOTE_TITLE, mTitle);
            cv.put(NotesConfig.COLUMN_NAME_NOTE_CONTENT, mContent);
            cv.put(NotesConfig.COLUMN_NAME_NOTE_DATE, MyDateUtils.getTimeAsString(MyDateUtils.getTimeAsDate()));
            cv.put(NotesConfig.COLUMN_NAME_NOTE_SORT, sort);

            if (noteId > -1) {//原本存在,则更新
                dbWrite.update(NotesConfig.TABLE_NAME_NOTES, cv, NotesConfig.COLUMN_NAME_ID + "=?", new String[]{noteId + ""});
            } else {//原本不存在,就插入
                dbWrite.insert(NotesConfig.TABLE_NAME_NOTES, null, cv);
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * popupWindow内的选项被点击
     * @param v
     */
    @Override
    public void onClick(View v) {
        popWindow.dismiss();
        switch (v.getId()) {
            case R.id.item_all_sort:
                sort = 0;
                tvSortItem.setText(sortName[0]);

                break;
            case R.id.item_work_sort:
                sort = 1;
                tvSortItem.setText(sortName[1]);
                break;
            case R.id.item_study_sort:
                sort = 2;
                tvSortItem.setText(sortName[2]);
                break;
            case R.id.item_life_sort:
                sort = 3;
                tvSortItem.setText(sortName[3]);
                break;
        }

        /*显示选中状态图标*/
        checkWhat(sort);
    }

    /**
     * 被选中的item显示选中图标
     * @param check
     */
    private void checkWhat(int check){

        ivAllCheck.setVisibility(View.INVISIBLE);
        ivWorkCheck.setVisibility(View.INVISIBLE);
        ivStudyCheck.setVisibility(View.INVISIBLE);
        ivLifeCheck.setVisibility(View.INVISIBLE);

        switch (check){
            case 0:
                ivAllCheck.setVisibility(View.VISIBLE);
                break;
            case 1:
                ivWorkCheck.setVisibility(View.VISIBLE);
                break;
            case 2:
                ivStudyCheck.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivLifeCheck.setVisibility(View.VISIBLE);
                break;
        }
    }

}