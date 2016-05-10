package njue.it.hb.presenter;

import java.util.List;
import java.util.Map;

import njue.it.hb.contract.BirdsListContract;
import njue.it.hb.data.source.BirdsListDataSource;
import njue.it.hb.databinding.ExpandBirdsOrderListItemBinding;
import njue.it.hb.model.BirdOrderListItem;

public class BirdsListPresenter implements BirdsListContract.presenter {

    private BirdsListContract.view mView;

    private BirdsListDataSource mDataSource;

    public BirdsListPresenter(BirdsListDataSource dataSource, BirdsListContract.view view) {
        mDataSource = dataSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadBirdsOrderList() {
        List<Map<String, List<BirdOrderListItem>>> list = mDataSource.getBirdsOrderList();
        mView.showBirdsOrderList(list);

    }

    @Override
    public void loadBirdsPinyinList() {
        mView.showBirdsPinyinList(mDataSource.getBirdsPinyinList());
    }

    @Override
    public void loadBirdData(Long birdId) {

    }
}