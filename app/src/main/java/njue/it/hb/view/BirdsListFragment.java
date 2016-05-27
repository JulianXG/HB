package njue.it.hb.view;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.databinding.tool.expr.BracketExpr;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.BirdsListContract;
import njue.it.hb.data.repository.BirdRepository;
import njue.it.hb.databinding.ExpandBirdsOrderListItemBinding;
import njue.it.hb.databinding.ExpandBirdsOrderListParentItemBinding;
import njue.it.hb.databinding.FragmentBirdsListBinding;
import njue.it.hb.model.BirdOrderListItem;
import njue.it.hb.presenter.BirdsListPresenter;
import njue.it.hb.util.ImageUtil;

public class BirdsListFragment extends Fragment implements BirdsListContract.view,RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "BirdsListFragment";

    public static final String KEY_INTENT_BIRD_ID = "INTENT_BIRD";

    private BirdsListContract.presenter mPresenter;

    private ExpandableListView mBirdOrderListView;

    /**
     * 总Fragment的Binding
     */
    private FragmentBirdsListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_birds_list, container, false);
        //绑定Presenter
        mPresenter = new BirdsListPresenter(new BirdRepository(),this);
        mBinding = DataBindingUtil.bind(root);
        mBinding.radioGroup.setOnCheckedChangeListener(this);

        mBirdOrderListView = new ExpandableListView(getContext());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.radioFamilyOrder.setChecked(true);
        mPresenter.loadBirdsOrderList();
    }

    public static BirdsListFragment newInstance(){
        return new BirdsListFragment();
    }

    @Override
    public void setPresenter(BirdsListContract.presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBirdsOrderList(List<Map<String, List<BirdOrderListItem>>> birdList) {
        mBinding.birdsListContent.removeAllViews();
        BirdsOrderListAdapter birdsOrderListAdapter = new BirdsOrderListAdapter(birdList, getContext());
        mBirdOrderListView.setAdapter(birdsOrderListAdapter);
        birdsOrderListAdapter.setData(birdList);
        mBinding.birdsListContent.addView(mBirdOrderListView);
    }

    @Override
    public void showBirdsPinyinList(Map<String, List<String>> birdList) {
        mBinding.birdsListContent.removeAllViews();
    }

    @Override
    public void showBirdDetail(int id) {
        Intent intent = new Intent(getContext(),BirdDetailActivity.class);
        intent.putExtra(KEY_INTENT_BIRD_ID, id);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_family_order:
                mPresenter.loadBirdsOrderList();
                break;
            case R.id.radio_pinyin:
                mPresenter.loadBirdsPinyinList();
                break;
        }
    }

    class BirdsOrderListAdapter implements ExpandableListAdapter {

        private Context mContext;

        private List<Map<String ,List<BirdOrderListItem>>> birdsOrderList;

        private ExpandBirdsOrderListItemBinding mChildBinding;

        private ExpandBirdsOrderListParentItemBinding mParentBinding;

        /**
         * 头像暂存内存的map，key值为gropuPosition*100+childPosition
         */
        private Map<Integer,Bitmap> avatars;

        public BirdsOrderListAdapter(List<Map<String, List<BirdOrderListItem>>> birdsOrderList, Context context) {
            this.birdsOrderList = birdsOrderList;
            mContext = context;
            avatars = new HashMap<>();
        }

        public void setData(List<Map<String, List<BirdOrderListItem>>> data) {
            birdsOrderList = data;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return birdsOrderList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return ((List) birdsOrderList.get(groupPosition).values().toArray()[0]).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return birdsOrderList.get(groupPosition).keySet().toArray()[0];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return ((List) birdsOrderList.get(groupPosition).values().toArray()[0]).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.expand_birds_order_list_parent_item, null);
            }
            mParentBinding = DataBindingUtil.bind(convertView);
            String family = (String) getGroup(groupPosition);
            mParentBinding.setFamily(family);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.expand_birds_order_list_item, null);
            }
            mChildBinding = DataBindingUtil.bind(convertView);
            final BirdOrderListItem item = (BirdOrderListItem) getChild(groupPosition, childPosition);
            mChildBinding.setItem(item);

            //在此处添加监听器
            mChildBinding.orderListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.loadBirdData(item.cnName.get());
                }
            });

            Bitmap avatar = avatars.get(groupPosition * 100 + childPosition);
            if (avatar == null) {
                mChildBinding.imgBirdAvatar.setImageBitmap(null);
                Handler handler = new LoadImageHandler(childPosition, groupPosition, mChildBinding);
                ImageUtil.getBitmapInThread(handler, GlobalConstant.HB_DATA_FILE_PATH + item.avatarPath.get(), 50, 50);
            }else {
                showImage(mChildBinding, avatar);
            }

//                mChildBinding.imgBirdAvatar.setImageBitmap(ImageUtil.getBitmap(GlobalConstant.HB_DATA_FILE_PATH + item.avatarPath.get(), 50, 50));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }

        private void showLoadingImage(ExpandBirdsOrderListItemBinding childBinding) {
            childBinding.orderListAvatarLoading.setVisibility(View.VISIBLE);
            childBinding.imgBirdAvatar.setVisibility(View.GONE);
        }

        private void showImage(ExpandBirdsOrderListItemBinding childBinding, Bitmap bitmap) {
            childBinding.orderListAvatarLoading.setVisibility(View.GONE);
            childBinding.imgBirdAvatar.setVisibility(View.VISIBLE);
            childBinding.imgBirdAvatar.setImageBitmap(bitmap);
        }

        private void showLoadingImageError(ExpandBirdsOrderListItemBinding childBinding) {
            childBinding.orderListAvatarLoading.setVisibility(View.GONE);
            childBinding.imgBirdAvatar.setVisibility(View.VISIBLE);
        }

        class LoadImageHandler extends Handler {

            private ExpandBirdsOrderListItemBinding mChildBinding;

            private int groupPosition;

            private int childPosition;

            public LoadImageHandler(int childPosition, int groupPosition, ExpandBirdsOrderListItemBinding childBinding) {
                this.childPosition = childPosition;
                this.groupPosition = groupPosition;
                mChildBinding = childBinding;
            }

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ImageUtil.START:
                        showLoadingImage(mChildBinding);
                        break;
                    case ImageUtil.DECODING:
                        showLoadingImage(mChildBinding);
                        break;
                    case ImageUtil.COMPLETE:
                        Bundle bundle = msg.getData();
                        Bitmap bitmap = (Bitmap) bundle.get(ImageUtil.KEY_BITMAP);
                        showImage(mChildBinding,bitmap);

                        avatars.put(groupPosition * 100 + childPosition, bitmap);
                        break;
                    case ImageUtil.ERROR:
                        showLoadingImageError(mChildBinding);
                        break;
                }
            }
        }

    }



}