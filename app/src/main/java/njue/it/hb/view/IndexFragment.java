package njue.it.hb.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.ImageInThreadContract;
import njue.it.hb.contract.IndexContract;
import njue.it.hb.data.repository.IndexRepository;
import njue.it.hb.databinding.FragmentIndexBinding;
import njue.it.hb.databinding.ItemBirdsOrderBinding;
import njue.it.hb.model.BirdListItem;
import njue.it.hb.presenter.ImageInThreadPresenter;
import njue.it.hb.presenter.IndexPresenter;
import njue.it.hb.util.ImageUtil;

public class IndexFragment extends Fragment implements IndexContract.View {

    private IndexContract.Presenter mPresenter;

    private FragmentIndexBinding mBinding;

    private ListAdapter mAdapter;

    private DrawerLayout mDrawerLayout;

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.fragment_index, container, false);
        mBinding = DataBindingUtil.bind(root);
        mAdapter = new ListAdapter(getContext(), null);
        //为了保证onOptionsItemSelected有效
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);

        toolbar.setTitle("");       //暂时用这个方式代替实现，没找到更好的办法
        TextView title = (TextView) root.findViewById(R.id.title);
        title.setText(R.string.index_title);

        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);       //修改图标
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);                     //决定图标是否可以点击
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
        mBinding.search.addTextChangedListener(new SearchWatcher());
        mBinding.clear.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                clearSearchContent();
                hideClearSearchContent();
                mPresenter.loadHistory();
            }
        });
        mBinding.indexListContent.setAdapter(mAdapter);

        try {
            mPresenter = new IndexPresenter(new IndexRepository(), this);
            mPresenter.loadHistory();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showImportDataError();      //找不到数据库文件在这里处理
        }

        return root;
    }

    public static IndexFragment newInstance(){
        return new IndexFragment();
    }

    @Override
    public void showSearchHistory(List<BirdListItem> birds) {
        closeTip();
        mBinding.indexListContent.setVisibility(android.view.View.VISIBLE);
        mBinding.setCaption(getString(R.string.caption_search_history));
        mAdapter.updateData(birds);
    }

    @Override
    public void showSearchResult(List<BirdListItem> birds) {
        closeTip();
        mBinding.indexListContent.setVisibility(android.view.View.VISIBLE);
        mBinding.setCaption(getString(R.string.caption_search_result));
        mAdapter.updateData(birds);
    }

    @Override
    public void showBirdDetail(int birdId) {
        Intent intent = new Intent(getContext(), BirdDetailActivity.class);
        intent.putExtra(GlobalConstant.KEY_INTENT_BIRD_ID, birdId);
        startActivity(intent);
    }

    @Override
    public void showSearching() {
        mBinding.setCaption(getResources().getString(R.string.caption_loading_searching));
    }

    @Override
    public void closeSearching() {
        mBinding.setCaption(getString(R.string.caption_close_search));
    }

    @Override
    public void clearSearchContent() {
        mBinding.search.setText("");
    }

    @Override
    public void hideClearSearchContent() {
        mBinding.clear.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showClearSearchContent() {
        mBinding.clear.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showNoResult() {
        mBinding.indexListContent.setVisibility(android.view.View.GONE);
        mBinding.indexTip.setText(getString(R.string.tip_no_result));
        mBinding.indexTip.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showNoSearchHistory() {
        mBinding.indexListContent.setVisibility(android.view.View.GONE);
        mBinding.indexTip.setText(getString(R.string.tip_no_history));
        mBinding.indexTip.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void closeTip() {
        mBinding.indexTip.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showImportDataError() {
        mBinding.indexListContent.setVisibility(View.GONE);
        mBinding.search.setVisibility(View.GONE);
        mBinding.indexTip.setText(R.string.tip_import_data_error);
        mBinding.indexTip.setVisibility(View.VISIBLE);
    }


    @Override
    public void setPresenter(IndexContract.Presenter Presenter) {
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

    class ListAdapter extends BaseAdapter {

        private Context mContext;

        private ItemBirdsOrderBinding mBinding;

        private List<BirdListItem> mList;

        private Map<Integer,Bitmap> mBitmapMap;

        public ListAdapter(Context context,List<BirdListItem> list) {
            mContext = context;
            mList = list;
            mBitmapMap = new HashMap<>();
        }

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }else {
                return 0;
            }
        }

        @Override
        public BirdListItem getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.item_birds_order, null);
            }
            mBinding = DataBindingUtil.bind(convertView);
            final BirdListItem item = getItem(position);
            mBinding.setItem(item);

            //在此处添加监听器
            mBinding.orderListItem.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    mPresenter.loadBirdDetail(item.cnName.get());
                }
            });

            Bitmap avatar = mBitmapMap.get(position);
            SearchItemImage searchItemImage = new SearchItemImage(position, mBinding);
            if (avatar == null) {
                Handler imageInThreadPresenter = new ImageInThreadPresenter(searchItemImage);
                ImageUtil.getBitmapInThread(imageInThreadPresenter, GlobalConstant.HB_DATA_FILE_PATH + item.avatarPath.get(), 50, 50);
            } else {
                searchItemImage.showImage(avatar);
            }

            return convertView;
        }

        public void updateData(List<BirdListItem> data) {
            mList = data;
            notifyDataSetChanged();
        }

        class SearchItemImage implements ImageInThreadContract.View {

            private ItemBirdsOrderBinding mBinding;

            private int position;

            public SearchItemImage(int position,ItemBirdsOrderBinding binding) {
                mBinding = binding;
                this.position = position;
            }

            @Override
            public void showLoadingImage() {
                mBinding.orderListAvatarLoading.setVisibility(android.view.View.VISIBLE);
                mBinding.imgBirdAvatar.setVisibility(android.view.View.GONE);
            }

            @Override
            public void showImage(Bitmap bitmap) {
                mBinding.orderListAvatarLoading.setVisibility(android.view.View.GONE);
                mBinding.imgBirdAvatar.setVisibility(android.view.View.VISIBLE);
                mBinding.imgBirdAvatar.setImageBitmap(bitmap);
            }

            @Override
            public void showLoadingImageError() {
                mBinding.orderListAvatarLoading.setVisibility(android.view.View.GONE);
                mBinding.imgBirdAvatar.setVisibility(android.view.View.VISIBLE);
            }

            @Override
            public void saveImage(Bitmap bitmap) {
                mBitmapMap.put(position, bitmap);
            }
        }

    }

    class SearchWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().equals("")) {
                hideClearSearchContent();
                mPresenter.loadHistory();
            }else {
                mPresenter.search(s.toString());
                showClearSearchContent();
            }
        }
    }
}