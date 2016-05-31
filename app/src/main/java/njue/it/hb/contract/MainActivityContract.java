package njue.it.hb.contract;

import java.io.File;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;

public interface MainActivityContract {

    interface Presenter extends BasePresenter {

        /**
         * 判断是否第一次运行，并继续执行对应逻辑
         */
        void isFirstAndRun();

        /**
         * 第一次运行的执行逻辑
         */
        void firstRun();

        /**
         * 不是第一次执行时的逻辑
         */
        void commonRun();

        /**
         * 解压数据文件
         */
        void extractDataFile(File sourceFile);

        /**
         * 选择文件错误
         */
        void selectDataError();
    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示正在解压界面
         */
        void showExtracting();

        /**
         * 关闭正在解压界面
         */
        void closeExtracting();

        /**
         * 显示解压数据成功
         */
        void showExtractDataSuccess();

        /**
         * 显示解压数据失败
         */
        void showExtractDataFail();

        /**
         * 显示默认栏目
         */
        void showDefaultSection();

        /**
         * 显示选择数据压缩文件界面
         */
        void showSelectDataFile();

        /**
         * 显示解压数据进度
         */
        void showExtractProgress(int progress);

        /**
         * 显示第一次运行
         */
        void showFirstRun();

        /**
         * 显示选择压缩包错误
         */
        void showSelectDataError();
    }

}