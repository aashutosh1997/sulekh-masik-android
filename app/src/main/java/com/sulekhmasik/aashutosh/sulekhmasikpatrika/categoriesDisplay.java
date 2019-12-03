package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;

public class categoriesDisplay extends appLayout{
    public View parView;
    public int parent;
    public String exclude;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        parent = i.getIntExtra("category",0);
        exclude = i.getStringExtra("exclude");
        parView = getLayoutInflater().inflate(R.layout.activity_categories, fl);
        downloadFile();
    }

    private void downloadFile() {
        File folder = new File(this.getFilesDir(),"json");
        File file = new File(folder,parent+".json");
        if (!isNetworkAvailable()){
            if (file.length()==0){
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
                return;
            }
            else{
                loadFile(file);
            }
        }
        else{
            Ion.with(this)
                    .load("http://sulekhmasik.com.np/wp-json/wp/v2/categories?per_page=100&exclude="+exclude+"&orderby=slug&parent="+parent)
                    .write(file)
                    .setCallback(new FutureCallback<File>() {
                        @Override
                        public void onCompleted(Exception e, File result) {
                            loadFile(result);
                        }
                    });
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void loadFile(File f) {
        Ion.with(this)
                .load(f)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        loadCategories(result);
                    }
                });
    }
    public void loadCategories(JsonArray arr){
        try{
            for (JsonElement i: arr){
                final int cat_parent = i.getAsJsonObject().get("parent").getAsInt();
                if (cat_parent == parent){
                    String cat_name = i.getAsJsonObject().get("name").getAsString();
                    String cat_slug = i.getAsJsonObject().get("slug").getAsString();
                    final int cat_id = i.getAsJsonObject().get("id").getAsInt();
                    Button b = addWid(cat_name,cat_id);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent in;
                            if(parent==14)
                                in = new Intent(categoriesDisplay.this,categoriesDisplay.class);
                            else
                                in = new Intent(categoriesDisplay.this,articleDisplay.class);
                            in.putExtra("category",cat_id);
                            in.putExtra("exclude","0");
                            startActivity(in);
                        }
                    });
                }

            }
        }catch(Exception e){}
    }
    public Button addWid(String name,int id){
        View lay = getLayoutInflater().inflate(R.layout.clickme,null);

        TextView tv = lay.findViewById(R.id.clickMeText);
        tv.setText(name);

        Button b = lay.findViewById(R.id.clickMeButt);
        b.setText("Read articles from" + name);



        GridLayout gl = parView.findViewById(R.id.publication_buttons);
        gl.addView(lay);
        return(b);
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
