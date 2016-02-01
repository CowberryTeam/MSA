package ru.cowberryteam.msa.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.cowberryteam.msa.R;


public class AllMarksFragmentPager extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public AllMarksFragmentPager() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_marks_pager, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.tab_view_pager);
        setupViewPager();

        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        setupTab();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupTab(){
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(){
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new AllMarksFragment();
                case 1:
                    return new AllMarksFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getString(R.string.title_fragment_allmarks_first_half);
                case 1:
                    return getString(R.string.title_fragment_allmarks_second_half);
            }
            return null;
        }
    }
}