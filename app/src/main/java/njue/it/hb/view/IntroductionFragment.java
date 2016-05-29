package njue.it.hb.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import njue.it.hb.R;
import njue.it.hb.contract.IntroductionContract;
import njue.it.hb.data.repository.IndexRepository;
import njue.it.hb.data.source.IndexDataSource;
import njue.it.hb.databinding.FragmentIntroduceBinding;

public class IntroductionFragment extends Fragment implements IntroductionContract.view {

    private static final String TAG = "IntroductionFragment";

    private IntroductionContract.presenter mPresenter;

    private FragmentIntroduceBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_introduce, container, false);
        mBinding = DataBindingUtil.bind(root);

        return root;
    }

    public static IntroductionFragment newInstance(){
        return new IntroductionFragment();
    }

    @Override
    public void setPresenter(IntroductionContract.presenter presenter) {
        mPresenter = presenter;
    }
}