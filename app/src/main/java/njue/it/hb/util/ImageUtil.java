package njue.it.hb.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public final class ImageUtil {

    private static final String TAG = "ImageUtil";

    /**
     * 信息码，解码开始
     */
    public static final int START = 100;

    /**
     * 信息码，正在解码中
     */
    public static final int DECODING = 101;

    /**
     * 信息码，解码完成
     */
    public static final int COMPLETE = 102;

    /**
     * 信息码，错误
     */
    public static final int ERROR = 103;

    /**
     * Bitmap的key值
     */
    public static final String KEY_BITMAP = "BITMAP";

    /**
     * 耗时的公共方法，大概超过200ms的任务会觉得卡顿，
     * 开在子线程，用Handler进行通信
     */
    public static void getBitmapInThread(final Handler handler, final String absoluteFileName, final int width, final int height) {
        handler.sendEmptyMessage(START);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.sendEmptyMessage(DECODING);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    byte[] data = getBytesByInputStream(new FileInputStream(absoluteFileName));
                    BitmapFactory.decodeByteArray(data, 0, data.length, options);
                    int imageWidth = options.outWidth;
                    int imageHeight = options.outHeight;
                    int scaleX = imageWidth / width;
                    int scaleY = imageHeight / height;
                    options.inJustDecodeBounds=false;
                    options.inSampleSize = Math.max(1, Math.max(scaleX, scaleY));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

                    Bundle bundle = new Bundle();
                    Message message = new Message();
                    bundle.putParcelable(KEY_BITMAP,bitmap);
                    message.what = COMPLETE;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(ERROR);
                }
            }
        }).start();
    }

    public static Bitmap getBitmap(String absoluteFileName, int width, int height) {
        Bitmap bitmap=null;
        try {
            long start = System.nanoTime();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            byte[] data = getBytesByInputStream(new FileInputStream(absoluteFileName));
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;
            int scaleX = imageWidth / width;
            int scaleY = imageHeight / height;
            options.inJustDecodeBounds=false;
            options.inSampleSize = Math.max(1, Math.max(scaleX, scaleY));
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            long finish = System.nanoTime();
            Log.i(TAG, "getBitmap: " + absoluteFileName + "\t" + (finish - start) / (1000 * 1000) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static byte[] getBytesByInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }
}