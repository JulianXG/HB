package njue.it.hb.data.repository;

import android.util.Log;

import java.util.List;

import njue.it.hb.data.source.DatabaseDataSource;
import njue.it.hb.data.source.IndexDataSource;
import njue.it.hb.model.BirdListItem;

public class IndexRepository implements IndexDataSource {

    private static final String TAG = "IndexRepository";
    
    private DatabaseDataSource mDatabaseDataSource;

    public IndexRepository() {
        mDatabaseDataSource = new DatabaseRepository();
    }

    @Override
    public List<BirdListItem> search(String keyword) {
        List<BirdListItem> list = mDatabaseDataSource.search(keyword);
        Log.i(TAG, "search: 结果总数：" + list.size() + "\t关键词：" + keyword);
        return list;
    }

    @Override
    public void saveHistory(int birdId) {
        mDatabaseDataSource.saveSearchHistory(birdId);
    }

    @Override
    public List<BirdListItem> getHistory() {
        return mDatabaseDataSource.getSearchHistory();
    }

    @Override
    public int getIdByChineseName(String name) {
        return mDatabaseDataSource.getIdByChineseName(name);
    }

}