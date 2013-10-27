package me.monnand.uniqush.demo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import org.uniqush.android.MessageCenter;

public class MainActivity extends Activity {
	static final private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			MessageCenter.init(this, "me.monnand.uniqush.demo.UserInfoProvider");
		} catch (Exception e) {
			Log.wtf(TAG, e.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
