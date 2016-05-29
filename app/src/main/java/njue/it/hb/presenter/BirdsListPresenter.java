package njue.it.hb.presenter;

import java.util.List;
import java.util.Map;

import njue.it.hb.contract.BirdsListContract;
import njue.it.hb.data.source.DatabaseDataSource;
import njue.it.hb.model.BirdListItem;

public class BirdsListPresenter implements BirdsListContract.presenter {

    private BirdsListContract.view mView;

    private DatabaseDataSource mDataSource;

    public BirdsListPresenter(DatabaseDataSource dataSource, BirdsListContract.view view) {
        mDataSource = dataSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadBirdsOrderList() {
        List<Map<String, List<BirdListItem>>> list = mDataSource.getBirdsOrderList();
        mView.showBirdsOrderList(list);
    }

    @Override
    public void loadBirdsPinyinList() {
        mView.showBirdsPinyinList(mDataSource.getBirdsPinyinList());
    }

    @Override
    public void loadBirdDetail(String name) {
        int id = mDataSource.getIdByChineseName(name);
        mView.showBirdDetail(id);
    }
}