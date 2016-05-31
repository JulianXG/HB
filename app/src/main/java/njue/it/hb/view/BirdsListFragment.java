package njue.it.hb.view;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.BirdsListContract;
import njue.it.hb.contract.ImageInThreadContract;
import njue.it.hb.custom.PinyinSideBar;
import njue.it.hb.custom.PinyinSortAdapter;
import njue.it.hb.data.repository.DatabaseRepository;
import njue.it.hb.databinding.FragmentBirdsListBinding;
import njue.it.hb.databinding.ItemBirdsOrderBinding;
import njue.it.hb.databinding.ItemExpandParentBinding;
import njue.it.hb.model.BirdListItem;
import njue.it.hb.presenter.BirdsListPresenter;
import njue.it.hb.presenter.ImageInThreadPresenter;
import njue.it.hb.util.ImageUtil;

public class BirdsListFragment extends Fragment implements BirdsListContract.View,RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "BirdsListFragment";

    private BirdsListContract.Presenter mPresenter;

    private ExpandableListView mBirdOrderListView;

    private TextView mTip;

    private FragmentBirdsListBinding mBinding;

    private RelativeLayout mLayoutPinyin;

    private DrawerLayout mDrawerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_birds_list, container, false);
        mBinding = DataBindingUtil.bind(root);
        mBirdOrderListView = mBinding.expand;
        mTip = mBinding.listTip;
        mLayoutPinyin = mBinding.pinyin;

        //为了保证onOptionsItemSelected有效
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);                     //决定图标是否可以点击

        //绑定Presenter
        try {
            mPresenter = new BirdsListPresenter(new DatabaseRepository(),this);
            mPresenter.loadBirdsOrderList();
            mBinding.radioGroup.setOnCheckedChangeListener(this);
            mBinding.radioFamilyOrder.setChecked(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showImportDataError();
        }
        return root;
    }

    public static BirdsListFragment newInstance(){
        return new BirdsListFragment();
    }

    @Override
    public void setPresenter(BirdsListContract.Presenter Presenter) {
        mPresenter = Presenter;
    }

    @Override
    public void showBirdsOrderList(List<Map<String, List<BirdListItem>>> birdList) {
        mTip.setVisibility(View.GONE);
        mLayoutPinyin.setVisibility(View.GONE);
        BirdsOrderListAdapter birdsOrderListAdapter = new BirdsOrderListAdapter(birdList, getContext());
        mBirdOrderListView.setAdapter(birdsOrderListAdapter);
        birdsOrderListAdapter.setData(birdList);
        mBirdOrderListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBirdsPinyinList(final List<BirdListItem> birdList) {
        mTip.setVisibility(View.GONE);
        mBirdOrderListView.setVisibility(View.GONE);
        mLayoutPinyin.setVisibility(View.VISIBLE);
        PinyinSideBar sideBar = mBinding.sideBar;
        final ListView pinyinListView = mBinding.listPinyin;
        final PinyinSortAdapter adapter = new PinyinSortAdapter(getContext(), birdList);

        sideBar.setOnTouchingLetterChangedListener(new PinyinSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(int section) {
                int position = adapter.getPositionForSection(section);
                if (position != -1) {
                    pinyinListView.requestFocusFromTouch();
                    pinyinListView.setSelection(position);
                                                                                                                                                                                                                                        Log.i(TAG, "onTouchingLetterChanged: pinyinList setSelection: " + position + "\tpinyin: " + section);
                }
            }
        });

        pinyinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.loadBirdDetail(birdList.get(position).cnName.get());
            }
        });

        pinyinListView.setAdapter(adapter);
    }

    @Override
    public void showBirdDetail(int id) {
        Intent intent = new Intent(getContext(),BirdDetailActivity.class);
        intent.putExtra(GlobalConstant.KEY_INTENT_BIRD_ID, id);
        startActivity(intent);
    }

    @Override
    public void showImportDataError() {
        mBirdOrderListView.setVisibility(View.GONE);
        mLayoutPinyin.setVisibility(View.GONE);
        mTip.setVisibility(View.VISIBLE);
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

    /**
     * 添加toolbar左上角的点击功能
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class BirdsOrderListAdapter implements ExpandableListAdapter {

        private Context mContext;

        private List<Map<String ,List<BirdListItem>>> birdsOrderList;

        private ItemBirdsOrderBinding mChildBinding;

        private ItemExpandParentBinding mParentBinding;

        /**
         * 头像暂存内存的map，key值为groupPosition*100+childPosition
         */
        private Map<Integer,Bitmap> avatars;

        public BirdsOrderListAdapter(List<Map<String, List<BirdListItem>>> birdsOrderList, Context context) {
            this.birdsOrderList = birdsOrderList;
            mContext = context;
            avatars = new HashMap<>();
        }

        public void setData(List<Map<String, List<BirdListItem>>> data) {
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
                convertView = inflater.inflate(R.layout.item_expand_parent, null);
            }
            mParentBinding = DataBindingUtil.bind(convertView);
            String family = (String) getGroup(groupPosition);
            mParentBinding.setTitle(family);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.item_birds_order, null);
            }
            mChildBinding = DataBindingUtil.bind(convertView);
            final BirdListItem item = (BirdListItem) getChild(groupPosition, childPosition);
            mChildBinding.setItem(item);

            //在此处添加监听器
            mChildBinding.orderListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.loadBirdDetail(item.cnName.get());
                }
            });

            Bitmap avatar = avatars.get(groupPosition * 100 + childPosition);
            AvatarView avatarView = new AvatarView(childPosition, groupPosition, mChildBinding);
            if (avatar == null) {
                mChildBinding.imgBirdAvatar.setImageBitmap(null);
                Handler handler = new ImageInThreadPresenter(avatarView);
                ImageUtil.getBitmapInThread(handler, GlobalConstant.HB_DATA_FILE_PATH + item.avatarPath.get(), 50, 50);
            }else {
                avatarView.showImage(avatar);
            }

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

        class AvatarView implements ImageInThreadContract.View {

            private ItemBirdsOrderBinding mBinding;

            private int groupPosition,childPosition;

            public AvatarView(int childPosition, int groupPosition, ItemBirdsOrderBinding binding) {
                this.childPosition = childPosition;
                this.groupPosition = groupPosition;
                mBinding = binding;
            }

            @Override
            public void showLoadingImage() {
                mBinding.orderListAvatarLoading.setVisibility(View.VISIBLE);
                mBinding.imgBirdAvatar.setVisibility(View.GONE);
            }

            @Override
            public void showImage(Bitmap bitmap) {
                mBinding.orderListAvatarLoading.setVisibility(View.GONE);
                mBinding.imgBirdAvatar.setVisibility(View.VISIBLE);
                mBinding.imgBirdAvatar.setImageBitmap(bitmap);
            }

            @Override
            public void showLoadingImageError() {
                mBinding.orderListAvatarLoading.setVisibility(View.GONE);
                mBinding.imgBirdAvatar.setVisibility(View.VISIBLE);
            }

            @Override
            public void saveImage(Bitmap bitmap) {
                avatars.put(groupPosition * 100 + childPosition, bitmap);
            }
        }

    }

}