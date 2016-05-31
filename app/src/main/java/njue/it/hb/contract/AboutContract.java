package njue.it.hb.contract;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;

public interface AboutContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示开发人员
         */
        void showDevelopers();

        /**
         * 显示鸟语图处理方法
         */
        void showTwitterProcessMethod();

        /**
         * 显示致谢
         */
        void showThank();
    }


}