package njue.it.hb.data.source;

import android.net.Uri;
import android.os.Handler;

import java.io.File;

public interface DataPackageDataSource {

    /**
     * 数据包下载地址
     */
    String DOWNLOAD_URL = "http://ebirdnote.cn/hb/data";

    /**
     * SP文件的名称
     */
    String SP_NAME = "DATA_PACKAGE";

    /**
     * SP中数据包安装状态KEY
     */
    String KEY_INSTALL_STATUS = "INSTALL_STATUS";

    /**
     * 安装子线程
     */
    void installInThread(Handler handler, File fileDataPackage);

    /**
     * 取得数据包安装状态
     */
    boolean getInstallStatus();

    /**
     * 改变数据包安装状态
     */
    void changeInstallStatus(boolean isInstalled);

    /**
     * 通过content转文件绝对路径
     */
    String getPathFromUri(Uri uri);
}