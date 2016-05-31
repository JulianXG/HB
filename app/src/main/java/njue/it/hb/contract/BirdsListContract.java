package njue.it.hb.contract;

import java.util.List;
import java.util.Map;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;
import njue.it.hb.model.BirdListItem;

public interface BirdsListContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示以科种排列的鸟种列表
         */
        void showBirdsOrderList(List<Map<String, List<BirdListItem>>> birdList);

        /**
         * 显示以拼音排序的鸟种列表
         */
        void showBirdsPinyinList(List<BirdListItem> birdList);

        /**
         * 进入鸟类详情页
         */
        void showBirdDetail(int id);

        /**
         * 显示没有正确导入资源包
         */
        void showImportDataError();

    }

    interface Presenter extends BasePresenter {

        /**
         * 加载鸟类科种排序列表
         */
        void loadBirdsOrderList();

        /**
         * 加载鸟类以拼音排序列表
         */
        void loadBirdsPinyinList();

        /**
         * 加载鸟类详情资料
         */
        void loadBirdDetail(String name);

    }


}
