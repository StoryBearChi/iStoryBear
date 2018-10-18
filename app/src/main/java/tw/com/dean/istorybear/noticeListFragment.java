package tw.com.dean.istorybear;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.com.dean.istorybear.ui.userhome.UserHomeViewModel;

public class noticeListFragment extends Fragment {


    public static noticeListFragment newInstance() {
        return new noticeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_me_notice_list, container, false);
    }

}
