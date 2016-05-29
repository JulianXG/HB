package njue.it.hb.contract;

import java.util.List;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;
import njue.it.hb.model.BirdListItem;

public interface IndexContract {

    interface presenter extends BasePresenter {

        /**
         * 通过关键词，进行模糊搜索
         */
        void search(String keyword);

        /**
         * 保存搜索关键字
         */
        void saveHistory(int birdId);

        /**
         * 加载近期搜索历史
         */
        void loadHistory();

        /**
         * 加载鸟类详情页
         */
        void loadBirdDetail(String name);
    }


    interface view extends BaseView<presenter> {

        /**
         * 显示搜索历史
         */
        void showSearchHistory(List<BirdListItem> items);

        /**
         * 显示搜索结果
         */
        void showSearchResult(List<BirdListItem> items);

        /**
         * 进入搜索结果详情页
         */
        void showBirdDetail(int birdId);

        /**
         * 显示正在搜索，等待界面
         */
        void showSearching();

        /**
         * 关闭正在搜索界面
         */
        void closeSearching();

        /**
         * 清空搜索框内容
         */
        void clearSearchContent();

        /**
         * 隐藏清除内容按钮
         */
        void hideClearSearchContent();

        /**
         * 显示清除按钮
         */
        void showClearSearchContent();

        /**
         * 显示无搜索结果
         */
        void showNoResult();

        /**
         * 显示无搜索历史
         */
        void showNoSearchHistory();

        /**
         * 关闭Tip
         */
        void closeTip();

    }

}