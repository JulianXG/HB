package njue.it.hb.presenter;

import java.util.List;

import njue.it.hb.contract.IndexContract;
import njue.it.hb.data.source.IndexDataSource;
import njue.it.hb.model.BirdListItem;

public class IndexPresenter implements IndexContract.presenter {

    private IndexContract.view mView;

    private IndexDataSource mDataSource;

    public IndexPresenter(IndexDataSource dataSource, IndexContract.view view) {
        mDataSource = dataSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void search(String keyword) {
        List<BirdListItem> list = mDataSource.search(keyword);
        if (list.size() == 0) {
            mView.showNoResult();
        }else {
            mView.showSearchResult(list);
        }
    }

    @Override
    public void saveHistory(int birdId) {
        mDataSource.saveHistory(birdId);
    }

    @Override
    public void loadHistory() {
        List<BirdListItem> list = mDataSource.getHistory();
        if (list.size() == 0) {
            mView.showNoSearchHistory();
        } else {
            mView.showSearchHistory(list);
        }
    }

    @Override
    public void loadBirdDetail(String name) {
        int id = mDataSource.getIdByChineseName(name);
        mView.showBirdDetail(id);
        saveHistory(id);
    }
}