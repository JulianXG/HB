package njue.it.hb.custom;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.ImageInThreadContract;
import njue.it.hb.databinding.ItemBirdsOrderBinding;
import njue.it.hb.model.BirdListItem;
import njue.it.hb.presenter.ImageInThreadPresenter;
import njue.it.hb.util.ImageUtil;

public class PinyinSortAdapter extends BaseAdapter implements SectionIndexer {

    List<BirdListItem> mList;

    private Context mContext;

    private ItemBirdsOrderBinding mBinding;

    private static final String[] INDEX_PINYIN = {"A", "B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};

    public PinyinSortAdapter(Context context, List<BirdListItem> list) {
        mContext = ((Activity) context);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_pinyin_list, null);
        }
        TextView category = (TextView) convertView.findViewById(R.id.pinyin_category);

        TextView name = (TextView) convertView.findViewById(R.id.bird_name);
        name.setText(mList.get(position).cnName.get());

        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            category.setVisibility(View.VISIBLE);
            category.setText(mList.get(position).initialPinyin.get().toUpperCase());
        } else {
            category.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return mList.toArray();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        int result=-1;
        String section = INDEX_PINYIN[sectionIndex];
        for (int i = 0; i < mList.size(); i++) {
            String pinyin = mList.get(i).initialPinyin.get().toUpperCase();
            if (section.equals(pinyin)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public int getSectionForPosition(int position) {
        int result = -1;
        String pinyin = mList.get(position).initialPinyin.get().toUpperCase();
        for (int i = 0; i < INDEX_PINYIN.length; i++) {
            if (pinyin.equals(INDEX_PINYIN[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

}
