package njue.it.hb.presenter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;

import njue.it.hb.contract.ImageInThreadContract;
import njue.it.hb.util.ImageUtil;

public class ImageInThreadPresenter extends ImageInThreadContract.Handler {

    private ImageInThreadContract.View mView;

    public ImageInThreadPresenter(ImageInThreadContract.View View) {
        mView = View;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case ImageUtil.START:
                mView.showLoadingImage();
                break;
            case ImageUtil.DECODING:
                mView.showLoadingImage();
                break;
            case ImageUtil.COMPLETE:
                Bundle bundle = msg.getData();
                Bitmap bitmap = (Bitmap) bundle.get(ImageUtil.KEY_BITMAP);
                mView.showImage(bitmap);
                mView.saveImage(bitmap);
                break;
            case ImageUtil.ERROR:
                mView.showLoadingImageError();
                break;
        }
    }

}