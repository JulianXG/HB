package njue.it.hb.contract;

import java.util.List;
import java.util.Map;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;
import njue.it.hb.model.BirdOrderListItem;

public interface BirdsListContract {

    interface view extends BaseView<presenter> {

        /**
         * 显示以科种排列的鸟种列表
         */
        void showBirdsOrderList(List<Map<String, List<BirdOrderListItem>>> birdList);

        /**
         * 显示以拼音排序的鸟种列表
         */
        void showBirdsPinyinList(Map<String, List<String>> birdList);

        /**
         * 进入鸟类详情页
         */
        void showBirdDetail(Object birdData);
    }


    interface presenter extends BasePresenter {

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
        void loadBirdData(Long birdId);
    }


}
