package njue.it.hb.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.common.escape.Escaper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ZipUtil {

    private static final String TAG = "ZipUtil";

    /**
     * 信息码，代表开始压缩
     */
    public static final int START = 1001;

    /**
     * 信息码，代表正在解压
     */
    public static final int EXTRACTING = 1002;

    /**
     * 信息码，代表完成
     */
    public static final int COMPLETED = 1003;

    /**
     * 信息码代表出错
     */
    public static final int ERROR = 1004;

    /**
     * 进度的key值
     */
    public static final String KEY_PROGRESS = "PROGRESS";

    /**
     * 出错key值
     */
    public static final String KEY_ERROR = "ERROR";

    public static void extractZipFileWithProgress(File sourceFile, final String outputPath, final Handler handler) throws IOException {
        final ZipFile zipFile = new ZipFile(sourceFile);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (handler == null) {
                    return;
                }
                Enumeration enumeration = zipFile.entries();
                Bundle bundle;
                Message message;
                int current=0;
                int count = zipFile.size();
                handler.sendEmptyMessage(START);
                while (enumeration.hasMoreElements()) {
                    Log.i(TAG, "run: current:" + current);
                    try {
                        ZipEntry entry = (ZipEntry) enumeration.nextElement();
                        if (entry.isDirectory()) {
                            String directory = outputPath + "/" + entry.getName();
                            File path = new File(directory);
                            if (!path.exists()) {
                                path.mkdirs();
                            }
                            current++;
                            continue;
                        }
                        byte[] buff = new byte[1024];
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(outputPath, entry.getName())));
                        InputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry));
                        int readLine;
                        while ((readLine = inputStream.read(buff, 0, 1024)) != -1) {
                            outputStream.write(buff,0,readLine);
                        }
                        Log.i(TAG, "extractZipFile: " + outputPath + "/" + entry.getName());
                        inputStream.close();
                        outputStream.close();

                        current++;
                        bundle = new Bundle();
                        bundle.putInt(KEY_PROGRESS, (current * 100) / count);
                        message = new Message();
                        message.what = EXTRACTING;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                        bundle = new Bundle();
                        bundle.putString(KEY_ERROR, e.getMessage());
                        message = new Message();
                        message.what = ERROR;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }
                handler.sendEmptyMessage(COMPLETED);
                Log.i(TAG, "run: 全部解压完毕");
            }
        }).start();

    }

}