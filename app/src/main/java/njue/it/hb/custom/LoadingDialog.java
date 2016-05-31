package njue.it.hb.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import njue.it.hb.R;

public class LoadingDialog extends Dialog {

    private Context mContext;

    private String title;

    public LoadingDialog(Context context,String title) {
        super(context);
        mContext = context;
        this.title = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        setCancelable(false);
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
        TextView textTitle= (TextView) findViewById(R.id.loading_title);
        textTitle.setText(this.title);
    }
}