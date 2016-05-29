package njue.it.hb.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import njue.it.hb.R;
import njue.it.hb.contract.MainActivityContract;
import njue.it.hb.data.repository.DataFileRepository;
import njue.it.hb.databinding.ActivityMainBinding;
import njue.it.hb.presenter.MainActivityPresenter;
import njue.it.hb.util.ActivityUtils;


public class MainActivity extends AppCompatActivity implements MainActivityContract.view {

    private MainActivityContract.presenter mPresenter;

    private ActivityMainBinding mBinding;

    private LoadingDialog mLoadingDialog;

    private static final int DATA_FILE_SELECT_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        mPresenter = new MainActivityPresenter(new DataFileRepository(this), this);
        setNavigation();

        mPresenter.isFirstAndRun();
    }

    private void setNavigation(){
        NavigationView navigationView= mBinding.navView;
        if (navigationView != null) {

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.introduction_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), IntroductionFragment.newInstance(),R.id.content);
                            break;
                        case R.id.common_birds_list_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), BirdsListFragment.newInstance(),R.id.content);
                            break;
                        case R.id.index_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), IndexFragment.newInstance(),R.id.content);
                            break;
                        case R.id.about_nav_menu_item:
                            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), AboutFragment.newInstance(),R.id.content);
                            break;
                        case R.id.update_data_menu_item:
                            showSelectDataFile();
                            break;
                    }
                    item.setCheckable(true);
                    mBinding.drawerLayout.closeDrawers();
                    return true;
                }
            });

        }
    }


    @Override
    public void showExtracting() {
        mLoadingDialog = new LoadingDialog(this, getString(R.string.title_extracting_loading));
        mLoadingDialog.show();
    }

    @Override
    public void closeExtracting() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }

    @Override
    public void showExtractDataSuccess() {
        Toast.makeText(MainActivity.this, R.string.toast_extract_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExtractDataFail() {
        Toast.makeText(MainActivity.this, R.string.toast_extract_fail, Toast.LENGTH_SHORT).show();
    }

    /**
     * 默认打开CommonBirdsListFragment
     */
    @Override
    public void showDefaultSection() {
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), BirdsListFragment.newInstance(),R.id.content);

//        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), IndexFragment.newInstance(),R.id.content);     //测试index
    }

    @Override
    public void showSelectDataFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "请选择正确的数据文件压缩包(data.zip)"), DATA_FILE_SELECT_CODE);
    }

    @Override
    public void showExtractProgress(int progress) {
        if (mLoadingDialog != null) {
            mLoadingDialog.setTitle(getResources().getString(R.string.title_extracting_loading) + progress + "%");
        }
    }

    @Override
    public void setPresenter(MainActivityContract.presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DATA_FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri.getScheme().equalsIgnoreCase("file")) {
                        mPresenter.extractDataFile(new File(uri.getPath()));
                    }

                }

        }
    }
}
