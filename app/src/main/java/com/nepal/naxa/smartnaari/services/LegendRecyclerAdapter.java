package com.nepal.naxa.smartnaari.services;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 9/24/17
 * by nishon.tan@gmail.com
 */

public class LegendRecyclerAdapter extends RecyclerView.Adapter<LegendRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    private List<ViewModel> items;
    private LegendRecyclerAdapter.OnItemClickListener onItemClickListener;
    private Context context;

    public LegendRecyclerAdapter(ArrayList<ViewModel> items) {
        this.items = items;
    }

    public void setOnItemClickListener(LegendRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public LegendRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutRes = 0;
        layoutRes = R.layout.list_item_map_ledgend;
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new LegendRecyclerAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(LegendRecyclerAdapter.ViewHolder holder, int position) {
        ViewModel item = items.get(position);
        holder.markerTitle.setText(item.getText());
        context = holder.markerTitle.getContext();
        setLegendColor(holder, item);

    }

    private void setLegendColor(LegendRecyclerAdapter.ViewHolder holder, ViewModel viewModel) {

        int color = ContextCompat.getColor(context, viewModel.getImage());


        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        holder.markerTitle.getCompoundDrawables()[0].setColorFilter(colorFilter);

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (ViewModel) v.getTag());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;


        @BindView(R.id.list_item_tv_title)
        TextView markerTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ViewModel viewModel);

    }
}
