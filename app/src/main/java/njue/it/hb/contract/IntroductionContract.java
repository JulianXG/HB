package njue.it.hb.contract;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;

public interface IntroductionContract {

    interface View extends BaseView<Presenter>{

        /**
         * 显示黑石顶介绍界面
         */
        void showHBIntroduction();

        /**
         * 显示什么是鸟类界面
         */
        void showWhatIsBird();

        /**
         * 显示怎么观鸟
         */
        void showHowToWatchBird();
    }

    interface Presenter extends BasePresenter{

    }
}