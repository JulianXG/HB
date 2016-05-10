package njue.it.hb.util;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SQLiteUtil {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private String CCWCC_DATABASE_PATH;
    private String CCWCC_DATABASE_NAME;
    private String CCWCC_FILE_ABSOLUTE_PATH;

    public SQLiteUtil(Context context) {
        mContext = context;
        CCWCC_DATABASE_PATH = mContext.getPackageCodePath()+"/databases/";
        CCWCC_DATABASE_NAME="hb.db";
        CCWCC_FILE_ABSOLUTE_PATH=CCWCC_DATABASE_PATH+CCWCC_DATABASE_NAME;
    }

    public SQLiteDatabase getDatabase(){
        File file = new File(CCWCC_FILE_ABSOLUTE_PATH);
        if (file.exists()) {
            if (mDatabase == null) {
                mDatabase = SQLiteDatabase.openOrCreateDatabase(CCWCC_FILE_ABSOLUTE_PATH, null);
            }
        } else {    //通过db文件创建SQLiteDatabase对象
            File path = new File(CCWCC_DATABASE_PATH);
            path.mkdir();
            try {
                AssetManager assetManager = mContext.getAssets();
                InputStream inputStream = assetManager.open(CCWCC_DATABASE_NAME);
                FileOutputStream outputStream = new FileOutputStream(CCWCC_FILE_ABSOLUTE_PATH);
                byte[] buffer = new byte[1024];
                int count;
                while ((count = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, count);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
                mDatabase = SQLiteDatabase.openOrCreateDatabase(CCWCC_FILE_ABSOLUTE_PATH, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mDatabase;
    }

}
