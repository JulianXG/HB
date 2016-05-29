package njue.it.hb.data.source;

import java.util.List;

import njue.it.hb.model.BirdListItem;

public interface IndexDataSource {

    /**
     * 搜索关键字
     */
    List<BirdListItem> search(String keyword);

    /**
     * 保存搜索历史
     */
    void saveHistory(int birdId);

    /**
     * 取得搜索历史
     */
    List<BirdListItem> getHistory();

    /**
     * 通过中文名取得鸟类id，此处直接使用database接口
     */
    int getIdByChineseName(String name);

}