package njue.it.hb.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njue.it.hb.data.source.BirdsListDataSource;
import njue.it.hb.model.BirdOrderListItem;
import njue.it.hb.util.SQLiteUtil;

public class BirdsListRepository implements BirdsListDataSource {

    private SQLiteDatabase mDatabase;

    public BirdsListRepository(Context context) {
        mDatabase = new SQLiteUtil(context).getDatabase();
    }

    @Override
    public List<Map<String, List<BirdOrderListItem>>> getBirdsOrderList() {
        List<Map<String, List<BirdOrderListItem>>> result = new ArrayList<>();
        List<String> familyList = new ArrayList<>();
        String sql = "SELECT DISTINCT family FROM bird";
        Cursor cursor = mDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            familyList.add(cursor.getString(0));
        }
        cursor.close();
        for (String family : familyList) {
            sql = "SELECT name,latin_name,img_paths FROM bird where family = ?";
            cursor = mDatabase.rawQuery(sql, new String[]{family});
            List<BirdOrderListItem> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                BirdOrderListItem item = new BirdOrderListItem();
                item.cnName.set(cursor.getString(0));
                item.latinName.set(cursor.getString(1));
                item.avatarPath.set(cursor.getString(2));
                list.add(item);
            }
            Map<String, List<BirdOrderListItem>> map = new HashMap<>();
            map.put(family, list);
            result.add(map);
        }
        return result;
    }

    @Override
    public Map<String, List<String>> getBirdsPinyinList() {
        return null;
    }
}