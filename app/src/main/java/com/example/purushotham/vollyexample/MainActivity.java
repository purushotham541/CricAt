package com.example.purushotham.vollyexample;

import android.app.ProgressDialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;

public class MainActivity extends AppCompatActivity
{
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
                        String id=jsonArray.getJSONObject(i).getString("unique_id");
                        if(status.equals("true"))
                        {
                            match_status="Started";

                        }
                        else {
                            match_status="Not started";

                        }


                        Model model=new Model(id,team1,team2,date,match_status,match_type);
                        match_list.add(model);

                    }

                } catch (JSONException e)
                {
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);



        return true;
    }
}
