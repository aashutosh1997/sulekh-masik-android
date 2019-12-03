package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.sulekhmasik.aashutosh.sulekhmasikpatrika.R;
import com.sulekhmasik.aashutosh.sulekhmasikpatrika.appLayout;

public class contactUs extends appLayout {
    public View parView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parView = getLayoutInflater().inflate(R.layout.activity_contact, fl);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent in = new Intent(this, appHome.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(in);
        }
    }
}
