//package com.example.travelcompanyapplication;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TableLayout;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.ListFragment;
//
//import com.example.travelcompanyapplication.databinding.FragmentGuideBinding;
//import com.example.travelcompanyapplication.db_controller.db_helpers.GuideDBHelper;
//
//import java.util.ArrayList;
//
//
//
//
//
//public class GuideFragment extends ListFragment {
//    OnCitySelectedListener mCallback;
//    private GuideDBHelper guideDBHelper;
//    private View view;
//    private FragmentGuideBinding binding;
//
//    public interface OnCitySelectedListener {
//        void onCitySelected(long id);
//    }
//
//    public GuideFragment(GuideDBHelper guideDBHelper) {
//        this.guideDBHelper = guideDBHelper;
//    }
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentGuideBinding.inflate(inflater, container, false);
//        view = binding.getRoot();
//
////        GuideDBHelper guideDBHelper = new GuideDBHelper(getContext());
////        GuideViewBuilder guideViewBuilder = new GuideViewBuilder(guideDBHelper);
////        view = guideViewBuilder.createView(inflater, container, null, null, false);
//
//        if (findViewById(R.id.fragment_container) != null) {
//            if (savedInstanceState != null) {
//                return;
//            }
//            HeadlinesFragment firstFragment = new HeadlinesFragment();
//            firstFragment.setArguments(getIntent().getExtras());
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_container, firstFragment).commit();
//        }
//
//        return view;
//        }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Activity activity = getActivity();
//
//        try {
//            mCallback = (OnCitySelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() +
//                    " must implement OnCitySelectedListener");
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ArrayList<String> cityNames = new ArrayList<>();
//        ArrayList<ArrayList<String>> data = guideDBHelper.getRecords(null, null);
//        for (ArrayList<String> row : data) {
//            cityNames.add(row.get(0));
//        }
//
//        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityNames));
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (getFragmentManager().findFragmentById(R.id.city_fragment) != null) {
//            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        }
//    }
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        int a = 0;
////        mCallback.onCitySelected(position);
////        getListView().setItemChecked(position, true);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}
