package njue.it.hb.data.source;

import android.os.Handler;

import java.io.File;

public interface DataFileDataSource {

    /**
     * 通过判断zip文件是否存在，得到结果
     * @return 是否为第一次运行
     */
    boolean isFirstRun();

    /**
     * 解压数据文件
     * 解压是否成功
     */
    void extractWithProgressInThread(File sourceFile, Handler handler);

    /**
     * 记录不是第一次运行
     */
    void recordNotFirstRun();

}