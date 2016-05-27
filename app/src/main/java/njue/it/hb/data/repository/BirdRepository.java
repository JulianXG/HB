package njue.it.hb.data.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njue.it.hb.data.source.BirdDataSource;
import njue.it.hb.model.Bird;
import njue.it.hb.model.BirdOrderListItem;
import njue.it.hb.util.SQLiteUtil;

public class BirdRepository implements BirdDataSource {

    private SQLiteDatabase mDatabase;

    public BirdRepository() {
        mDatabase = SQLiteUtil.getDatabase();
    }

    @Override
    public Bird getBirdById(int id) {
        Bird result = new Bird();
        result.id.set(id);
        String sql = "SELECT * FROM bird WHERE id =?";
        Cursor cursor = mDatabase.rawQuery(sql, new String[]{id+""});
        while (cursor.moveToNext()) {
            result.name.set(cursor.getString(cursor.getColumnIndex("name")));
            result.latinName.set(cursor.getString(cursor.getColumnIndex("latin_name")));
            result.order.set(cursor.getString(cursor.getColumnIndex("order")));
            result.family.set(cursor.getString(cursor.getColumnIndex("family")));
            result.shape.set(cursor.getString(cursor.getColumnIndex("shape")));
            result.habit.set(cursor.getString(cursor.getColumnIndex("habitat")));
            result.mainColor.set(cursor.getString(cursor.getColumnIndex("main_color")));
            result.secondaryColor.set(cursor.getString(cursor.getColumnIndex("secondary_color")));
            result.beak.set(cursor.getString(cursor.getColumnIndex("beak")));
            result.chirp.set(cursor.getString(cursor.getColumnIndex("chirp")));
            result.flyDetail.set(cursor.getString(cursor.getColumnIndex("fly_detail")));
            result.tail.set(cursor.getString(cursor.getColumnIndex("tail")));
            result.feeding.set(cursor.getString(cursor.getColumnIndex("feeding")));
            result.fuzzyFeature.set(cursor.getString(cursor.getColumnIndex("fuzzy_feature")));
            result.headPinyin.set(cursor.getString(cursor.getColumnIndex("head_pinyin")));
            result.imageListPath.set(cursor.getString(cursor.getColumnIndex("img_list")));

            String imgPaths = cursor.getString(cursor.getColumnIndex("img_paths"));
            String twitterImagePaths = cursor.getString(cursor.getColumnIndex("song_img_path"));
            String twitterPaths = cursor.getString(cursor.getColumnIndex("song_path"));

            result.imagePaths.addAll(simpleSplit(imgPaths));
            result.twitterImagePath.addAll(simpleSplit(twitterImagePaths));
            result.twitterPath.addAll(simpleSplit(twitterPaths));
        }

        cursor.close();
        return result;
    }

    @Override
    public int getIdByChineseName(String name) {
        int result = 0;
        String sql = "SELECT id FROM bird WHERE name=?";
        Cursor cursor = mDatabase.rawQuery(sql, new String[]{name});
        while (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        return result;
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
            sql = "SELECT name,latin_name,img_list FROM bird where family = ?";
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
        cursor.close();
        return result;
    }

    @Override
    public Map<String, List<String>> getBirdsPinyinList() {
        return null;
    }

    private List<String> splitPathsToList(String paths,char delimiter) {
        List<String> result = new ArrayList<>();
        String[] s = paths.split(String.valueOf(delimiter));
        String path = s[0].substring(0, s[0].lastIndexOf('/'));
        s[0] = s[0].substring(path.length());
        for (int i = 0; i < s.length; i++) {
            result.add(path + s[i]);
        }
        return result;
    }

    private List<String> simpleSplit(String paths) {
        return splitPathsToList(paths, ';');
    }
}