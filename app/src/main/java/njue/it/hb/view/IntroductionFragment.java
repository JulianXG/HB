package njue.it.hb.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.IntroductionContract;
import njue.it.hb.databinding.FragmentIntroductionBinding;

public class IntroductionFragment extends Fragment implements IntroductionContract.View {

    private IntroductionContract.Presenter mPresenter;

    private FragmentIntroductionBinding mBinding;

    private DrawerLayout mDrawerLayout;

    public static final String KEY_BASIC_FACT = "1";

    public static final String KEY_WHAT_IS_BIRD = "2";

    public static final String KEY_WATCH_BIRD = "3";

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_introduction, container, false);
        mBinding = DataBindingUtil.bind(root);
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.introduction_title);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);//修改图标
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);//决定图标是否可以点击
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标


        mBinding.hbBasicFact.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showHBIntroduction();
            }
        });

        mBinding.whatIsBird.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showWhatIsBird();
            }
        });
        mBinding.watchBird.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showHowToWatchBird();
            }
        });

        return root;
    }

    public static IntroductionFragment newInstance() {
        return new IntroductionFragment();
    }

    @Override
    public void setPresenter(IntroductionContract.Presenter Presenter) {
        mPresenter = Presenter;
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
    public void showHBIntroduction() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_INTRODUCTION, KEY_BASIC_FACT);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showWhatIsBird() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_INTRODUCTION, KEY_WHAT_IS_BIRD);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showHowToWatchBird() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_INTRODUCTION, KEY_WATCH_BIRD);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }
}