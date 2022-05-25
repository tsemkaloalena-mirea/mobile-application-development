//package com.example.travelcompanyapplication.ui;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.example.travelcompanyapplication.databinding.FragmentGuideBinding;
//
//
//public class GuideFragment extends Fragment {
//    private View view;
//    private FragmentGuideBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentGuideBinding.inflate(inflater, container, false);
//        view = binding.getRoot();
//
//        GuideDBHelper hotelDBHelper = new GuideDBHelper(getContext());
//        GuideViewBuilder hotelsViewBuilder = new GuideViewBuilder(hotelDBHelper);
//        view = hotelsViewBuilder.createView(inflater, container, null, null, false);
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}