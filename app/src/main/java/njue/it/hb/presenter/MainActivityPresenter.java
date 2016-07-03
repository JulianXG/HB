package njue.it.hb.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import njue.it.hb.R;
import njue.it.hb.contract.MainActivityContract;
import njue.it.hb.data.source.MainDataSource;
import njue.it.hb.view.AboutFragment;
import njue.it.hb.view.BirdsListFragment;
import njue.it.hb.view.DataPackageFragment;
import njue.it.hb.view.IndexFragment;
import njue.it.hb.view.IntroductionFragment;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private static final String TAG = "MainActivityPresenter";

    private MainDataSource mDataSource;

    private MainActivityContract.View mView;

    public MainActivityPresenter(MainDataSource dataSource, MainActivityContract.View View) {
        mDataSource = dataSource;
        mView = View;
    }

    @Override
    public void isFirstAndRun() {
        if (mDataSource.isFirstRun()) {
            firstRun();
        } else {
            commonRun();
        }
    }

    @Override
    public void firstRun() {
        mView.showFirstRun();
        commonRun();
    }

    @Override
    public void commonRun() {
        mView.showDefaultSection();
    }

    @Override
    public void loadMenuSection(FragmentManager fragmentManager, int resId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragmentTransaction.hide(fragment);
            }
        }
        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(resId));
        if (fragment != null) {
            fragmentTransaction.show(fragment);
        } else {
            switch (resId) {
                case R.id.introduction_nav_menu_item:
                    fragmentTransaction.add(R.id.content, IntroductionFragment.newInstance(), String.valueOf(resId));
                    break;
                case R.id.common_birds_list_nav_menu_item:
                    fragmentTransaction.add(R.id.content, BirdsListFragment.newInstance(), String.valueOf(resId));
                    break;
                case R.id.index_nav_menu_item:
                    fragmentTransaction.add(R.id.content, IndexFragment.newInstance(), String.valueOf(resId));
                    break;
                case R.id.about_nav_menu_item:
                    fragmentTransaction.add(R.id.content, AboutFragment.newInstance(), String.valueOf(resId));
                    break;
                case R.id.update_data_menu_item:
                    fragmentTransaction.add(R.id.content, DataPackageFragment.newInstance(), String.valueOf(resId));
                    break;
            }
        }

        fragmentTransaction.commit();
        mView.closeDrawer();
    }

}