package njue.it.hb.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import njue.it.hb.common.GlobalConstant;
import njue.it.hb.util.ZipUtil;

public class DataFileRepository implements njue.it.hb.data.source.DataFileDataSource {

    private static final String TAG = "DataFileRepository";

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    /**
     * SharedPreference的名称
     */
    private static final String KEY_DATA_SP = "DATA_SP";

    /**
     * isFirstRun的key
     */
    private static final String KEY_IS_FIRST_RUN = "IS_FIRST_RUN";

    /**
     * 解压文件存放路径
     */
    private static final String DATA_FILE_PATH = GlobalConstant.HB_DATA_FILE_PATH;


    public DataFileRepository(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(KEY_DATA_SP,Context.MODE_PRIVATE);
    }

    @Override
    public boolean isFirstRun() {
        boolean isFirstRun = mSharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true);
        Log.i(TAG, "isFirstRun: " + isFirstRun);
        return isFirstRun;
    }

    @Override
    public void extractWithProgressInThread(File sourceFile, Handler handler) {
        try {
            ZipUtil.extractZipFileWithProgress(sourceFile, DATA_FILE_PATH, handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recordNotFirstRun() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_IS_FIRST_RUN, false);
        editor.apply();     //这是一个异步操作，没有同步
    }

}