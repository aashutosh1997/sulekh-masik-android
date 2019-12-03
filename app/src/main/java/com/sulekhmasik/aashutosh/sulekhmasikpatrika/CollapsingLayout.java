package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sulekhmasik.aashutosh.sulekhmasikpatrika.R;

public class CollapsingLayout extends AppCompatActivity {
    protected NestedScrollView fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collapsing);
        fl = findViewById(R.id.coll_list);
        configActionBar();
    }

    private void configActionBar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.pin_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home: finish();
        }
        return super.onOptionsItemSelected(item);
    }
}