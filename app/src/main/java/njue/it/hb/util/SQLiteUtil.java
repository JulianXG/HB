package njue.it.hb.util;

import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileNotFoundException;

import njue.it.hb.common.GlobalConstant;

/**
 * 单例模式的Util，节约资源
 */
public final class SQLiteUtil {

    private static final String TAG = "SQLiteUtil";

    private static SQLiteDatabase mDatabase = null;

    public static final String DATA_FILE_NAME = "hb.db";

    /**
     * 数据库名字
     */
    private static final String HB_DATABASE_ABSOLUTE_NAME = GlobalConstant.HB_DATA_FILE_PATH + "/" + "hb.db";

    /**
     * 工具类，提供public static方法
     */
    public static SQLiteDatabase getDatabase() throws FileNotFoundException {
        if (mDatabase == null) {
            try {
                mDatabase = SQLiteDatabase.openOrCreateDatabase(HB_DATABASE_ABSOLUTE_NAME, null);
            } catch (SQLiteCantOpenDatabaseException e) {
                Log.i(TAG, "getDatabase: " + e.toString());
                throw new FileNotFoundException(DATA_FILE_NAME);
            }

        }
        return mDatabase;
    }

}
