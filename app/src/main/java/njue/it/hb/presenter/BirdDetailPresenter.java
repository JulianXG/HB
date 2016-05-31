package njue.it.hb.presenter;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import njue.it.hb.common.GlobalConstant;
import njue.it.hb.contract.BirdDetailContract;
import njue.it.hb.data.source.DatabaseDataSource;
import njue.it.hb.model.Bird;

public class BirdDetailPresenter implements BirdDetailContract.Presenter {

    private static final String TAG = "BirdDetailPresenter";

    private BirdDetailContract.View mView;

    private DatabaseDataSource mDataSource;

    private Bird mBird;

    private int id;

    private MediaPlayer mPlayer;

    private MediaPlayer mPlayer2;

    public BirdDetailPresenter(BirdDetailContract.View View, int id, DatabaseDataSource dataSource) {
        mDataSource = dataSource;
        mView = View;
        this.id = id;
        mView.setPresenter(this);
    }

    @Override
    public void loadBirdDetail() {
        //这句话得写在这里，在PreExecute中似乎不奏效
        mView.showLoading();
        new LoadBirdByIdTask().execute(id);
    }

    @Override
    public void pauseTwitter() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            mView.showTwitterPlayable();
        }
    }

    @Override
    public void playTwitterOnProgress(int progress) {
        if (mPlayer != null) {
            mPlayer.seekTo(progress);
            mPlayer.start();
        }
    }

    @Override
    public void playTwitter() {
        new PlayTwitterTask().execute(GlobalConstant.HB_DATA_FILE_PATH + mBird.twitterPath.get(0));
    }

    @Override
    public boolean isTwitterPlaying() {
        return mPlayer != null && mPlayer.isPlaying();
    }

    @Override
    public void releaseTwitter() {
        if (mPlayer != null) {
            mPlayer.release();
        }
        if (mPlayer2 != null) {
            mPlayer2.release();
        }
    }

    @Override
    public void playTwitter2() {
        new PlayTwitter2Task().execute(GlobalConstant.HB_DATA_FILE_PATH + mBird.twitterPath.get(1));
    }

    @Override
    public void pauseTwitter2() {
        if (mPlayer2 != null && mPlayer2.isPlaying()) {
            mPlayer2.pause();
            mView.showTwitter2Playable();
        }
    }

    @Override
    public void playTwitter2OnProgress(int progress) {
        if (mPlayer2 != null) {
            mPlayer2.seekTo(progress);
            mPlayer2.start();
        }
    }

    @Override
    public boolean isTwitter2Playing() {
        return mPlayer2 != null && mPlayer2.isPlaying();
    }

    @Override
    public void loadBirdHDOriginalImages() {
        mView.showHDOriginalPicture(mBird.imagePaths);
    }

    @Override
    public void loadTwitterHDOriginalImage() {
        List<String> list = new ArrayList<>();
        list.add(mBird.twitterImagePath.get(0));
        mView.showHDOriginalPicture(list);
    }

    @Override
    public void loadTwitter2HDOriginalImage() {
        List<String> list = new ArrayList<>();
        list.add(mBird.twitterImagePath.get(1));
        mView.showHDOriginalPicture(list);
    }

    class LoadBirdByIdTask extends AsyncTask<Integer,Void,Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            mBird = mDataSource.getBirdById(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mView.showBirdDetail(mBird);
            mView.closeLoading();
        }
    }

    class PlayTwitterTask extends AsyncTask<String, Integer, Boolean> implements MediaPlayer.OnCompletionListener {

        //这种以时间为单位的更新UI的动作最好用Handler实现
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.showTwitterPause();
            final Handler handler = new Handler();
            Runnable updateProgress = new Runnable() {
                @Override
                public void run() {
                        try {
                            if (mPlayer != null) {
                                mView.showTwitterProgress(mPlayer.getDuration(), mPlayer.getCurrentPosition());
                                int totalTime = mPlayer.getDuration();
                                int currentTime = mPlayer.getCurrentPosition();
                                int remainingTime = (totalTime - currentTime) / 1000;
                                int seconds = remainingTime % 60;
                                int minutes = remainingTime / 60;
                                mView.showTwitterPlayRemainingTime(String.format(Locale.ENGLISH, "-%d:%02d", minutes, seconds));
                            }
                            handler.postDelayed(this, 100);     //跟新周期100ms
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            Log.i(TAG, "run: twitter 出错");
                            releaseTwitter();
                            handler.removeCallbacks(this);      //这里做简单处理了
                        }

                }
            };
            handler.post(updateProgress);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (mPlayer == null) {
                    mPlayer = new MediaPlayer();
                    mPlayer.setDataSource(params[0]);
                    mPlayer.setOnCompletionListener(this);
                    mPlayer.prepare();
                }
                mPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (!aBoolean) {
                mView.showTwitterPlayError();
                mView.showTwitterPlayable();
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            mView.showTwitterPlayable();
        }
    }

    class PlayTwitter2Task extends AsyncTask<String, Integer, Boolean> implements MediaPlayer.OnCompletionListener {

        //这种以时间为单位的更新UI的动作最好用Handler实现
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.showTwitter2Pause();
            final Handler handler = new Handler();
            Runnable updateProgress = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mPlayer2 != null) {
                            mView.showTwitter2Progress(mPlayer2.getDuration(),mPlayer2.getCurrentPosition());
                            int totalTime = mPlayer2.getDuration();
                            int currentTime = mPlayer2.getCurrentPosition();
                            int remainingTime = (totalTime - currentTime) / 1000;
                            int seconds = remainingTime % 60;
                            int minutes = remainingTime / 60;
                            mView.showTwitter2PlayRemainingTime(String.format(Locale.ENGLISH,"-%d:%02d",minutes,seconds));
                        }
                        handler.postDelayed(this, 100);     //跟新周期100ms
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "run: twitter2 出错");
                        releaseTwitter();
                        handler.removeCallbacks(this);
                    }

                }
            };
            handler.post(updateProgress);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (mPlayer2 == null) {
                    Log.i(TAG, "doInBackground: twitter2 path:" + params[0]);
                    mPlayer2 = new MediaPlayer();
                    mPlayer2.setDataSource(params[0]);
                    mPlayer2.setOnCompletionListener(this);
                    mPlayer2.prepare();
                }
                mPlayer2.start();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (!aBoolean) {
                mView.showTwitterPlayError();
                mView.showTwitter2Playable();
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            mView.showTwitter2Playable();
        }
    }

}