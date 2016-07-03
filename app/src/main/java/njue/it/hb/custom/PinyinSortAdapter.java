package njue.it.hb.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import njue.it.hb.R;
import njue.it.hb.model.BirdListItem;

public class PinyinSortAdapter extends BaseAdapter implements SectionIndexer {

    List<BirdListItem> mList;

    private Context mContext;

    private static final String[] INDEX_PINYIN = {"A", "B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};

    public PinyinSortAdapter(Context context, List<BirdListItem> list) {
        mContext = context;
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
