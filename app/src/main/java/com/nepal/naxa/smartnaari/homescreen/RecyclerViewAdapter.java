/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nepal.naxa.smartnaari.homescreen;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {


    private final static int RED_VIEW = 0;
    private final static int BORDER_VIEW = 1;

    private List<ViewModel> items;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(List<ViewModel> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutRes = 0;
        switch (viewType) {
            case RED_VIEW:
                layoutRes = R.layout.grid_list_item_red;
                break;
            case BORDER_VIEW:
                layoutRes = R.layout.grid_list_item_border;
                break;
        }


        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
            case 3:
            case 4:
                return RED_VIEW;
            default:
                return BORDER_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewModel item = items.get(position);
        holder.text.setText(item.getText());
//        holder.image.setImageResource(item.getImage());

        holder.itemView.setTag(item);


    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {

    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;


        @BindView(R.id.root_layout_item_recycler)
        FrameLayout rootLayoutItemRecycler;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ViewModel viewModel);

    }
}
