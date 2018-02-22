package com.nepal.naxa.smartnaari.tapitstopit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import java.util.List;

/**
 * Created by samir on 2/19/2018.
 */

public class TapItStopItRecyclerAdapter extends RecyclerView.Adapter<TapItStopItRecyclerAdapter.MyViewHolder> implements View.OnClickListener{

    private Context mContext;
    private List<ViewModel> albumList;

    private OnItemClickListener onItemClickListener;

    public TapItStopItRecyclerAdapter(List<ViewModel> albumList) {
        this.albumList = albumList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.text);
        }
    }


    public TapItStopItRecyclerAdapter(Context mContext, List<ViewModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tap_it_stop_it_cardview_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ViewModel album = albumList.get(position);

        holder.title.setText(album.getText());
        holder.image.setImageResource(album.getImage());

        holder.itemView.setTag(album);
        holder.itemView.setOnClickListener(this);
    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }


    @Override
    public void onClick(final View v) {


        onItemClickListener.onItemClick(v,(ViewModel) v.getTag());
        Log.e("SAMIR", "onClick: " );
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ViewModel viewModel);

    }
}