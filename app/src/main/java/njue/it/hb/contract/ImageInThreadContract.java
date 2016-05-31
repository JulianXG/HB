package njue.it.hb.contract;

import android.graphics.Bitmap;
import android.os.Handler;

public interface ImageInThreadContract {

    /**
     * 此Handler是为了处理与view线程的通信
     */
    class Handler extends android.os.Handler {

    }

    /**
     * 这个类似与工具类，在主线程运行，
     */
    interface View {

        /**
         * 显示正在加载图片界面
         */
        void showLoadingImage();

        /**
         * 显示图片
         */
        void showImage(Bitmap bitmap);

        /**
         * 显示加载图片错误
         */
        void showLoadingImageError();

        /**
         * 保存图片
         */
        void saveImage(Bitmap bitmap);
    }

}