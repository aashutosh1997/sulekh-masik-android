package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class articlePage
        extends CollapsingLayout {
    public View parView;
    CardData d;
    public Menu m;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parView = getLayoutInflater().inflate(R.layout.activity_article_read, fl);
        Intent in = getIntent();
        d = (CardData) in.getParcelableExtra("data");
        onCreateOptionsMenu(m);
        display(d.getId(),d.getTitle(),d.getContent(),d.getImg(),d.getImgId());
    }

    public void display(int id, String t, String content, String img, int img_id) {

        View lay = getLayoutInflater().inflate(R.layout.articledisplay, null);

        TextView tv = lay.findViewById(R.id.article_title_main);
        tv.setText(t);

        tv = lay.findViewById(R.id.article_content);
        tv.setText(content);


        LinearLayout ll = parView.findViewById(R.id.articleView);
        ll.addView(lay);
        ImageView imv = findViewById(R.id.htab_header);
        File folder = new File(this.getFilesDir(), "media/");
        final File file = new File(folder, img_id+".jpg");
        Bitmap bmp = BitmapFactory.decodeFile(file.toString());
        imv.setImageBitmap(bmp);
        }


    @Override
    public void onBackPressed() {
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try{
            getMenuInflater().inflate(R.menu.actions, menu);
            m = menu;
            MenuItem shareItem = menu.findItem(R.id.action_share);
            ShareActionProvider myShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
            Intent myShareIntent = new Intent(Intent.ACTION_SEND);
            myShareIntent.setType("text/plain");
            String url = "http://sulekhmasik.com.np/posts?p="+d.getId();
            myShareIntent.putExtra(Intent.EXTRA_TEXT,url);
            myShareActionProvider.setShareIntent(myShareIntent);
        }catch (Exception e){}
        return true;
    }

}