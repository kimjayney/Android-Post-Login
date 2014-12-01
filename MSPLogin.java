package com.example.testmspone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.testmspone.MainActivity.HttpAsyncTask;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class MSPLogin extends ActionBarActivity {
	EditText txtID;
	EditText txtPW;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msplogin);
		txtID = (EditText) findViewById(R.id.txtID);
		txtPW = (EditText) findViewById(R.id.txtPW);
		if (android.os.Build.VERSION.SDK_INT > 9) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()

			.permitAll().build();

			StrictMode.setThreadPolicy(policy);

		}
		Button Submit = (Button)this.findViewById(R.id.btnSubmit);
		Submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String ID = txtID.getText().toString();
					String PW = txtPW.getText().toString();
					String enc = sha1(PW);
					Toast.makeText(getBaseContext(), "ID " + ID + " , PW : " +enc, Toast.LENGTH_LONG).show();
					
					makePostRequest(ID,enc);
					
				} catch(Exception ex) {
					Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
				}
			} 
			
		});
}
	
	
	 private void makePostRequest(String ID, String PW) throws NoSuchAlgorithmException {
		 
         
	        HttpClient httpClient = new DefaultHttpClient();
	                                // replace with your url
	        HttpPost httpPost = new HttpPost("URL");

	        //Post Data
	        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
	        nameValuePair.add(new BasicNameValuePair("id", ID));
	        nameValuePair.add(new BasicNameValuePair("pw", PW));
	        
	        //Encoding POST data
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
	        } catch (UnsupportedEncodingException e) {
	            // log exception
	            e.printStackTrace();
	        }
	 
	        //making POST request.
	        try {
	            HttpResponse response = httpClient.execute(httpPost);
	            // write response to log
	            HttpEntity resEntity = response.getEntity();
	            
//	            Toast.makeText(getBaseContext(), EntityUtils.toString(resEntity), Toast.LENGTH_LONG).show();
	            String responseR = EntityUtils.toString(resEntity);
	            String work = responseR.trim();
	            String work2 = work.replace(System.getProperty("line.separator"), ""); 
	            if (responseR.indexOf("Login") > -1) {
	            	final Intent i = new Intent(this, MainActivity.class);
			        startActivity(i);
			        
	            } else {	            	
	            	Toast.makeText(getBaseContext(), work2, Toast.LENGTH_LONG).show();
	            }
	            
	        } catch (ClientProtocolException e) {
	            // Log exception
	            e.printStackTrace();
	        } catch (IOException e) {
	            // Log exception
	            e.printStackTrace();
	        }
	 
	    }
	static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.msplogin, menu);
		return true;
	}
	
	@Override
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
