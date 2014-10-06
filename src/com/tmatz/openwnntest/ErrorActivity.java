package com.tmatz.openwnntest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ErrorActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
//        Intent intent = getIntent();
//    	Throwable exception = intent.getParcelableExtra("error");
//    	Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
	}
}
