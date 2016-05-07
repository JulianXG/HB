package njue.it.hb.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import njue.it.hb.R;
import njue.it.hb.contract.BirdsListContract;

public class BirdsListFragment extends Fragment implements BirdsListContract.view {

    private BirdsListContract.presenter mPresenter;

    private DrawerLayout mDrawerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_common_birds_list, container, false);

        //为了保证onOptionsItemSelected有效
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);//修改图标
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);//决定图标是否可以点击
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
        return root;
    }

    public static BirdsListFragment newInstance(){
        return new BirdsListFragment();
    }


    @Override
    public void setPresenter(BirdsListContract.presenter presenter) {
        mPresenter=presenter;
    }

    /**
    * 添加toolbar左上角的点击功能
    **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showBirdsOrderList(Map<String, List<String>> birdList) {

    }

    @Override
    public void showBirdsPinyinList(Map<String, List<String>> birdList) {

    }

    @Override
    public void showBirdDetail(Object birdData) {

    }
}