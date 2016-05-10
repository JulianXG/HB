package njue.it.hb.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import njue.it.hb.R;
import njue.it.hb.contract.BirdsListContract;
import njue.it.hb.data.repository.BirdsListRepository;
import njue.it.hb.databinding.ExpandBirdsOrderListItemBinding;
import njue.it.hb.databinding.FragmentBirdsListBinding;
import njue.it.hb.model.BirdOrderListItem;
import njue.it.hb.presenter.BirdsListPresenter;

public class BirdsListFragment extends Fragment implements BirdsListContract.view {

    private BirdsListContract.presenter mPresenter;

    private DrawerLayout mDrawerLayout;

    /**
     * 科种排序的鸟类列表Adapter
     */
    private BirdsOrderListAdapter mBirdsOrderListAdapter;

    /**
     * 总Fragment的Binding
     */
    private FragmentBirdsListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_birds_list, container, false);

        //为了保证onOptionsItemSelected有效
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);//修改图标
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);//决定图标是否可以点击
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标

        //绑定Presenter
        mPresenter = new BirdsListPresenter(new BirdsListRepository(root.getContext()),this);

        mBinding = DataBindingUtil.bind(root);
        mPresenter.loadBirdsOrderList();
        return root;
    }

    public static BirdsListFragment newInstance(){
        return new BirdsListFragment();
    }


    @Override
    public void setPresenter(BirdsListContract.presenter presenter) {
        mPresenter=presenter;
    }

    /**
     * 添加toolbar左上角的点击功能
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showBirdsOrderList(List<Map<String, List<BirdOrderListItem>>> birdList) {
        mBinding.expBirdsOrderList.setAdapter(mBirdsOrderListAdapter);
        mBirdsOrderListAdapter = new BirdsOrderListAdapter(birdList, getContext());
        mBirdsOrderListAdapter.setData(birdList);
    }

    @Override
    public void showBirdsPinyinList(Map<String, List<String>> birdList) {

    }

    @Override
    public void showBirdDetail(Object birdData) {

    }

    class BirdsOrderListAdapter implements ExpandableListAdapter {

        private Context mContext;

        private List<Map<String ,List<BirdOrderListItem>>> birdsOrderList;

        private ExpandBirdsOrderListItemBinding mBinding;

        public BirdsOrderListAdapter(List<Map<String, List<BirdOrderListItem>>> birdsOrderList, Context context) {
            this.birdsOrderList = birdsOrderList;
            mContext = context;
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
            return birdsOrderList.get(groupPosition).values().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return birdsOrderList.get(groupPosition).keySet().toArray()[0];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return birdsOrderList.get(groupPosition).values().toArray()[childPosition];
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
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);

            TextView orderText = (TextView) convertView.findViewById(android.R.id.text1);
            orderText.setText((String) getGroup(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.expand_birds_order_list_item, null);
            }
            mBinding = ExpandBirdsOrderListItemBinding.bind(convertView);

            mBinding.setItem((BirdOrderListItem) getChild(groupPosition,childPosition));
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
    }


}