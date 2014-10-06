package com.tmatz.openwnntest;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ExceptionHandler implements UncaughtExceptionHandler {
	public static ExceptionHandler register(Context context)
	{
		UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
		if (handler instanceof ExceptionHandler)
		{
			return (ExceptionHandler)handler;
		}
		else
		{
			ExceptionHandler newHandler = new ExceptionHandler(handler, context);
			Thread.setDefaultUncaughtExceptionHandler(newHandler);
			return newHandler;
		}
	}

	private final Context mContext;
	private final UncaughtExceptionHandler mHandler;
	
	private ExceptionHandler(UncaughtExceptionHandler handler, Context context)
	{
		mHandler = handler;
		mContext = context;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Toast.makeText(mContext, "caught exception", Toast.LENGTH_SHORT).show();
	    Intent i = new Intent(mContext, ErrorActivity.class);
	    //i.putExtra("error", ex);
		PendingIntent pi = PendingIntent.getActivity(mContext, 0, i, 0);
	    AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pi);
        System.exit(2); 
	}
}
