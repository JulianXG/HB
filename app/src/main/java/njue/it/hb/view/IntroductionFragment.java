package njue.it.hb.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import njue.it.hb.R;
import njue.it.hb.contract.IntroductionContract;

public class IntroductionFragment extends Fragment implements IntroductionContract.View {

    private IntroductionContract.Presenter mPresenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_introduce, container, false);

        return root;
    }

    public static IntroductionFragment newInstance(){
        return new IntroductionFragment();
    }

    @Override
    public void setPresenter(IntroductionContract.Presenter presenter) {
        mPresenter = presenter;
    }
}