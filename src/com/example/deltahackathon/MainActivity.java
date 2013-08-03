package com.example.deltahackathon;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String result;
	Customview cv1;
	Customview cv2;
	Customview cv3;
	int lon;
	RelativeLayout rl;
	String[][] vals;
	int[][] loc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loaddata();
	}

	public void loaddata()
	{
		final class Retrievedata extends AsyncTask<String, Void, String[][]>
		{
			
			protected String[][] doInBackground(String... urls)
			{
				for(String url:urls)
				{
					 HttpGet httpget = new HttpGet(url);
					 HttpResponse response;
					 HttpClient httpclient = new DefaultHttpClient();
					 try
					 {
						 response=httpclient.execute(httpget);
						 HttpEntity entity= response.getEntity();
						 String data[][];
						 result=EntityUtils.toString(entity);
						 data=parsejson(result);
						 if(data==null)
						 {
							 Log.e("-----------","parsejson error!");
						 }
						 else
						 {
							 return data;
						 }
					 }
					 catch (Exception e) 
					{
						 e.printStackTrace();
				    }
				}
			return null;
			}
			
			@Override
			protected void onPostExecute(String[][] result)
			{
				if(result==null)
				{
					Toast.makeText(MainActivity.this, "Error Parsing Json!", Toast.LENGTH_LONG).show();
				}
				else
				{
					cv1=(Customview) findViewById(R.id.custView1);
					cv1= new Customview(MainActivity.this,result,loc);
					setContentView(cv1);
				}
					
			}
		}
		Retrievedata retdata= new Retrievedata();
		retdata.execute(new String[]{"http://10.0.2.2:8080"});
	}
	
	public String[][] parsejson(String data)
	{
		try
		{
			JSONObject jObj= new JSONObject(data);
			if(jObj==null) Log.i("-----------","JOBJ IS NULL");
			JSONArray jObjArr= jObj.optJSONArray("data");
			if(jObjArr==null)
			{
				Log.i("-----------","JOBJARR IS NULL"); 
				return null;
			}
			lon = jObjArr.length();
			vals=new String[lon][2];
			loc= new int[lon][2];
			for(int i=0;i<lon;i++)
			{
				JSONObject temp = jObjArr.optJSONObject(i);
				vals[i][0]=temp.getString("name");
				vals[i][1]=temp.getString("background");
				loc[i][0]=temp.getInt("locationx");
				loc[i][1]=temp.getInt("locationy");
			}
			return vals;
		}
		catch(Exception e)
		{
			Log.i("Exception1 >> ", e.toString());
		}	
		return null;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}