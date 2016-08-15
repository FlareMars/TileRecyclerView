package com.utils.javenruan.tilerecyclerview;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.utils.javenruan.tilerecyclerview.adapter.TestItemAdapter;
import com.utils.javenruan.tilerecyclerview.library.TileRecyclerView;
import com.utils.javenruan.tilerecyclerview.library.TileRecyclerViewAdapter;
import com.utils.javenruan.tilerecyclerview.model.ModelUtils;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ModelUtils modelUtils;
    private TileRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelUtils = new ModelUtils();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        TileRecyclerView recyclerView = (TileRecyclerView) findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        recyclerView.setNumColumns(6);
        int padding = getResources().getDimensionPixelSize(R.dimen.recycler_padding);
        recyclerView.setHorizontalSpacing(padding);
        recyclerView.setVerticalSpacing(padding);

        TestItemAdapter dataSourceAdapter = new TestItemAdapter(modelUtils.dataSource());
        modelUtils.moreItems(50);
        adapter = new TileRecyclerViewAdapter<>(this, recyclerView, dataSourceAdapter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_add_more_item:
                                modelUtils.moreItems(40);
                                adapter.notifyDataSetChanged();
                                break;
                            case R.id.nav_reset_items:
                                modelUtils.clearItems();
                                adapter.notifyDataSetChanged();
                                break;
                        }
                        return true;
                    }
                });
    }
}
