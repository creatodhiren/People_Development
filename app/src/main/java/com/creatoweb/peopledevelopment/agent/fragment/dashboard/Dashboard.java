package com.creatoweb.peopledevelopment.agent.fragment.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Dashboard extends Fragment {

    public static final String TAG = "Dashboard";
    private Context context;
    private ObservableInt status = new ObservableInt(0);
    FragmentDashboardBinding binding;

    private List<String> sliderList = new ArrayList<>();
    private List<String> item_name = new ArrayList<>();
    private List<String> item_image = new ArrayList<>();

    private int vp_current_item;
    private int vp_count;

    public Dashboard() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(context, 3));
        binding.setStatus(status);

        sliderList.clear();
        item_image.clear();
        item_name.clear();

        sliderList.add("" + R.drawable.slider);
        sliderList.add("" + R.drawable.slider1);
        sliderList.add("" + R.drawable.slider3);
        sliderList.add("" + R.drawable.slider4);
        setSliderAdapter();
        count();


        item_image.add("" + R.drawable.image1);
        item_image.add("" + R.drawable.image2);
        item_image.add("" + R.drawable.image3);
        item_image.add("" + R.drawable.image4);
        item_image.add("" + R.drawable.image5);
        item_image.add("" + R.drawable.image6);
        item_image.add("" + R.drawable.image7);
        item_image.add("" + R.drawable.image8);
        item_image.add("" + R.drawable.image9);
        item_image.add("" + R.drawable.image10);
        item_image.add("" + R.drawable.image11);
        item_image.add("" + R.drawable.image12);

        item_name.add("Systematic Investment Plan");
        item_name.add("Monthly Income Scheme");
        item_name.add("Saving Account");
        item_name.add("FD Account");
        item_name.add("DDS Scheme");
        item_name.add("Rd Account");
        item_name.add("Current Account");
        item_name.add("Personal Loan");
        item_name.add("Digital Finance");
        item_name.add("Bussiness Loan");
        item_name.add("Gold Loan");
        item_name.add("Home Loan");

        setCategoryAdapter();

        return binding.getRoot();
    }

    private void setSliderAdapter() {
        DashboardImageAdapter homeImageAdapter = new DashboardImageAdapter(context, sliderList);
        binding.viewpager.setAdapter(homeImageAdapter);
        vp_current_item = binding.viewpager.getCurrentItem();
        vp_count = homeImageAdapter.getCount();

        homeImageAdapter.setOnItemClickListener(new DashboardImageAdapter.ClickListener() {
            @Override
            public void onImageClick(int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("image", (Serializable) sliderHomeAPI);
//                bundle.putSerializable("position", position);
//                androidx.navigation.Navigation.findNavController(binding.getRoot()).navigate(R.id.imageShowSliderHomeFragment, bundle);
                Toast.makeText(context, "Click to image", Toast.LENGTH_SHORT).show();
            }
        });

        //set page indicator according to viewpager
        binding.pageindicator.setViewPager(binding.viewpager);
    }

    private void count() {
        //auto image slide
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (vp_current_item == vp_count) {
                    vp_current_item = 0;
                }
                binding.viewpager.setCurrentItem(vp_current_item++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2000, 9000);
    }

    private void setCategoryAdapter() {
        DashboardAdapter homeAdapter = new DashboardAdapter(item_name, item_image, context);
        binding.recyclerview.setAdapter(homeAdapter);

        homeAdapter.setClickListener((position, view) -> {

            if (position == 0) {
                Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
            } else if (position == 1) {
                Navigation.findNavController(view).navigate(R.id.MISFragment);
            } else if (position == 2) {
                Navigation.findNavController(view).navigate(R.id.savingFragment);
            } else if (position == 3) {
                Navigation.findNavController(view).navigate(R.id.FDFragment);
            } else if (position == 4) {
                Navigation.findNavController(view).navigate(R.id.DDSFragment);
            } else if (position == 5) {
                Navigation.findNavController(view).navigate(R.id.RDFragment);
            } else if (position == 6) {
                Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
            } else if (position == 7) {
                Navigation.findNavController(view).navigate(R.id.loanFragment);
            } else if (position == 8) {
                Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
            } else if (position == 9) {
                Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
            } else if (position == 10) {
                Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
            } else if (position == 11) {
                Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
            }

//            if (!url.equalsIgnoreCase("")) {
//                Intent intent = new Intent(context, ProductWebviewActivity.class);
//                intent.putExtra("url", url);
//                startActivity(intent);
//            }

//            Navigation navigation = new Navigation();
//            navigation.setCity();
//            Bundle bundle = new Bundle();
//            bundle.putString(CATEGORY_ID, parentCategoryAPI.get(position).getCatId());
//            catName = parentCategoryAPI.get(position).getCatName();
//            findNavController(view).navigate(R.id.action_homeFragment_to_sellerTabFragment, bundle);
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
