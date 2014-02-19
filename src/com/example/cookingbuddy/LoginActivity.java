package com.example.cookingbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	public TextView countBox;
	public TextView userBox;
	public String username;
	public int count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		userBox = (TextView) findViewById(R.id.textView1);
		countBox = (TextView) findViewById(R.id.textView2);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		count = intent.getIntExtra("count",0);
		userBox.setText("Welcome " + username);
		countBox.setText("You have logged in " + count + " times!");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void logout(View view){
		finish();
	}
	

}
