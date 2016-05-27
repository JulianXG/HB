package njue.it.hb.common;

public class GlobalConstant {

    /**
     * SharedPreference的名称
     */
    public static final String KEY_DATA_SP = "DATA_SP";

    private static final String HB_APP_ROOT_PATH ="/data/data/njue.it.hb";

    /**
     * 解压文件存放路径
     */
    public static final String HB_DATA_FILE_PATH = HB_APP_ROOT_PATH + "/data";


    /**
     * 外置存储路径
     */
    public static final String EXTERNAL_STORAGE_ROOT_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

}