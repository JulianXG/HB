package njue.it.hb.view;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.IntroductionContract;
import njue.it.hb.databinding.FragmentIntroductionBinding;
import njue.it.hb.databinding.ItemExpandParentBinding;

public class IntroductionFragment extends Fragment implements IntroductionContract.View {

    private IntroductionContract.Presenter mPresenter;

    private FragmentIntroductionBinding mBinding;

    private DrawerLayout mDrawerLayout;

    public static final String KEY_BASIC_FACT = "1";

    public static final String KEY_WHAT_IS_BIRD = "2";

    public static final String KEY_WATCH_BIRD = "3";

    private ExpandAdapter mAdapter;

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_introduction, container, false);
        mBinding = DataBindingUtil.bind(root);
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.introduction_title);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);//修改图标
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);//决定图标是否可以点击
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标

        mAdapter = new ExpandAdapter(getContext());
        mBinding.expand.setAdapter(mAdapter);

        return root;
    }

    public static IntroductionFragment newInstance() {
        return new IntroductionFragment();
    }

    @Override
    public void setPresenter(IntroductionContract.Presenter Presenter) {
        mPresenter = Presenter;
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

    @Override
    public void showHBIntroduction() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_INTRODUCTION, KEY_BASIC_FACT);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showWhatIsBird() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_INTRODUCTION, KEY_WHAT_IS_BIRD);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showHowToWatchBird() {
        Intent intent=new Intent();
        intent.putExtra(GlobalConstant.KEY_INTENT_INTRODUCTION, KEY_WATCH_BIRD);
        intent.setClass(getActivity(),CommonContentActivity.class);
        getActivity().startActivity(intent);
    }

    class ExpandAdapter implements ExpandableListAdapter {

        private String[] titles = {
                getString(R.string.title_hb_basic_fact),
                getString(R.string.title_what_is_bird),
                getString(R.string.title_how_to_watch_bird)
        };

        private int[] contents = {
                R.string.hb_basic_fact,
                R.string.what_is_bird,
                R.string.how_to_watch_bird
        };

        private List<List<Integer>> mData;

        private Context mContext;

        private ItemExpandParentBinding mParentBinding;

        public ExpandAdapter(Context context) {
            mContext = context;
            mData = new ArrayList<>();
            for (int content : contents) {
                List<Integer> item = new ArrayList<>();
                item.add(content);
                mData.add(item);
            }
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return mData.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mData.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return titles[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return contents[childPosition];
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
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_expand_parent, null);
            mParentBinding = DataBindingUtil.bind(convertView);
            mParentBinding.setTitle((String) getGroup(groupPosition));

            return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            LinearLayout layout = new LinearLayout(mContext);
            TextView content = new TextView(mContext);
            layout.addView(content);
            content.setMovementMethod(ScrollingMovementMethod.getInstance());
            content.setText( mData.get(groupPosition).get(childPosition));
            return layout;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
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