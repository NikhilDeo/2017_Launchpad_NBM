package io.phizer.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mv__ on 5/22/17.
 */

public class FileAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflator;
    private List<String> dataSource;
    private String mPath;
    public FileAdapter(Context context, List<String> items, String mPath) {
        mContext = context;
        this.inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dataSource = items;
        this.mPath = mPath;
    }

    public int getCount() {
        return dataSource.size()+1;
    }

    public Object getItem(int position) {
        return dataSource.get(position);
    }
    public long getItemId(int position) {
            if (position==dataSource.size()){
                return 2;
            } else if (isImage(position)) {
                return 1;
            } else {
                return 0;
            }
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position==dataSource.size()){
            Button b = new Button(mContext);
            b.setFocusable(false);
            b.setClickable(false);
            b.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setBackgroundColor(Color.BLACK);
            b.setTextColor(Color.WHITE);
            b.setText("ADD NEW FOLDER");
            return b;
        } else if (isImage(position)) {
            /*Button b = new Button(mContext);
            b.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    400));
            b.setBackgroundColor(Color.rgb(153, 204, 255));
            b.setText(dataSource.get(position).substring(0,dataSource.get(position).length()-4));
            b.setTextColor(Color.WHITE);
            b.setAllCaps(true);
            b.setFocusable(false);
            b.setClickable(false);
            return b;*/
            ImageView imageView = new ImageView(mContext);
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    300));
            //imageView.setAdjustViewBounds(true);
            //imageView.setPadding(4, 4, 4, 4);
            imageView.setClickable(true);
            imageView.setImageURI(Uri.parse(mPath+"/"+dataSource.get(position)));
            return imageView;
        } else {
            Button b = new Button(mContext);
            b.setFocusable(false);
            b.setClickable(false);
            b.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setBackgroundColor(Color.rgb(255, 204, 153));
            b.setText(dataSource.get(position));
            b.setTextColor(Color.WHITE);
            b.setAllCaps(true);
            return b;
        }
    }
    private boolean isImage(int position) {
        String fileName = dataSource.get(position);
        return (fileName.length()>4&&fileName.substring(fileName.length()-4).equals(".jpg"));
    }
}
