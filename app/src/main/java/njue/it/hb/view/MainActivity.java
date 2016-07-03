package njue.it.hb.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import njue.it.hb.R;
import njue.it.hb.contract.MainActivityContract;
import njue.it.hb.data.repository.MainRepository;
import njue.it.hb.databinding.ActivityMainBinding;
import njue.it.hb.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter mPresenter;

    private ActivityMainBinding mBinding;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBinding.drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        mPresenter = new MainActivityPresenter(new MainRepository(this), this);
        mFragmentManager = getSupportFragmentManager();

        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mPresenter.loadMenuSection(mFragmentManager,item.getItemId());
                return true;
            }
        });

        mPresenter.isFirstAndRun();
    }

    /**
     * 默认打开CommonBirdsListFragment
     */
    @Override
    public void showDefaultSection() {
        mPresenter.loadMenuSection(mFragmentManager,R.id.introduction_nav_menu_item);
    }

    @Override
    public void showFirstRun() {
        Toast.makeText(MainActivity.this, R.string.toast_first_run, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDrawer() {
        mBinding.drawerLayout.closeDrawers();
    }

    @Override
    public void setPresenter(MainActivityContract.Presenter Presenter) {
        mPresenter = Presenter;
    }

}
