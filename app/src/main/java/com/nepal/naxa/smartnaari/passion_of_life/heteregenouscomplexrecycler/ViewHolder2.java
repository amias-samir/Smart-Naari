package com.nepal.naxa.smartnaari.passion_of_life.heteregenouscomplexrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;


/**
 * Created by samir on 12/27/2017.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder {

    private ImageView ivExample;
    private TextView tvHead;

    public ViewHolder2(View v) {
        super(v);
        ivExample = (ImageView) v.findViewById(R.id.ivExample);
        tvHead = (TextView)v.findViewById(R.id.tv_title);
    }

    public ImageView getImageView() {
        return ivExample;
    }

    public void setImageView(ImageView ivExample) {
        this.ivExample = ivExample;
    }


    public TextView getTvHead() {
        return tvHead;
    }

    public void setTvHead(TextView tvHead) {
        this.tvHead = tvHead;
    }
}
