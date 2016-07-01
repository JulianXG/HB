package njue.it.hb.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import njue.it.hb.common.GlobalConstant;
import njue.it.hb.data.source.MainDataSource;

public class MainRepository implements MainDataSource {

    private static final String TAG = "MainRepository";

    private SharedPreferences mSharedPreferences;

    public MainRepository(Context context) {
        mSharedPreferences = context.getSharedPreferences(KEY_DATA_SP,Context.MODE_PRIVATE);
    }

    @Override
    public boolean isFirstRun() {
        boolean isFirstRun = mSharedPreferences.getBoolean(KEY_IS_FIRST_RUN, true);
        Log.i(TAG, "isFirstRun: " + isFirstRun);
        recordNotFirstRun();
        return isFirstRun;
    }

    @Override
    public void recordNotFirstRun() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_IS_FIRST_RUN, false);
        editor.apply();     //这是一个异步操作，没有同步
    }

}