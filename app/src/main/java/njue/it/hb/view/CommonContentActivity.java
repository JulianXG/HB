package njue.it.hb.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import njue.it.hb.R;
import njue.it.hb.common.GlobalConstant;

/**
 * Created by liberty on 2016/5/27.
 */

public class CommonContentActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_content);
        Intent intent= getIntent();
        String introductionValue=intent.getStringExtra(GlobalConstant.KEY_INTENT_INTRODUCTION);
        String aboutValue = intent.getStringExtra(GlobalConstant.KEY_INTENT_ABOUT);
        TextView text=(TextView)findViewById(R.id.introduce_hb);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);

        if (text != null) {
            text.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        String content = null;
        if (introductionValue != null) {

            switch (introductionValue) {
                case IntroductionFragment.KEY_BASIC_FACT:
                    content = getString(R.string.hb_basic_fact);
                    toolbar.setTitle(R.string.title_hb_basic_fact);
                    break;
                case IntroductionFragment.KEY_WHAT_IS_BIRD:
                    content = getString(R.string.what_is_bird);
                    toolbar.setTitle(R.string.title_what_is_bird);
                    break;
                case IntroductionFragment.KEY_WATCH_BIRD:
                    content = getString(R.string.how_to_watch_bird);
                    toolbar.setTitle(R.string.title_how_to_watch_bird);
                    break;

            }
        }

        if (aboutValue != null) {
            switch (aboutValue) {
                case AboutFragment.DEVELOPER:
                    content = getString(R.string.developers);
                    toolbar.setTitle(R.string.title_hb_basic_fact);
                    break;
                case AboutFragment.TWITTER_PROCESS_METHOD:
                    content = getString(R.string.twitter_process_method);
                    toolbar.setTitle(R.string.title_what_is_bird);
                    break;
                case AboutFragment.THANK:
                    content = getString(R.string.thank);
                    toolbar.setTitle(R.string.title_thank);
                    break;

            }
        }

        text.setText(content);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
    }


    /**
     * 添加toolbar左上角的点击功能
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
