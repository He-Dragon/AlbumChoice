package com.demo.albumchoice;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hecl on 2016/11/14.
 */

public abstract class Cursors {
    private Context context;
    private Cursor cursor;
    private static final int HANDLE_OK = 10;
    private HashMap<String, List<String>> hashMap = new HashMap<>();//储存图片的路径  以及图片的父文件的名字
    private ProgressDialog mProgressDialog;//读取图片进度
    private ArrayList<ImageBean> parentList = new ArrayList<>();

    public Cursors(Context context) {
        this.context = context;
        cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null
                , null, null, null);
//        getData();
        mProgressDialog = ProgressDialog.show(context, null, "加载中。。。。");
        /**
         * 用Rxjava的思想来异步查询手机里面的图片
         * */
        Observable.just("异步")
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(new Func1<String, HashMap<String, List<String>>>() {
                    @Override
                    public HashMap<String, List<String>> call(String s) {
                        return getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HashMap<String, List<String>>>() {
                    @Override
                    public void call(HashMap<String, List<String>> stringListHashMap) {
                        mProgressDialog.dismiss();
                        Iterator<Map.Entry<String, List<String>>> iterator = stringListHashMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, List<String>> entry = iterator.next();
                            String key = entry.getKey();
                            List<String> value = entry.getValue();
                            ImageBean bean = new ImageBean();
                            bean.setImageList((ArrayList) value);
                            bean.setImageSize(value.size());
                            bean.setParentName(key);
                            parentList.add(bean);
                        }
                        getParentList(parentList);
                    }
                });
    }

    private HashMap<String, List<String>> getData() {
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            //获取图片路径
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String imagePath = new String(data, 0, data.length - 1);//获取图片的路径
            String parentPath = new File(imagePath).getParentFile().getName();//获取储存图片路径的父文件夹的名字
            /**
             * 在hashMap中分类储存同一个文件夹下面的图片
             * */
            if (hashMap.containsKey(parentPath)) {//如果hashMap中存在有这个文件夹就直接把图片信息放进去
                hashMap.get(parentPath).add(imagePath);
            } else {
                List<String> imageList = new ArrayList<>();
                imageList.add(imagePath);
                hashMap.put(parentPath, imageList);
            }
        }
        cursor.close();
        return hashMap;
    }


    /**
     * 获取数据完了后回调
     */
    protected abstract void getParentList(ArrayList<ImageBean> parentList);

}
