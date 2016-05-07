package njue.it.hb.data.repository;

import java.util.List;
import java.util.Map;

import njue.it.hb.data.source.BirdsListDataSource;

public class BirdsListRepository implements BirdsListDataSource {

    @Override
    public Map<String, List<String>> getBirdsOrderList() {
        return null;
    }

    @Override
    public Map<String, List<String>> getBirdsPinyinList() {
        return null;
    }
}