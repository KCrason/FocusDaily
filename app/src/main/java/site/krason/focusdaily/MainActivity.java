package site.krason.focusdaily;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import site.krason.focusdaily.events.RefreshEvent;
import site.krason.focusdaily.fragments.HandPickFragment;
import site.krason.focusdaily.fragments.ImageFragment;
import site.krason.focusdaily.fragments.JokeFragment;
import site.krason.focusdaily.fragments.RecommendedFragment;
import site.krason.focusdaily.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private int mCurPosition;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new RefreshEvent(0, mCurPosition));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        KFragmentPagerAdapter kFragmentPagerAdapter = new KFragmentPagerAdapter(getSupportFragmentManager());
        kFragmentPagerAdapter.setFragments(getFragments());
        kFragmentPagerAdapter.setTitles(getTitles());
        mViewPager.setAdapter(kFragmentPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendedFragment());
        fragments.add(new HandPickFragment());
        fragments.add(new JokeFragment());
        fragments.add(new VideoFragment());
        fragments.add(new ImageFragment());
        return fragments;
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("精选");
        titles.add("笑话");
        titles.add("视频");
        titles.add("图片");
        return titles;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mCurPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public final class KFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<String> mTitles;

        private List<Fragment> mFragments;

        public KFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setTitles(List<String> titles) {
            this.mTitles = titles;
        }

        public void setFragments(List<Fragment> fragments) {
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments != null) {
                return mFragments.get(position);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitles != null) {
                return mTitles.get(position);
            }
            return super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            if (mFragments != null) {
                return mFragments.size();
            }
            return 0;
        }
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
