package com.example.purushotham.vollyexample;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MatchDetails extends AppCompatActivity {

    TextView team1,team2,score,date,status,match_type;
    String URL="http://cricapi.com/api/cricketScore/?apikey=4onDaQRbAhXBJkOuZcXUetTJwj23&unique_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
       /* ActionBar actionBar=getSupportActionBar();

        actionBar.setTitle("Match details..");
        actionBar.setDisplayHomeAsUpEnabled(true);*/



        team1=findViewById(R.id.tm1);
        team2=findViewById(R.id.tm2);
        score=findViewById(R.id.score);
        date=findViewById(R.id.m_dte);
        status=findViewById(R.id.m_sts);
        match_type=findViewById(R.id.m_tpe);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String uid=bundle.getString("uid");
        final String m_date=bundle.getString("date");
        String Url=URL+uid;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String mstatus=jsonObject.getString("stat");
                    String mteam1=jsonObject.getString("team-1");
                    String mteam2=jsonObject.getString("team-2");
                    String mscore=jsonObject.getString("score");
                    String mdescrptn=jsonObject.getString("description");
                    status.setText(mstatus);
                    team1.setText(mteam1);
                    team2.setText(mteam2);
                    score.setText(mscore);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
