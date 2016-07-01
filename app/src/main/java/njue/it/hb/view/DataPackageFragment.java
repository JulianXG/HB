package njue.it.hb.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import njue.it.hb.R;
import njue.it.hb.contract.DataPackageContract;
import njue.it.hb.custom.LoadingDialog;
import njue.it.hb.data.repository.DataPackageRepository;
import njue.it.hb.databinding.FragmentDataPackageBinding;
import njue.it.hb.presenter.DataPackagePresenter;

public class DataPackageFragment extends Fragment implements DataPackageContract.View {

    private DrawerLayout mDrawerLayout;

    private FragmentDataPackageBinding mBinding;

    private DataPackageContract.Presenter mPresenter;

    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_package, container, false);
        mBinding = DataBindingUtil.bind(root);
        setHasOptionsMenu(true);
        Toolbar toolbar= (Toolbar) root.findViewById(R.id.toolbar);

        toolbar.setTitle("");       //暂时用这个方式代替实现，没找到更好的办法
        TextView title = (TextView) root.findViewById(R.id.title);
        title.setText(R.string.data_package_title);

        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);//修改图标
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);//决定图标是否可以点击
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标

        mBinding.goToDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadDownloadURL();
            }
        });

        mBinding.selectDataPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selectDataPackage();
            }
        });

        mPresenter = new DataPackagePresenter(new DataPackageRepository(getContext()), this);

        mPresenter.loadCurrentStatus();

        return root;
    }

    /**
     * 添加toolbar左上角的点击功能
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static DataPackageFragment newInstance() {
        return new DataPackageFragment();
    }

    @Override
    public void showInstallProgress(int progress) {
        if (mLoadingDialog != null) {
            mLoadingDialog.setTitle(getResources().getString(R.string.title_extracting_loading) + progress + "%");
        }
    }

    @Override
    public void showInstallSuccess() {
        Toast.makeText(getContext(), R.string.toast_install_success, Toast.LENGTH_SHORT).show();
        mBinding.setStatus(getString(R.string.toast_install_success));
    }

    @Override
    public void showInstallError() {
        Toast.makeText(getContext(), R.string.toast_extract_fail, Toast.LENGTH_SHORT).show();
        closeInstall();
    }

    @Override
    public void closeInstall() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }

    @Override
    public void showSelectDataPackage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.prompt_select_data_package)), DataPackageContract.DATA_PACKAGE_CODE);
    }

    @Override
    public void showInstallAlready() {
        mBinding.setStatus(getString(R.string.status_install_already));
    }

    @Override
    public void showNotInstall() {
        mBinding.setStatus(getString(R.string.status_not_install));
    }

    @Override
    public void showGoToDownload(String downloadURL) {
        Uri uri = Uri.parse(downloadURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void showSelectError() {
        Toast.makeText(getContext(), R.string.toast_select_data_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInstallStart() {
        mLoadingDialog = new LoadingDialog(getContext(), getString(R.string.title_extracting_loading));
        mLoadingDialog.show();
    }

    @Override
    public void setPresenter(DataPackageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.handleSelectResult(requestCode, resultCode, data);
    }
}