package navigation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import MainThreeFragment.Analyze;
import MainThreeFragment.EnterFunction;
import MainThreeFragment.User;

public class MyViewPaper2BottomAdapter extends FragmentStateAdapter {
    boolean isSelect = false;
    public MyViewPaper2BottomAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new EnterFunction();
            case 1:
                return new Analyze();
            default:
                return new User();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
