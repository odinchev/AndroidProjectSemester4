package com.example.examples.androidprojectsemester4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

//import com.example.qzewe.androidcursorwheel.Data.ImageData;
//import com.example.qzewe.androidcursorwheel.R;

import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

/**
 * Created by Qzewe on 14.11.2017 г..
 */

public class WheelImageAdapter extends CursorWheelLayout.CycleWheelAdapter {

    private Context mContext;
    private List<ImageData> menuItems;
    private LayoutInflater inflater;
    private int gravity;

public WheelImageAdapter(Context mContext,List<ImageData>menuItems){
    this.mContext = mContext;
    this.menuItems = menuItems;
    inflater = LayoutInflater.from(mContext);
}


    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public View getView(View parent, int position) {
        ImageData data = getItem(position);
View root = inflater.inflate(R.layout.wheel_image_layout,null,false);
        ImageView imageView = (ImageView)root.findViewById(R.id.wheel_menu_item_iv);
        imageView.setImageResource(data.imageResource);

        return root;
    }

    @Override
    public ImageData getItem(int position) {
        return menuItems.get(position);
    }
}
