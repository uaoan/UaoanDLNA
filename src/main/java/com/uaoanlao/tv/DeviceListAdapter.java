package com.uaoanlao.tv;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eliyar.bfdlna.SSDP.SSDPDevice;

import java.util.ArrayList;

/**
 * Created by brikerman on 2017/5/13.
 */

public class DeviceListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SSDPDevice> mDataSource;

    public DeviceListAdapter(Context context, ArrayList<SSDPDevice> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.v("Adapter", "GetView" + "i:" + String.valueOf(i));

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.devicelistcell, viewGroup, false);

            holder = new ViewHolder();
            holder.titleTextView      = (TextView) view.findViewById(R.id.recipe_list_title);
            holder.subtitleTextView   = (TextView) view.findViewById(R.id.recipe_list_subtitle);
            holder.detailTextView     = (TextView) view.findViewById(R.id.recipe_list_detail);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        TextView titleTextView = holder.titleTextView;
        TextView subtitleTextView = holder.subtitleTextView;
        TextView detailTextView = holder.detailTextView;


        SSDPDevice SSDPDevice = (SSDPDevice) getItem(i);

        titleTextView.setText(SSDPDevice.friendlyName);
        subtitleTextView.setText(SSDPDevice.uuid);
//        detailTextView.setText(receipe.label);


//        Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-Bold.ttf");
//        titleTextView.setTypeface(titleTypeFace);
//
//        Typeface subtitleTypeFace =
//                Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-SemiBoldItalic.ttf");
//        subtitleTextView.setTypeface(subtitleTypeFace);
//
//        Typeface detailTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Bold.otf");
//        detailTextView.setTypeface(detailTypeFace);
//        detailTextView.setTextColor(ContextCompat.getColor(mContext, LABEL_COLORS.get(receipe.label)));

        return view;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView detailTextView;
        public ImageView thumbnailImageView;
    }
}
