package com.liu.drawlayoutstudy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.liu.drawlayoutstudy.fragment.SimpleFragment;
import com.liu.drawlayoutstudy.utils.BlurBitmapUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private View head_layout;

    private Fragment[] fragments;
    private String[] titles;
    private int currentTabIndex;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNavigationView();
        initToolbar();
        initFragment();
    }

    private void initView() {
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawLayout);
    }

    private void initNavigationView() {
        head_layout = navigationView.inflateHeaderView(R.layout.head_layout);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.head);
        Bitmap blurBitmap = BlurBitmapUtil.blurBitmap(this, decodeResource, 18f);
        head_layout.setBackgroundDrawable(new BitmapDrawable(getResources(), blurBitmap));
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //创建返回键，并实现打开关/闭监听
        toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    private void initFragment() {
        titles=getResources().getStringArray(R.array.nav_menu_titles);
        SimpleFragment newsFragment=SimpleFragment.getInstance(titles[0]);
        SimpleFragment pictureFragment=SimpleFragment.getInstance(titles[1]);
        fragments = new Fragment[]{newsFragment, pictureFragment};
        clickItem(0);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_gank:
                clickItem(0);
                break;
            case R.id.nav_image:
                clickItem(1);
                break;
        }
        return true;
    }

    private void clickItem(int changeIndex) {
        index = changeIndex;
        navigationView.getMenu().getItem(changeIndex).setChecked(true);
        switchFragment();
        drawerLayout.closeDrawers();
    }

    /**
     * 切换Fragment
     */
    private void switchFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            fragmentTransaction.add(R.id.content_fl, fragments[index]);
        }
        fragmentTransaction.show(fragments[index]).commit();
        currentTabIndex = index;
    }
}
