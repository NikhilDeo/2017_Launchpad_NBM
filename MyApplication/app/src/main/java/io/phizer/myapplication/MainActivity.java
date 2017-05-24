package io.phizer.myapplication;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String mPath;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        if (extras!=null&&extras.containsKey("path")&&!extras.getString("path").equals("Home")){
            mPath=extras.getString("path");
        } else {
            mPath=getFilesDir().getPath();
        }
        init();
    }
    public void init(){
        TextView mytv = (TextView) findViewById(R.id.textview);
        mytv.setText(mPath.replace(getFilesDir().getPath(),"Home"));
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPath=mPath.substring(0,mPath.lastIndexOf('/'));
                init();
            }
        });
        if(mytv.getText().equals("Home")){
            b.setVisibility(View.VISIBLE);
        } else {
            b.setVisibility(View.VISIBLE);
        }
        Button cam = (Button) findViewById(R.id.button5);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CameraActivity.class);
                i.putExtra("path",mPath);
                startActivity(i);
            }
        });

        List<String> data = (new ContentScraper(mPath)).getFiles();

        final ListView lv = (ListView) findViewById(R.id.listview);
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.constraint);
        cl.setBackgroundColor(Color.WHITE);
        lv.setAdapter(new FileAdapter(this, data,mPath));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(lv.getItemIdAtPosition(position)==0) {
                    mPath+="/"+lv.getItemAtPosition(position);
                    init();
                } else if (lv.getItemIdAtPosition(position)==1) {
                    Intent i = new Intent(MainActivity.this, PhotoViewer.class);
                    //i.putExtra("name",((String)lv.getItemAtPosition(position)).substring(0,((String)lv.getItemAtPosition(position)).length()-4));
                    i.putExtra("name",(String)lv.getItemAtPosition(position));
                    i.putExtra("path",mPath);
                    startActivity(i);
                } else {
                    Intent i = new Intent(MainActivity.this, AddNewFolder.class);
                    i.putExtra("path",mPath);
                    startActivity(i);
                }
            }
        });
    }
}

