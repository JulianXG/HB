package njue.it.hb.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import njue.it.hb.R;

public class CommonBirdsListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_common_birds_list, container, false);

        return root;
    }

    public static CommonBirdsListFragment newInstance(){
        return new CommonBirdsListFragment();
    }


}