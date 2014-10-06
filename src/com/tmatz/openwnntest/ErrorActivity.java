package com.tmatz.openwnntest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textView = (TextView)findViewById(R.id.text_view);
        Intent intent = getIntent();
        String message = intent.getStringExtra("ex_message");
        textView.append("\n" + message);
	}
}
