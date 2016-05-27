package njue.it.hb.contract;

import java.util.List;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;
import njue.it.hb.model.Bird;

public interface IndexContract {

    interface Presenter extends BasePresenter {

        /**
         * 通过关键词，进行模糊搜索
         */
        void search(String keyword);

        /**
         * 保存搜索关键字
         */
        void saveSearchedKeyword(String keyword);

        /**
         * 加载近期搜索过的关键字
         */
        void loadSearchedKeywords();
    }


    interface View extends BaseView<Presenter> {

        /**
         * 显示近期搜索
         */
        void showResentSearch(List<String> keywords);

        /**
         * 显示搜索结果
         */
        void showSearchResult(List<Bird> bird);

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
    }

}