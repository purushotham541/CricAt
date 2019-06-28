package com.example.purushotham.vollyexample;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayersDetails extends AppCompatActivity
{
    TextView Name_team1,Name_team2,plyr1,plyr2;
    RequestQueue requestQueue;
    String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_details);
        Name_team1=findViewById(R.id.team1_tv1);
        Name_team2=findViewById(R.id.team2_tv2);
        plyr1=findViewById(R.id.plyr1);
        plyr2=findViewById(R.id.plyr2);

        requestQueue=Volley.newRequestQueue(this);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String id=bundle.getString("uid");
        String Url="http://cricapi.com/api/fantasySquad/?apikey=4onDaQRbAhXBJkOuZcXUetTJwj23&unique_id="+id;
        Log.e(TAG,Url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("squad");
                    JSONObject json0=jsonArray.getJSONObject(0);
                    JSONObject json1=jsonArray.getJSONObject(1);



                    String team1=jsonArray.getJSONObject(0).getString("name");
                    String team2=jsonArray.getJSONObject(1).getString("name");
                    Name_team1.setText(team1);
                    Name_team2.setText(team2);
                    JSONArray team1_players=json0.getJSONArray("players");
                    JSONArray team2_players=json0.getJSONArray("players");
                    for(int i=0;i<team1_players.length();i++)
                    {
                        String tm1_plyr_nme=team1_players.getJSONObject(i).getString("name");
                        plyr1.append(i+1+". "+tm1_plyr_nme+""+"\n");

                    }
                    for(int i=0;i<team2_players.length();i++)
                    {
                        String tm1_plyr_nme=team2_players.getJSONObject(i).getString("name");
                        plyr2.append(i+1+". "+tm1_plyr_nme+""+"\n");


                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        requestQueue.add(stringRequest);

    }
}
