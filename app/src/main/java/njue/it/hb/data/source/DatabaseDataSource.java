package njue.it.hb.data.source;

import java.util.List;
import java.util.Map;

import njue.it.hb.model.Bird;
import njue.it.hb.model.BirdListItem;

/**
 * 由于这是数据库接口，所以此接口作为基础接口，可供其他数据接口调用
 */
public interface DatabaseDataSource {

    /**
     * 通过鸟类id，取得Bird类
     */
    Bird getBirdById(int id);

    /**
     * 通过鸟的中文名，来取得id
     */
    int getIdByChineseName(String name);

    /**
     * 获取鸟类以科种排序列表，从sqlite数据库获取
     */
    List<Map<String,List<BirdListItem>>> getBirdsOrderList();

    /**
     * 获取鸟类拼音排序列表
     */
    List<BirdListItem> getBirdsPinyinList();

    /**
     * 搜索关机词
     */
    List<BirdListItem> search(String keyword);

    /**
     * 保存搜索记录
     */
    void saveSearchHistory(int birdId);

    /**
     * 取得所有历史
     */
    List<BirdListItem> getSearchHistory();

    /**
     * 通过id号取得BirdListItem
     */
    BirdListItem getBirdListItemById(int birdId);
}