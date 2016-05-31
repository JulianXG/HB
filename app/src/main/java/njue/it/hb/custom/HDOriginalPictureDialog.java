package njue.it.hb.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.custom.HDOriginalViewPager;
import njue.it.hb.util.ImageUtil;
import uk.co.senab.photoview.PhotoViewAttacher;

public class HDOriginalPictureDialog extends Dialog {

    private List<String> mImageFiles;

    private Context mContext;

    private ImageView mCloseImageView;

    private HDOriginalViewPager mViewPager;

    private List<Bitmap> mBitmapList;

    private int mDeviceWidth;

    private int mDeviceHeight;

    private ImageAdapter mAdapter;

    public HDOriginalPictureDialog(Context context, List<String> imageFiles, int deviceWidth, int deviceHeight) {
        super(context, R.style.FullscreenDialog);
        mImageFiles = imageFiles;
        mContext = context;
        mDeviceWidth = deviceWidth;
        mDeviceHeight = deviceHeight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_original_pictures);
        mCloseImageView = (ImageView) findViewById(R.id.close);
        mViewPager= new HDOriginalViewPager(mContext);
        mBitmapList = new ArrayList<>();
        updateImages(mImageFiles);

        mAdapter= new ImageAdapter(mBitmapList, mContext);
        FrameLayout frameLayout= (FrameLayout) findViewById(R.id.hd_content);

        mViewPager.setAdapter(mAdapter);
        frameLayout.addView(mViewPager);

        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.destroyAllItems();
                cancel();
            }
        });
    }

    public void updateImages(List<String> list) {
        mImageFiles = list;
        for (String file : list) {
            Bitmap bitmap = ImageUtil.getBitmap(GlobalConstant.HB_DATA_FILE_PATH + file, mDeviceWidth, mDeviceHeight);
            mBitmapList.add(bitmap);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    class ImageAdapter extends PagerAdapter {

        private Context mContext;

        private List<Bitmap> mBitmapList;

        private List<ImageView> mImageViews;

        public ImageAdapter(List<Bitmap> bitmapList, Context context) {
            mBitmapList = bitmapList;
            mContext = context;
            mImageViews = new ArrayList<>();
            for (Bitmap bitmap : mBitmapList) {
                ImageView imageView = new ImageView(mContext);
                imageView.setImageBitmap(bitmap);
                PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
                mImageViews.add(imageView);
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        public void destroyAllItems() {
            if (mViewPager != null) {
                for (int i = 0; i < mImageViews.size(); i++) {
                    destroyItem(mViewPager, i, null);
                }
            }
        }
    }

}