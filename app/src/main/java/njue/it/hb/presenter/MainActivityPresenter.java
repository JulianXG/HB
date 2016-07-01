package njue.it.hb.presenter;

import njue.it.hb.contract.MainActivityContract;
import njue.it.hb.data.source.MainDataSource;

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

}