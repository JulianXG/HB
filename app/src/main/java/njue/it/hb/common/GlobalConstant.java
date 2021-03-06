package njue.it.hb.common;

public class GlobalConstant {

    /**
     * SharedPreference的名称，data
     */
    public static final String SP_DATA = "DATA_SP";

    private static final String HB_APP_ROOT_PATH ="/data/data/njue.it.hb";

    /**
     * 解压文件存放路径
     */
    public static final String HB_DATA_FILE_PATH = HB_APP_ROOT_PATH + "/data";

    /**
     * 外置存储路径
     */
    public static final String EXTERNAL_STORAGE_ROOT_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 启动鸟类详情页的bird id 的key值
     */
    public static final String KEY_INTENT_BIRD_ID = "INTENT_BIRD";

    /**
     * 数据包绝对路径，这个版本写死在代码里
     */
    public static final String DATA_PACKAGE_FILE_NAME = EXTERNAL_STORAGE_ROOT_PATH + "/data.zip";

    /**
     * 介绍页intent的key值
     */
    public static final String KEY_INTENT_INTRODUCTION = "INTENT_INTRODUCTION";

    /**
     * 关于页Intent的key值
     */
    public static final String KEY_INTENT_ABOUT = "INTENT_ABOUT";
}