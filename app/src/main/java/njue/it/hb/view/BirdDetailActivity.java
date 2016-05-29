package njue.it.hb.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.List;

import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.BirdDetailContract;
import njue.it.hb.data.repository.DatabaseRepository;
import njue.it.hb.databinding.ActivityBirdDetailDataBinding;
import njue.it.hb.model.Bird;
import njue.it.hb.presenter.BirdDetailPresenter;
import njue.it.hb.util.ImageUtil;

/**
 * 这次的这个鸟类详情页的由于鸟语图有三种鸟有两份，并且自己没有找到其他的更好的办法，只好写了两份几乎一模一样的代码，重复率太高，
 * 自己也不太满意，下次争取解决这个问题。
 */
public class BirdDetailActivity extends AppCompatActivity implements BirdDetailContract.view {

    private static final String TAG = "BirdDetailActivity";

    private BirdDetailContract.presenter mPresenter;

    private ActivityBirdDetailDataBinding mBinding;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_detail_data);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bird_detail_data);
        int id =  getIntent().getIntExtra(GlobalConstant.KEY_INTENT_BIRD_ID,0);   //上一级传来的id
        mPresenter = new BirdDetailPresenter(this, id, new DatabaseRepository());
        mLoadingDialog = new LoadingDialog(this, getString(R.string.title_loading_data));

        mPresenter.loadBirdDetail();
        mBinding.twitterPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPresenter.isTwitterPlaying()) {
                    mPresenter.playTwitter();
                }else {
                    mPresenter.pauseTwitter();
                }
            }
        });

        mBinding.twitterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPresenter.playTwitterOnProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                showTwitterPlayable();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                showTwitterPause();
            }
        });

        mBinding.twitter2Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPresenter.isTwitter2Playing()) {
                    mPresenter.playTwitter2();
                }else {
                    mPresenter.pauseTwitter2();
                }
            }
        });

        mBinding.twitter2SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPresenter.playTwitter2OnProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                showTwitter2Playable();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                showTwitter2Pause();
            }
        });
    }

    @Override
    public void showTwitterPlayRemainingTime(String time) {
        mBinding.twitterPlayRemainingTime.setText(time);
    }

    @Override
    public void showTwitter2(Bird bird) {
        mBinding.twitter2.setVisibility(View.VISIBLE);
        mBinding.twitter2Image.setImageBitmap(ImageUtil.getBitmap(GlobalConstant.HB_DATA_FILE_PATH +bird.twitterImagePath.get(1),500,120));
    }

    @Override
    public void showTwitter2PlayRemainingTime(String time) {
        mBinding.twitter2PlayRemainingTime.setText(time);
    }

    @Override
    public void showTwitter2Playable() {
        mBinding.twitter2Play.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_48dp));
    }

    @Override
    public void showTwitter2Pause() {
        mBinding.twitter2Play.setBackground(getResources().getDrawable(R.drawable.ic_pause_black_48dp));
    }

    @Override
    public void showTwitter2Progress(int progressMax, int progress) {
        mBinding.twitter2SeekBar.setMax(progressMax);
        mBinding.twitter2SeekBar.setProgress(progress);
    }

    @Override
    public void showBirdDetail(Bird bird) {
        mBinding.setBird(bird);
        //由于标题栏需要显示鸟的名字，所以讲Toolbar的代码放在了这里
        Toolbar toolbar = mBinding.toolbar;
        toolbar.setTitle(bird.name.get());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //决定图标是否可以点击
        Gallery gallery = mBinding.gallery;
        GalleryAdapter galleryAdapter = new GalleryAdapter(getApplicationContext(), bird.imagePaths);
        gallery.setAdapter(galleryAdapter);
        gallery.setOnItemSelectedListener(galleryAdapter);

        String absoluteFileName = GlobalConstant.HB_DATA_FILE_PATH + bird.twitterImagePath.get(0);
        Log.i(TAG, "showBirdDetail: twitter file:" + absoluteFileName);
        mBinding.twitterImage.setImageBitmap(ImageUtil.getBitmap(absoluteFileName, 500, 120));

        if (bird.twitterImagePath.size() > 1) {
            showTwitter2(bird);
        }
    }

   @Override
    public void backToBirdList() {
       mPresenter.releaseTwitter();
       finish();
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        mLoadingDialog.cancel();
    }

    @Override
    public void showTwitterPlayable() {
        mBinding.twitterPlay.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_48dp));
    }

    @Override
    public void showTwitterPause() {
        mBinding.twitterPlay.setBackground(getResources().getDrawable(R.drawable.ic_pause_black_48dp));
    }

    @Override
    public void showTwitterProgress(int progressMax, int progress) {
        mBinding.twitterSeekBar.setMax(progressMax);
        mBinding.twitterSeekBar.setProgress(progress);
    }

    @Override
    public void showTwitterPlayError() {
        Toast.makeText(BirdDetailActivity.this, R.string.toast_data_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(BirdDetailContract.presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 添加toolbar左上角的点击功能
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        backToBirdList();
        return super.onOptionsItemSelected(item);
    }

    class GalleryAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {

        private List<String> images;

        private Context mContext;

        public GalleryAdapter(Context context, List<String> images) {
            this.images = images;
            mContext = context;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageBitmap(ImageUtil.getBitmap(GlobalConstant.HB_DATA_FILE_PATH + getItem(position).toString(), 200, 100));
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}