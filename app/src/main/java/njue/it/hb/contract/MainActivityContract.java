package njue.it.hb.contract;

import android.support.v4.app.FragmentManager;

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
         * 加载菜单对应界面
         */
        void loadMenuSection(FragmentManager fragmentManager, int resId);

    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示默认栏目
         */
        void showDefaultSection();

        /**
         * 显示第一次运行
         */
        void showFirstRun();

        /**
         * 关闭侧边栏
         */
        void closeDrawer();

    }

}