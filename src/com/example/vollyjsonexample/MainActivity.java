package com.example.vollyjsonexample;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity {

	ListView videoList;
	ArrayList<String> videoArray = new ArrayList<String>();
	String feedUrl = "https://gdata.youtube.com/feeds/api/users/twistedequations/uploads?v=2&alt=jsonc&start-index=1&max-results=30";
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);
		videoList = (ListView) findViewById(R.id.videoList);		
		final ArrayAdapter<String> videoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videoArray);
		videoList.setAdapter(videoAdapter);
		
		RequestQueue rq = Volley.newRequestQueue(this);
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, feedUrl, null, 
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try 
						{	
							JSONArray videos = response.getJSONObject("data").getJSONArray("items");
							for(int i=0; i<videos.length(); i++)
							{
								videoArray.add(videos.getJSONObject(i).getString("title"));
							}
						} 
						catch (JSONException e) 
						{
							
						}
						
						videoAdapter.notifyDataSetChanged();
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
						
					}
				});
		rq.add(jsonRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
