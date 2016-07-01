package njue.it.hb.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;

import njue.it.hb.contract.DataPackageContract;
import njue.it.hb.data.source.DataPackageDataSource;
import njue.it.hb.util.ZipUtil;

public class DataPackagePresenter implements DataPackageContract.Presenter {

    private static final String TAG = "DataPackagePresenter";

    private DataPackageDataSource mDataSource;

    private DataPackageContract.View mView;

    private InstallHandler mInstallHandler;

    public DataPackagePresenter(DataPackageDataSource dataSource, DataPackageContract.View view) {
        mDataSource = dataSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadCurrentStatus() {
        if (mDataSource.getInstallStatus()) {
            mView.showInstallAlready();
        } else {
            mView.showNotInstall();
        }
    }

    @Override
    public void installDataPackage(String fileName) {
        if (mInstallHandler == null) {
            mInstallHandler = new InstallHandler();
        }
        mDataSource.installInThread(mInstallHandler, new File(fileName));
        mView.showInstallStart();
    }

    @Override
    public void loadDownloadURL() {
        mView.showGoToDownload(DataPackageDataSource.DOWNLOAD_URL);
    }

    @Override
    public void selectDataPackage() {
        mView.showSelectDataPackage();
    }

    @Override
    public void handleSelectResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==DataPackageContract.DATA_PACKAGE_CODE){
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String fileName = mDataSource.getPathFromUri(uri);
                if (fileName != null) {
                    installDataPackage(fileName);
                } else {
                    mView.showSelectError();
                }

            }
        }

    }

    class InstallHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ZipUtil.START:
                    mView.showInstallProgress(0);
                    break;
                case ZipUtil.EXTRACTING:
                    Bundle bundle = msg.getData();
                    int progress = bundle.getInt(ZipUtil.KEY_PROGRESS);
                    mView.showInstallProgress(progress);
                    Log.i(TAG, "handleMessage: 解压进度：" + progress);
                    break;
                case ZipUtil.COMPLETED:
                    mView.showInstallSuccess();
                    mView.closeInstall();
                    mDataSource.changeInstallStatus(true);
                    break;
                case ZipUtil.ERROR:
                    mView.showInstallError();
                    mView.closeInstall();
                    break;
            }
        }
    }
}