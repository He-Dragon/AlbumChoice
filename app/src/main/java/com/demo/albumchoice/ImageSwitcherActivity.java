package com.demo.albumchoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hecl on 2016/11/14.
 */

public class ImageSwitcherActivity extends Activity {

    private GridView mGridView;
    private ArrayList<String> allImageList = new ArrayList<>();
    private ArrayList<String> choiceImagePath = new ArrayList<>();
    private Cursors cursors;
    private TextView backTv, numberTv, confirmTv;
    private GridViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);
        mGridView = (GridView) findViewById(R.id.mGridView);
        backTv = (TextView) findViewById(R.id.backTv);
        numberTv = (TextView) findViewById(R.id.numberTv);
        confirmTv = (TextView) findViewById(R.id.confirmTv);
        cursors = new Cursors(this) {
            @Override
            protected void getParentList(ArrayList<ImageBean> parentList) {
                mGridView.setAdapter(new GridViewAdapter(ImageSwitcherActivity.this, getAllImagePath(parentList)) {
                    @Override
                    protected void ImagePath(ArrayList<String> choiceImagePath) {
                        //选中图片的集合
                        ImageSwitcherActivity.this.choiceImagePath = choiceImagePath;
                        numberTv.setText(choiceImagePath.size() + "/6");
                    }
                });

            }
        };
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //这里可以写查看大图的方法
            }
        });
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("chioceIamge",choiceImagePath);
                intent.setClass(ImageSwitcherActivity.this,MainActivity.class);
                setResult(120,intent);
                finish();
            }
        });

    }

    /**
     * 取出所有图片放到allImageList集合里面
     */
    private ArrayList<String> getAllImagePath(ArrayList<ImageBean> parentList) {
        for (int i = 0; i < parentList.size(); i++) {
            allImageList.addAll(parentList.get(i).getImageList());
        }
        return allImageList;
    }

}
