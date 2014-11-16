package psi14.udc.es.thewardrobe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CombinerPageAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments = null;

    public CombinerPageAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);

    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}