package njue.it.hb.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;


public class Bird {

    /**
     * 鸟Id
     */
    public ObservableInt id = new ObservableInt();

    /**
     * 鸟中文名称
     */
    public ObservableField<String> name = new ObservableField<>();

    /**
     * 中文拼音首字母
     */
    public ObservableField<String> headPinyin = new ObservableField<>();

    /**
     * 拉丁名
     */
    public ObservableField<String> latinName = new ObservableField<>();

    /**
     * 所属目
     */
    public ObservableField<String> order = new ObservableField<>();

    /**
     * 所属科
     */
    public ObservableField<String> family = new ObservableField<>();

    /**
     * 鸟图片路径列表
     */
    public ObservableArrayList<String> imagePaths = new ObservableArrayList<>();

    /**
     * 体型
     */
    public ObservableField<String> shape = new ObservableField<>();

    /**
     * 栖息地
     */
    public ObservableField<String> habit = new ObservableField<>();

    /**
     * 主色
     */
    public ObservableField<String> mainColor = new ObservableField<>();

    /**
     * 次色
     */
    public ObservableField<String> secondaryColor = new ObservableField<>();

    /**
     * 喙型、喙长
     */
    public ObservableField<String> beak = new ObservableField<>();

    /**
     * 叫声
     */
    public ObservableField<String> chirp = new ObservableField<>();

    /**
     * 飞行情况
     */
    public ObservableField<String> flyDetail = new ObservableField<>();

    /**
     * 尾型
     */
    public ObservableField<String> tail = new ObservableField<>();

    /**
     * 食性
     */
    public ObservableField<String> feeding = new ObservableField<>();

    /**
     * 模糊特征
     */
    public ObservableField<String> fuzzyFeature = new ObservableField<>();

    /**
     * 鸟语图路径
     */
    public ObservableArrayList<String> twitterImagePath = new ObservableArrayList<>();

    /**
     * 鸟声音文件路径
     */
    public ObservableArrayList<String> twitterPath = new ObservableArrayList<>();

    /**
     * 列表头图片路径
     */
    public ObservableField<String> imageListPath = new ObservableField<>();

/*
    protected Bird(Parcel in) {
        id.set(in.readInt());
        name.set(in.readString());
        imagePaths = (ObservableArrayList<String>) in.readArrayList(String.class.getClassLoader());
        twitterImagePath.set(in.readString());
        twitterPath.set(in.readString());
        shape.set(in.readString());
        habit.set(in.readString());
        mainColor.set(in.readString());
        secondaryColor.set(in.readString());
        beak.set(in.readString());
        chirp.set(in.readString());
        flyDetail.set(in.readString());
        tail.set(in.readString());
        feeding.set(in.readString());
        fuzzyFeature.set(in.readString());
        order.set(in.readString());
        family.set(in.readString());
        latinName.set(in.readString());
    }

    public static final Creator<Bird> CREATOR = new Creator<Bird>() {
        @Override
        public Bird createFromParcel(Parcel in) {
            return new Bird(in);
        }

        @Override
        public Bird[] newArray(int size) {
            return new Bird[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id.get());
        dest.writeString(name.get());
        dest.writeList(imagePaths);
        dest.writeString(twitterImagePath.get());
        dest.writeString(twitterPath.get());
        dest.writeString(shape.get());
        dest.writeString(habit.get());
        dest.writeString(mainColor.get());
        dest.writeString(secondaryColor.get());
        dest.writeString(beak.get());
        dest.writeString(chirp.get());
        dest.writeString(flyDetail.get());
        dest.writeString(tail.get());
        dest.writeString(feeding.get());
        dest.writeString(fuzzyFeature.get());
        dest.writeString(order.get());
        dest.writeString(family.get());
        dest.writeString(latinName.get());
    }*/
}