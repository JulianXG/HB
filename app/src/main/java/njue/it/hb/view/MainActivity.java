package njue.it.hb.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import njue.it.hb.R;
import njue.it.hb.util.ActivityUtils;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);//修改图标
        getSupportActionBar().setHomeButtonEnabled(true);//决定图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        setNavigation();
    }

    private void setNavigation() {
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.introduction_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), IntroductionFragment.newInstance(),R.id.content);
                            break;
                        case R.id.common_birds_list_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), CommonBirdsListFragment.newInstance(),R.id.content);
                            break;
                        case R.id.index_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), IndexFragment.newInstance(),R.id.content);
                            break;
                        case R.id.about_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), AboutFragment.newInstance(),R.id.content);
                            break;
                        default:
                            break;
                    }
                    item.setCheckable(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                }
            });
            /**
            * 默认打开IntroductionFragment
            * */

            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(),IntroductionFragment.newInstance(),R.id.content);
        }
    }


    /*
    * 添加toolbar左上角的点击功能
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
