package com.app.treasure.treasure.home;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.treasure.treasure.BaseActivity;
import com.app.treasure.treasure.R;
import com.app.treasure.treasure.commons.ActivityUtils;
import com.app.treasure.treasure.user.account.AccountActivity;

import butterknife.Bind;

/**
 * Created by ruifeng on 2016/7/12.
 */
public class HomeAcitvity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityUtils activityUtils;

    private ImageView imageView;

    @Bind(R.id.toobar_Home)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout
                ,toolbar

                , R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_userIcon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityUtils.startActivity(AccountActivity.class);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_hide:
                break;
            case R.id.menu_my_list:
                break;
            case R.id.menu_item_help:
                break;
            case R.id.menu_item_logout:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }
}
