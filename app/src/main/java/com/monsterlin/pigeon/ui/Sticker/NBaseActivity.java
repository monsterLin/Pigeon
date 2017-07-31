package com.monsterlin.pigeon.ui.Sticker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * 封装的公共方法
 *
 */
public class NBaseActivity extends AppCompatActivity {
//    private SQLiteDatabase db;
//    private NoteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dbHelper=new NoteDbHelper(this);
    }

    /**
     * 显示Toast
     * @param msg 要显示的信息
     */
    protected void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳到另一个页面
     * @param cls
     */
    public void nextActivity(Class cls) {
        nextActivity(cls, null);
    }

    /**
     * 带数据包的跳转
     * @param cls
     * @param bundle
     */
    public void nextActivity(Class cls, Bundle bundle) {
        Intent i = new Intent(this, cls);
        if (bundle != null)
            i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * 待返回值的跳转另一个页面
     * @param cls
     * @param bundle
     * @param requsetCode
     */
    public void nextActivityForResult(Class cls,Bundle bundle, int requsetCode){
        Intent intent = new Intent(this,cls);
        if (null != bundle){
            intent.putExtras(bundle);
        }

        startActivityForResult(intent,requsetCode);
    }

    /**
     * 初始化ToolBar
     * @param toolbar
     * @param title
     */
    public void initToolBar(Toolbar toolbar , String title, boolean isBack){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (isBack){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
}
