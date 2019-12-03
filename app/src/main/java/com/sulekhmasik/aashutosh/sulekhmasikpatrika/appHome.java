package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;

public class appHome extends appLayout implements SwipeRefreshLayout.OnRefreshListener{
    public View parView;
    private static ArrayList<CardData> data;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private String fileDir;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_app_home, fl);
        makeFolders();
        parView = getLayoutInflater().inflate(R.layout.activity_app_home, fl);
        fileDir = this.getFilesDir().getPath();
        refreshAction();
    }

    private void refreshAction() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) parView.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                downloadData();
            }
        });
    }
    @Override
    public void onRefresh(){
        data.clear();
        downloadData();
    }
    private void downloadData() {
        File folder = new File(fileDir,"json/");
        final File file = new File(folder, "recent.json");
        if (file.exists() && file.length()>0){
            loadTitles(file);
            if (isNetworkAvailable()){
                mSwipeRefreshLayout.setRefreshing(true);
                Ion.with(this)
                        .load("http://sulekhmasik.com.np/wp-json/wp/v2/posts?per_page=15")
                        .write(file)
                        .setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File res) {
                                loadTitles(res);
                            }
                        });
            }
        }else {
            if (!isNetworkAvailable()) {
                TextView tv = (TextView) findViewById(R.id.recent);
                tv.setText("Check Your Internet Connection");
                mSwipeRefreshLayout.setRefreshing(false);
                return;
            } else {
                TextView tv = (TextView) findViewById(R.id.recent);
                tv.setText("Recent Posts");
                mSwipeRefreshLayout.setRefreshing(true);
                Ion.with(this)
                        .load("http://sulekhmasik.com.np/wp-json/wp/v2/posts?per_page=15")
                        .write(file)
                        .setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File res) {
                                loadTitles(res);
                            }
                        });
            }
        }
    }

    private void loadTitles(File res) {
        Ion.with(this)
                .load(res)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        loadTitles1(result);
                    }
                });
    }

    public void makeFolders(){
        File file = new File(this.getFilesDir(), "posts");
        file.mkdirs();
        file = new File(this.getFilesDir(),"media");
        file.mkdirs();
        file = new File(this.getFilesDir(),"media/thumbnails");
        file.mkdirs();
        file = new File(this.getFilesDir(), "json");
        file.mkdirs();
    }

    public void loadTitles1(final JsonArray result){
        try{
            data = new ArrayList<CardData>();
            for (JsonElement i: result){
                final String t = i.getAsJsonObject().getAsJsonObject("title").get("rendered").getAsString();
                final int id = i.getAsJsonObject().get("id").getAsInt();
                String exc_temp = i.getAsJsonObject().getAsJsonObject("excerpt").get("rendered").getAsString();
                exc_temp = android.text.Html.fromHtml(exc_temp).toString();
                final String exc = exc_temp.substring(0,exc_temp.length()-12);
                final String featuredMedia = i.getAsJsonObject().getAsJsonObject("_links").getAsJsonArray("wp:featuredmedia").get(0).getAsJsonObject().get("href").getAsString();
                String content = i.getAsJsonObject().getAsJsonObject("content").get("rendered").getAsString();
                final String contentFiltered = android.text.Html.fromHtml(content).toString();
                File folder = new File(fileDir,"media/thumbnails/");
                final File file = new File(folder,id+ ".json");
                if (!isNetworkAvailable()){
                    if (!file.exists() || file.length()==0){
                        mSwipeRefreshLayout.setRefreshing(false);
                        TextView tv = (TextView) findViewById(R.id.recent);
                        tv.setText("Check Your Internet Connection");
                        return;
                    }
                    else{
                        loadTitles2(t,id,exc,contentFiltered,file,result.size());
                    }
                }
                else{
                    if (file.exists() && file.length()!=0){
                        loadTitles2(t,id,exc,contentFiltered,file,result.size());
                    }
                    Ion.with(this)
                            .load(featuredMedia)
                            .write(file)
                            .setCallback(new FutureCallback<File>() {
                                @Override
                                public void onCompleted(Exception e, File f) {
                                    loadTitles2(t,id,exc,contentFiltered,f,result.size());
                                }
                            });
                }
            }
        }catch (Exception jsone){
            //log(jsone);
        }
    }

    private void loadTitles2(final String t, final int id, final String exc, final String content, File f, final int size) {
        Ion.with(this)
                .load(f)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject mediaObject) {
                        String img = mediaObject.getAsJsonObject("guid").get("rendered").getAsString();
                        int img_id = mediaObject.get("id").getAsInt();
                        data.add(new CardData(t,id,exc,img,img_id,content));
                        if (data.size()==size){addCards();}
                    }
                });
    }

    public void addCards(){
        RecyclerView recyclerView = (RecyclerView) parView.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.removeAllViews();
        RecyclerView.Adapter radapter = new AttachAdapter(data,this,true);
        recyclerView.setAdapter(radapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }
}