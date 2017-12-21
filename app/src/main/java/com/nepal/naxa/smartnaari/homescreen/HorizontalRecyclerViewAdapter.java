package com.nepal.naxa.smartnaari.homescreen;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.utils.ui.BeautifulMainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 9/4/17
 * by nishon.tan@gmail.com
 */

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {


    private List<ViewModel> items;
    private HorizontalRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public HorizontalRecyclerViewAdapter(List<ViewModel> items) {
        this.items = items;
    }

    public void setOnItemClickListener(HorizontalRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public HorizontalRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item, parent, false);
        v.setOnClickListener(this);
        return new HorizontalRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HorizontalRecyclerViewAdapter.ViewHolder holder, int position) {
        ViewModel item = items.get(position);
        holder.text.setText(item.getText());
        holder.itemView.setTag(item);


        Context context = holder.itemView.getContext();
        int color = ContextCompat.getColor(context, item.getImage());
        holder.rootLayoutItemRecycler.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
            onItemClickListener.onHorizontalItemClick(v, (ViewModel) v.getTag());




    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.root_layout_home_hori_item_recycler)
        RelativeLayout rootLayoutItemRecycler;

        @BindView(R.id.home_horizontal_item_name)
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {

        void onHorizontalItemClick(View view, ViewModel viewModel);

    }


}
