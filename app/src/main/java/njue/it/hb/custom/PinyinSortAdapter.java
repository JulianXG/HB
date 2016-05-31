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
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView letter = (TextView) convertView.findViewById(R.id.tip_dialog);

        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(mList.get(position).cnName.get());

        int section = getSectionForPosition(position);
//
//        if (position == getPositionForSection(section)) {
//            letter.setVisibility(View.VISIBLE);
//            letter.setText(item.initialPinyin.get());
//        } else {

//        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return mList.toArray();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).initialPinyin.get();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).initialPinyin.get().charAt(0);
    }

    public void updateData(List<BirdListItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

}
