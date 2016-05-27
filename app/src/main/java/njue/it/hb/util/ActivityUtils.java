package njue.it.hb.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public final class ActivityUtils {


    public static void replaceFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int fragmentId) {
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(fragmentId, fragment);
            transaction.commit();
        }
    }
}