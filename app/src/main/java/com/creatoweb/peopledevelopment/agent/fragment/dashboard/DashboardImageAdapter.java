package com.creatoweb.peopledevelopment.agent.fragment.dashboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class DashboardImageAdapter extends PagerAdapter {
    private Context context;
    private ClickListener clickListener;
    private List<String> list;

    public DashboardImageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(Integer.parseInt(list.get(position)));
//        Picasso.with(context).load(ApiClient.IMAGE_URL_SLIDER + list.get(position).getImage()).into(imageView);
        ((ViewPager) container).addView(imageView, 0);

        imageView.setOnClickListener(v -> {
            clickListener.onImageClick(position);
        });

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onImageClick(int position);
    }
}
