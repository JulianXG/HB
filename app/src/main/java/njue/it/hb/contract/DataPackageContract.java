package njue.it.hb.contract;

import android.content.Intent;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;

public interface DataPackageContract {

    /**
     * 数据包请求CODE
     */
    int DATA_PACKAGE_CODE = 10;

    interface Presenter extends BasePresenter {

        /**
         * 加载当时本机情况
         */
        void loadCurrentStatus();

        /**
         * 安装数据包
         */
        void installDataPackage(String fileName);

        /**
         * 加载下载链接
         */
        void loadDownloadURL();

        /**
         * 选择数据包
         */
        void selectDataPackage();

        /**
         * 处理选择结果
         */
        void handleSelectResult(int requestCode, int resultCode, Intent data);
    }


    interface View extends BaseView<Presenter> {


        /**
         * 显示安装进度
         */
        void showInstallProgress(int progress);

        /**
         * 显示安装成功
         */
        void showInstallSuccess();

        /**
         * 显示安装出错
         */
        void showInstallError();

        /**
         * 开始显示安装界面
         */
        void showInstallStart();

        /**
         * 关闭安装数据包界面
         */
        void closeInstall();

        /**
         * 显示选择数据包界面
         */
        void showSelectDataPackage();

        /**
         * 显示数据包已经安装
         */
        void showInstallAlready();

        /**
         * 显示数据包文件选择错误
         */
        void showSelectError();

        /**
         * 显示数据包未安装
         */
        void showNotInstall();

        /**
         * 转到下载数据包界面
         */
        void showGoToDownload(String downloadURL);
    }
}
