package com.example.purushotham.vollyexample;

import android.app.ProgressDialog;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    InterstitialAd mInterstitialAd;
    RecyclerView recyclerView;
    String URL="http://cricapi.com/api/matches?apikey=4onDaQRbAhXBJkOuZcXUetTJwj23";
    List<Model> match_list;
    MyAdapter myAdapter;
    RequestQueue requestQueue;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.mysearch);





        recyclerView=findViewById(R.id.rcv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*DividerItemDecoration itemDecor = new DividerItemDecoration(getBaseContext(), HORIZONTAL);
        recyclerView.addItemDecoration(itemDecor);*/


        match_list=new ArrayList<>();
       // myAdapter=new MyAdapter(match_list,this);
        requestUrl();


        //String sample_url="https://pixabay.com/api/?key=12811730-99a3594d497feaa652b94f929&q=yellow+flowers&image_type=photo&pretty=true";

    }

    private void requestUrl()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading..");
        progressDialog.show();
        final StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();

                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("matches");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        String match_status="";
                        String team1=jsonArray.getJSONObject(i).getString("team-1");
                        String team2=jsonArray.getJSONObject(i).getString("team-2");
                        String status=jsonArray.getJSONObject(i).getString("matchStarted");
                        String match_type=jsonArray.getJSONObject(i).getString("type");
                        String date=jsonArray.getJSONObject(i).getString("date");
                      //  Log.d(TAG, "onResponse: Datae"+date);

                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date Date_test = inputFormat.parse(date);
                        String formattedDate = outputFormat.format(Date_test);




                        String dateTimeGMT=jsonArray.getJSONObject(i).getString("dateTimeGMT");

                        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        fmt.setTimeZone(TimeZone.getTimeZone(dateTimeGMT));
                        Date date1=fmt.parse(dateTimeGMT);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String c_date=simpleDateFormat.format(date1);
                        TimeZone timeZone=simpleDateFormat.getTimeZone();
                        Log.d(TAG, "onResponse: Time"+timeZone);



                        String id=jsonArray.getJSONObject(i).getString("unique_id");

                        if(status.equals("true"))
                        {
                            match_status="Started";

                        }
                        else {
                            match_status="Not started";

                        }


                        Model model=new Model(id,team1,team2,formattedDate,match_status,match_type);
                        match_list.add(model);

                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                myAdapter=new MyAdapter(match_list,MainActivity.this);
                recyclerView.setAdapter(myAdapter);




            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


         super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.mysearch,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(v.callOnClick())
                {
                    Log.d(TAG, "onTouch: ");
                    Toast.makeText(MainActivity.this, "onTouch", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: ");
                mInterstitialAd.show();
                myAdapter.getFilter().filter(s);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         super.onOptionsItemSelected(item);
         int itemId=item.getItemId();
         if(itemId==R.menu.mysearch)
         {
             Log.d(TAG, "onOptionsItemSelected: Search Icon");
         }



        return true;
    }
}
