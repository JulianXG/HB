package njue.it.hb.data.source;

public interface MainDataSource {

    /**
     * SharedPreference的名称
     */
    String KEY_DATA_SP = "DATA_SP";

    /**
     * isFirstRun的key
     */
    String KEY_IS_FIRST_RUN = "IS_FIRST_RUN";

    /**
     * 通过判断zip文件是否存在，得到结果
     * @return 是否为第一次运行
     */
    boolean isFirstRun();

    /**
     * 记录不是第一次运行
     */
    void recordNotFirstRun();

}