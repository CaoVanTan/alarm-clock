package com.example.alarmclock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AlarmFragment();
            case 1:
                return new ClockFragment();
            case 2:
                return new StopTimeFragment();
            case 3:
                return new TimerFragment();
            default:
                return new AlarmFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Báo thức";
                break;
            case 1:
                title = "Đồng hồ";
                break;
            case 2:
                title = "Bấm giờ";
                break;
            case 3:
                title = "Hẹn giờ";
                break;
        }
        return title;
    }
}
