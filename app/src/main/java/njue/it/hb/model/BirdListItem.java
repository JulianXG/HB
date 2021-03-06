package njue.it.hb.model;

import android.databinding.ObservableField;

public class BirdListItem {

    /**
     * 列表图片
     */
    public ObservableField<String> avatarPath = new ObservableField<>();

    /**
     * 中文名
     */
    public ObservableField<String> cnName = new ObservableField<>();

    /**
     * 拉丁名
     */
    public ObservableField<String> latinName = new ObservableField<>();

    /**
     * 拼音首字母
     */
    public ObservableField<String> initialPinyin = new ObservableField<>();
}