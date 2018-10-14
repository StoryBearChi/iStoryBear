package tw.com.dean.istorybear.ui.userhome;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.com.dean.istorybear.R;
import tw.com.dean.istorybear.StoryPlayerActivity;

public class storyHomeFragment extends Fragment {

    private UserHomeViewModel mViewModel;

    public static storyHomeFragment newInstance() {
        return new storyHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_userhome_story, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserHomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
