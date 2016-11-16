package com.demo.albumchoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by hecl on 2016/11/14.
 */

public abstract class GridViewAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;
    private ArrayList<Boolean> isCheck;//保存选中状态，解决错乱问题
    private ArrayList<String> choiceImagePath = new ArrayList<>();//选中图片保存的集合


    public GridViewAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        isCheck = new ArrayList<Boolean>();
        for (int i = 0; i < list.size(); i++) {
            isCheck.add(false);
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        String path = list.get(i);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.grid_group_item, null);
            viewHolder.mImageView = (ImageView) view.findViewById(R.id.mImageView);
            viewHolder.mCheckBox = (CheckBox) view.findViewById(R.id.mCheckBox);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choiceImagePath.size() < 6) {
                    if (viewHolder.mCheckBox.isChecked()) {
                        isCheck.set(i, true);
                        choiceImagePath.add(list.get(i).toString());
                        ImagePath(choiceImagePath);
                    } else {
                        isCheck.set(i, false);
                        choiceImagePath.remove(list.get(i).toString());
                        ImagePath(choiceImagePath);
                    }
                } else {
                    Toast.makeText(context, "最多选取6长图片", Toast.LENGTH_SHORT).show();
                    isCheck.set(i, false);
                    viewHolder.mCheckBox.setChecked(false);
                }
            }
        });
        Glide.with(context).load(path).into(viewHolder.mImageView);
        if (viewHolder.mCheckBox.isChecked()) {
            viewHolder.mCheckBox.setChecked(true);
        } else {
            viewHolder.mCheckBox.setChecked(false);

        }
        viewHolder.mCheckBox.setChecked(isCheck.get(i));

        return view;
    }

    public class ViewHolder {
        private ImageView mImageView;
        private CheckBox mCheckBox;
    }

    protected abstract void ImagePath(ArrayList<String> choiceImagePath);

}
