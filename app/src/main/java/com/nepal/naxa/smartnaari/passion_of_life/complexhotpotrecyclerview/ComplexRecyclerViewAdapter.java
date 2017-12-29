package com.nepal.naxa.smartnaari.passion_of_life.complexhotpotrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nepal.naxa.smartnaari.R;

import java.util.List;


/**
 * Created by samir on 12/27/2017.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;

    private final int USER = 0, IMAGE = 1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapter(List<Object> items) {
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        //More to come
        if (items.get(position) instanceof HotPotBlogRecipe) {
            return USER;
        } else if (items.get(position) instanceof HotPotImage) {
            return IMAGE;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        RecyclerView.ViewHolder viewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case USER:
                View v1 = inflater.inflate(R.layout.layout_hotpot_viewholder1, viewGroup, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case IMAGE:
                View v2 = inflater.inflate(R.layout.layout_hotpot_viewholder2, viewGroup, false);
                viewHolder = new ViewHolder2(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //More to come

        switch (viewHolder.getItemViewType()) {
            case USER:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case IMAGE:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                configureViewHolder2(vh2, position);
                break;
        }
    }


    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        HotPotBlogRecipe hotPotBlogRecipe = (HotPotBlogRecipe) items.get(position);
        if (hotPotBlogRecipe != null) {
            Glide.with(vh1.itemView.getContext())
                    .load(hotPotBlogRecipe.photo)
//                .override(250, 200)
                    .into(vh1.getImage());
            vh1.getHead().setText(hotPotBlogRecipe.head);
            vh1.getAuthor().setText(hotPotBlogRecipe.author);
            vh1.getExpandableTitle().setText(hotPotBlogRecipe.head);
            vh1.getBody().setText(hotPotBlogRecipe.body);
        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        HotPotImage hotPotImage = (HotPotImage) items.get(position);
//        vh2.getImageView().setImageResource(R.mipmap.ic_launcher);

        Glide.with(vh2.itemView.getContext())
                .load(hotPotImage.imagePath)
//                .override(250, 200)
                .into(vh2.getImageView());
    }
}
