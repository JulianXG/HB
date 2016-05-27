package njue.it.hb.data.source;

import java.util.List;
import java.util.Map;

import njue.it.hb.model.Bird;
import njue.it.hb.model.BirdOrderListItem;

public interface BirdDataSource {

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
    List<Map<String,List<BirdOrderListItem>>> getBirdsOrderList();

    /**
     * 获取鸟类拼音排序列表
     */
    Map<String, List<String>> getBirdsPinyinList();
}