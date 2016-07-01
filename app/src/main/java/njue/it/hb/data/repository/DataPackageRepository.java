package njue.it.hb.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import njue.it.hb.common.GlobalConstant;
import njue.it.hb.data.source.DataPackageDataSource;
import njue.it.hb.util.FilePathResolverUtil;
import njue.it.hb.util.ZipUtil;

public class DataPackageRepository implements DataPackageDataSource {

    private static final String TAG = "DataPackageRepository";

    private SharedPreferences mSharedPreferences;

    private Context mContext;

    public DataPackageRepository(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void installInThread(Handler handler, File fileDataPackage) {
        ZipUtil.extractZipFileWithProgress(fileDataPackage, GlobalConstant.HB_DATA_FILE_PATH, handler);
    }

    @Override
    public boolean getInstallStatus() {
        return mSharedPreferences.getBoolean(KEY_INSTALL_STATUS, false);
    }

    @Override
    public void changeInstallStatus(boolean isInstalled) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_INSTALL_STATUS, isInstalled);
        editor.apply();
    }

    @Override
    public String getPathFromUri(Uri uri) {
        return FilePathResolverUtil.getPath(mContext, uri);

    }

}