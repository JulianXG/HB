package njue.it.hb.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.mozilla.universalchardet.prober.MBCSGroupProber;

import java.io.File;

import njue.it.hb.contract.MainActivityContract;
import njue.it.hb.data.source.DataFileDataSource;
import njue.it.hb.util.ZipUtil;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private static final String TAG = "MainActivityPresenter";

    private DataFileDataSource mDataSource;

    private MainActivityContract.View mView;

    public MainActivityPresenter(DataFileDataSource dataSource, MainActivityContract.View View) {
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
    public void extractDataFile(File sourceFile) {
        mView.showExtracting();
        ExtractHandler extractHandler = new ExtractHandler();
        mDataSource.extractWithProgressInThread(sourceFile,extractHandler);
    }

    @Override
    public void selectDataError() {
        mView.showSelectDataError();
        mView.showSelectDataFile();
    }

    class ExtractHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ZipUtil.START:
                    mView.showExtractProgress(0);
                    break;
                case ZipUtil.EXTRACTING:
                    Bundle bundle = msg.getData();
                    int progress = bundle.getInt(ZipUtil.KEY_PROGRESS);
                    mView.showExtractProgress(progress);
                    Log.i(TAG, "handleMessage: 解压进度：" + progress);
                    break;
                case ZipUtil.COMPLETED:
                    mView.showExtractDataSuccess();
                    mDataSource.recordNotFirstRun();
                    mView.closeExtracting();
                    commonRun();
                    break;
                case ZipUtil.ERROR:
                    mView.showExtractDataFail();
                    break;
            }
        }
    }

}