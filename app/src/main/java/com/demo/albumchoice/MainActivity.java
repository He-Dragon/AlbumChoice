package com.demo.albumchoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, ImageSwitcherActivity.class), 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < data.getStringArrayListExtra("chioceIamge").size(); i++) {
                stringBuffer.append(data.getStringArrayListExtra("chioceIamge").get(i).toString()+"\n");
            }
            textView.setText(stringBuffer);
        }
    }
}
