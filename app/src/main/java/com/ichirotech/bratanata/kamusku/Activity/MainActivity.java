package com.ichirotech.bratanata.kamusku.Activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.ichirotech.bratanata.kamusku.Adapter.KamusEngAdapter;
import com.ichirotech.bratanata.kamusku.Adapter.KamusIndoAdapter;
import com.ichirotech.bratanata.kamusku.Helper.KamusEngHelper;
import com.ichirotech.bratanata.kamusku.Helper.KamusIndoHelper;

import com.ichirotech.bratanata.kamusku.Helper.itemClickSupport;
import com.ichirotech.bratanata.kamusku.POJO_Parcelable.KamusModel;
import com.ichirotech.bratanata.kamusku.R;
import com.ichirotech.bratanata.kamusku.Activity.DetaiActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressBar loding;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_catagory)
    RecyclerView rvCatagory;


    private  int TYPE_CARI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

            @Override
            public boolean onQueryTextChange(String newText) {
                String cari = newText+"%";

                if(TYPE_CARI == 100) {
                    KamusIndoAdapter adapter = new KamusIndoAdapter(MainActivity.this);
                    KamusIndoHelper helper = new KamusIndoHelper(MainActivity.this);
                    rvCatagory.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rvCatagory.setAdapter(adapter);
                    helper.open();
                    final ArrayList<KamusModel> list = helper.getDataByAbjad(cari);
                    helper.close();
                    adapter.addItem(list);
                    itemClickSupport.addTo(rvCatagory).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            Intent intent = new Intent(MainActivity.this,DetaiActivity.class);
                            intent.putExtra(DetaiActivity.EXTRA_NOTE,list.get(position));
                            startActivity(intent);
                        }
                    });

                }else if(TYPE_CARI == 200){
                    KamusEngAdapter adapter = new KamusEngAdapter(MainActivity.this);
                    KamusEngHelper helper = new KamusEngHelper(MainActivity.this);
                    rvCatagory.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rvCatagory.setAdapter(adapter);
                    helper.open();
                    final ArrayList<KamusModel> list = helper.getDataByAbjad(cari);
                    helper.close();
                    adapter.addItem(list);
                    itemClickSupport.addTo(rvCatagory).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            Intent intent = new Intent(MainActivity.this,DetaiActivity.class);
                            intent.putExtra(DetaiActivity.EXTRA_NOTE,list.get(position));
                            startActivity(intent);
                        }
                    });
                }
            return true;
            }

        });

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.indo_eng:

                TYPE_CARI = 100;
                KamusIndoAdapter adapter = new KamusIndoAdapter(MainActivity.this);
                KamusIndoHelper helper = new KamusIndoHelper(MainActivity.this);
                rvCatagory.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvCatagory.setAdapter(adapter);
                helper.open();
                final ArrayList<KamusModel> list = helper.getAllData();
                helper.close();
                adapter.addItem(list);
                itemClickSupport.addTo(rvCatagory).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                      Intent intent = new Intent(MainActivity.this,DetaiActivity.class);
                      intent.putExtra(DetaiActivity.EXTRA_NOTE,list.get(position));
                      startActivity(intent);
                    }
                });
                break;
            case R.id.eng_indo:
                TYPE_CARI = 200;
                KamusEngAdapter engAdapter = new KamusEngAdapter(MainActivity.this);
                KamusEngHelper engHelper = new KamusEngHelper(MainActivity.this);
                rvCatagory.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvCatagory.setAdapter(engAdapter);

                engHelper.open();
                final ArrayList<KamusModel> engModel = engHelper.getAllData();
                engHelper.close();
                engAdapter.addItem(engModel);
                itemClickSupport.addTo(rvCatagory).setOnItemClickListener(new itemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(MainActivity.this,DetaiActivity.class);
                        intent.putExtra(DetaiActivity.EXTRA_NOTE,engModel.get(position));
                        startActivity(intent);
                    }
                });
                break;



        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
