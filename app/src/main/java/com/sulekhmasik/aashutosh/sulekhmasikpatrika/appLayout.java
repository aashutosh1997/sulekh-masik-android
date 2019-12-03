package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

public class appLayout extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawer;
    protected LinearLayout fl;
    protected NavigationView navigationView;
    private static boolean isLaunch = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_layout);
        fl = findViewById(R.id.content_frame);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        enableActionBar();
        drawer.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    protected void enableActionBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider myShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("text/plain");
        myShareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.sulekhmasik.com.np");
        try {
            myShareActionProvider.setShareIntent(myShareIntent);
        }catch (Exception e){}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_share:

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            navigationView.setCheckedItem(id);
            Intent in = new Intent(this, appHome.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(in);
        } else if (id == R.id.nav_publications) {
            navigationView.setCheckedItem(id);
            Intent in = new Intent(this, categoriesDisplay.class);
            in.putExtra("category",14);
            in.putExtra("exclude","0");
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(in);
        }else if (id == R.id.nav_genre){
            navigationView.setCheckedItem(id);
            Intent in = new Intent(this, categoriesDisplay.class);
            in.putExtra("category",13);
            in.putExtra("exclude","14,15");
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(in);
            //sulekhmasik.com.np/wp-json/wp/v2/categories?parent=13&exclude=14,15&per_page=100
        }else if (id == R.id.nav_contact) {
            navigationView.setCheckedItem(id);
            Intent in = new Intent(this, contactUs.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
