package njue.it.hb.contract;

import njue.it.hb.common.BasePresenter;
import njue.it.hb.common.BaseView;
import njue.it.hb.model.Bird;

public interface BirdDetailContract {

    interface presenter extends BasePresenter {

        /**
         * 加载鸟类信息
         */
        void loadBirdDetail();

        /**
         * 播放鸟语音
         */
        void playTwitter();

        /**
         * 暂停鸟语播放
         */
        void pauseTwitter();

        /**
         * 播放鸟语到指定位置
         */
        void playTwitterOnProgress(int progress);

        /**
         * 获取当前播放状态
         */
        boolean isTwitterPlaying();

        /**
         * 释放Twitter资源
         */
        void releaseTwitter();

        /**
         * 播放第二幅鸟图音频
         */
        void playTwitter2();

        /**
         * 第二幅鸟语图，暂停鸟语播放
         */
        void pauseTwitter2();

        /**
         * 第二幅鸟语图，播放鸟语到指定位置
         */
        void playTwitter2OnProgress(int progress);

        /**
         * 第二幅鸟语图，获取当前播放状态
         */
        boolean isTwitter2Playing();
    }

    interface view extends BaseView<presenter> {

        /**
         * 显示鸟类信息
         */
        void showBirdDetail(Bird bird);

        /**
         * 返回上级鸟类列表界面
         */
        void backToBirdList();

        /**
         * 显示加载界面
         */
        void showLoading();

        /**
         * 关闭加载界面
         */
        void closeLoading();

        /**
         * 显示播放按钮，可播放
         */
        void showTwitterPlayable();

        /**
         * 显示播放按钮，暂停
         */
        void showTwitterPause();

        /**
         * 显示播放进度
         */
        void showTwitterProgress(int progressMax, int progress);

        /**
         * 显示播放错误
         */
        void showTwitterPlayError();

        /**
         * 显示鸟语音频剩余播放时间，（-分:秒）的格式
         */
        void showTwitterPlayRemainingTime(String time);

        /**
         * 显示第二幅鸟语图
         */
        void showTwitter2(Bird bird);

        /**
         * 显示鸟语第二幅音频剩余播放时间，（-分:秒）的格式
         */
        void showTwitter2PlayRemainingTime(String time);

        /**
         * 第二幅鸟语图，显示播放按钮，可播放
         */
        void showTwitter2Playable();

        /**
         * 第二幅鸟语图，显示播放按钮，暂停
         */
        void showTwitter2Pause();

        /**
         * 第二幅鸟语图，显示播放进度
         */
        void showTwitter2Progress(int progressMax, int progress);

    }

}