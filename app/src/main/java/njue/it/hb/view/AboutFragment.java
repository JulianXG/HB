package njue.it.hb.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.AboutContract;
import njue.it.hb.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment implements AboutContract.View {

    public static final String DEVELOPER = "DEVELOPER";

    public static final String TWITTER_PROCESS_METHOD = "TWITTER_PROCESS_METHOD";

    public static final String THANK = "THANK";

    private FragmentAboutBinding mBinding;

    private DrawerLayout mDrawerLayout;

    private AboutContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        mBinding = DataBindingUtil.bind(root);
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.introduction_title);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);//修改图标
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);//决定图标是否可以点击
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标

        mBinding.developer.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showDevelopers();
            }
        });

        mBinding.twitterProcessMethod.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showTwitterProcessMethod();
            }
        });
        mBinding.thank.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showThank();
            }
        });
        return root;
    }

    public static AboutFragment newInstance(){
        return new AboutFragment();
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
    public void showDevelopers() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_ABOUT, DEVELOPER);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showTwitterProcessMethod() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_ABOUT, TWITTER_PROCESS_METHOD);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showThank() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_ABOUT, THANK);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        mPresenter = presenter;
    }
}