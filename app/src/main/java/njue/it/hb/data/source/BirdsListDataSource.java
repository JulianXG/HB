package njue.it.hb.data.source;

import java.util.List;
import java.util.Map;

import njue.it.hb.model.BirdOrderListItem;

public interface BirdsListDataSource {

    /**
     * 获取鸟类以科种排序列表，从sqlite数据库获取
     */
    List<Map<String,List<BirdOrderListItem>>> getBirdsOrderList();

    /**
     * 获取鸟类拼音排序列表
     */
    Map<String, List<String>> getBirdsPinyinList();


}