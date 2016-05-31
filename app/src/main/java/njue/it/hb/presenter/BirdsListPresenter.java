package njue.it.hb.presenter;

import android.database.sqlite.SQLiteCantOpenDatabaseException;

import java.util.List;
import java.util.Map;

import njue.it.hb.contract.BirdsListContract;
import njue.it.hb.data.source.DatabaseDataSource;
import njue.it.hb.model.BirdListItem;

public class BirdsListPresenter implements BirdsListContract.Presenter {

    private BirdsListContract.View mView;

    private DatabaseDataSource mDataSource;

    public BirdsListPresenter(DatabaseDataSource dataSource, BirdsListContract.View View) {
        mDataSource = dataSource;
        mView = View;
        mView.setPresenter(this);
    }

    @Override
    public void loadBirdsOrderList() {
        try {
            List<Map<String, List<BirdListItem>>> list = mDataSource.getBirdsOrderList();
            mView.showBirdsOrderList(list);
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            mView.showImportDataError();
        }
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