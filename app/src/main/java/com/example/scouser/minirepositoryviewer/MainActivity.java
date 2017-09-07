package com.example.scouser.minirepositoryviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private List<GitHub> gitList = new ArrayList<>();
    List<Integer> strArray = new ArrayList<Integer>();
    RealmList<GitHub> vash = new RealmList<GitHub>();
    private RecyclerView recyclerView;
    private GitHubAdapter ghAdapter;
    String Jsonresponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.view);

        ghAdapter = new GitHubAdapter(vash);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(ghAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new MainActivity.ClickListener() {

            public void onClick(View view, int position) {
                Intent intent = new Intent(getBaseContext(), Info.class);
                intent.putExtra("data", strArray.get(position).toString());
                startActivity(intent);
            }

            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), " Do not press long Click!!!", Toast.LENGTH_SHORT).show();

            }
        }));

        getGitHubData();


    }


    private void getGitHubData() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String url = "https://api.github.com/search/repositories?sort=stars&order=desc&q=created:" + dateFormat.format(calendar.getTime()).toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("*********GEtting*****");
                System.out.println("response" + response);
                System.out.println("*********ENDING*****");
                Jsonresponse = response;
                strArray=new ArrayList<Integer>();
                try {

                    //Tranform the string into a json object

                    final JSONArray routeArray = new JSONObject(Jsonresponse).getJSONArray("items");

                    for (int i = 0; i < routeArray.length(); i++) {
                        JSONObject object = routeArray.getJSONObject(i);
                        String full_name = object.getString("full_name");
                        String name = object.getString("name");
                        int id = object.getInt("id");
                        JSONObject owner = object.getJSONObject("owner");
                        String login = owner.getString("login");
                        String url = owner.getString("url");
                        String type = owner.getString("type");
                        String avatar=owner.getString("avatar_url");

                        GitHub glist = new GitHub(id, name, full_name, login, url, type,avatar);
                        gitList.add(glist);
                    }


                    // Initialize Realm (just once per application)
                    Realm.init(getApplicationContext());


                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(gitList);
                    realm.commitTransaction();

                    RealmResults<GitHub> realmResultList = realm.where(GitHub.class)
                            .findAll();

                    vash.addAll(realmResultList.subList(0, realmResultList.size()));

                    for(int j=0;j<realmResultList.size();j++){
                        strArray.add(realmResultList.get(j).getID());
                    }

                    ghAdapter.notifyDataSetChanged();

                } catch (JSONException e) {

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }
        });


        queue.add(stringRequest);


    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


}

