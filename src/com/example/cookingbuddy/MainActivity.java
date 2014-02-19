package com.example.cookingbuddy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity  {
	
	
	public String username;
	public String password;
	public Context context;
	public int errorcode;
	public int count;
	public TextView message;
	public String user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		message = (TextView) findViewById(R.id.textView3);
		context = this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void getInfo(){
		EditText userfield = (EditText) findViewById(R.id.userfield);
		EditText passfield = (EditText) findViewById(R.id.passfield);
		username = userfield.getText().toString();
		password = passfield.getText().toString();
	}
	
	public void login(){
		//Check username and password against database
		
		//Send to login screen
	}
	
	public void add(View view){
		getInfo();
		DataTransferTask data = new DataTransferTask();
		String t = "http://mysterious-woodland-6699.herokuapp.com/users/add";
		data.execute(t);
		
		//Send to login screen
	}
	
	public void login(View view){
		getInfo();
		DataTransferTask data = new DataTransferTask();
		String t = "http://mysterious-woodland-6699.herokuapp.com/users/login";
		data.execute(t);
		
		//Send to login screen
	}
	
	private class DataTransferTask extends AsyncTask<String, Integer, String>{

		

		@Override
		protected String doInBackground(String... arg0) {
			
			String jsonOutput = "-100";
			
			try {
				String address = arg0[0];
				Log.d("Address",address);
				URL url = new URL(address);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
				urlConnection.setUseCaches (false);
				urlConnection.setChunkedStreamingMode(0);
				urlConnection.setRequestProperty("Content-Type","application/json");
				urlConnection.connect();
				JSONObject jsonParam = new JSONObject();
				jsonParam.put("user",username);
				jsonParam.put("password",password);
				byte[] outputBytes = jsonParam.toString().getBytes("UTF-8");
				OutputStream os = urlConnection.getOutputStream();
				os.write(outputBytes);
				os.flush();
				os.close();
				InputStream in = urlConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder build = new StringBuilder();
				String placeHolder;
				while ((placeHolder = reader.readLine()) != null){
					build.append(placeHolder);
				}
				jsonOutput = build.toString();
				Log.d("JSON",jsonOutput);
				//JSON Object created

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonOutput;
		}
		
		protected void onPostExecute(String result) {
			Log.d("onPostExecute",result);
			JSONObject jobject;
			try {
				jobject = new JSONObject(result);
				errorcode = Integer.parseInt(jobject.getString("errCode"));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (errorcode == 1){
				try {
					jobject = new JSONObject(result);
					count = Integer.parseInt(jobject.getString("count"));
					Intent intent = new Intent(context,LoginActivity.class);
					intent.putExtra("count", count);
					intent.putExtra("username",username);
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if (errorcode == -1) {
				message.setText("Bad Credentials");
			}
			else if (errorcode == -2){
				message.setText("User already exists");
			}
			else if (errorcode == -3){
				message.setText("Invalid username");
			}
			else if (errorcode == -4){
				message.setText("Invalid password");
			}
	     }
		
	}
	
	

}