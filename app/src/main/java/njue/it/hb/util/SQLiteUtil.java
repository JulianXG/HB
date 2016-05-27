package njue.it.hb.util;

import android.database.sqlite.SQLiteDatabase;

import njue.it.hb.common.GlobalConstant;

/**
 * 单例模式的Util，节约资源
 */
public final class SQLiteUtil {

    private static SQLiteDatabase mDatabase = null;

    /**
     * 数据库名字
     */
    private static final String HB_DATABASE_ABSOLUTE_NAME = GlobalConstant.HB_DATA_FILE_PATH + "/" + "hb.db";

    /**
     * 工具类，提供public static方法
     */
    public static SQLiteDatabase getDatabase() throws RuntimeException{
        if (mDatabase == null) {
            mDatabase = SQLiteDatabase.openOrCreateDatabase(HB_DATABASE_ABSOLUTE_NAME, null);
        }
        return mDatabase;
    }

}
