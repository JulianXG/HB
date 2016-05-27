package njue.it.hb.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import njue.it.hb.R;
import njue.it.hb.contract.IndexContract;
import njue.it.hb.model.Bird;

public class IndexFragment extends Fragment implements IndexContract.View {

    private IndexContract.Presenter mPresenter;

    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_index, container, false);

        return root;
    }

    public static IndexFragment newInstance(){
        return new IndexFragment();
    }

    @Override
    public void showResentSearch(List<String> keywords) {

    }

    @Override
    public void showSearchResult(List<Bird> bird) {

    }

    @Override
    public void showBirdDetail(int birdId) {

    }

    @Override
    public void showSearching() {
        mLoadingDialog = new LoadingDialog(getContext(), getContext().getString(R.string.title_loading_searching));
        mLoadingDialog.show();
    }

    @Override
    public void closeSearching() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }

    @Override
    public void setPresenter(IndexContract.Presenter presenter) {
        mPresenter = presenter;
    }
}