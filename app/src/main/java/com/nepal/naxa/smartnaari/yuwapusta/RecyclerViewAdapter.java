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

package com.nepal.naxa.smartnaari.yuwapusta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.data.network.OwlData;
import com.nepal.naxa.smartnaari.homescreen.ViewModel;

import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {


    private List<OwlData> items;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public RecyclerViewAdapter(List<OwlData> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        int layoutRes = R.layout.list_item_yuwa_pust_owls;
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OwlData item = items.get(position);


//        Random rand = new Random();

//        int randomNumber = rand.nextInt(50) + 1;
//        String photoUrl = "http://lorempixel.com/500/500/people/"+randomNumber;
        String photoUrl = item.getOwlPhotoPath();


        Glide.with(context)
                .load(photoUrl).
                apply(RequestOptions.
                        circleCropTransform()).
                into(holder.listItemYuwaPustaIv1);
        holder.itemView.setTag(item);

//        Glide.with(context).
//                load(photoUrl).
//                apply(RequestOptions.
//                        circleCropTransform()).
//                into(holder.listItemYuwaPustaIv2);
//        holder.itemView.setTag(item);
//
//        Glide.with(context).
//                load(photoUrl).
//                apply(RequestOptions.
//                        circleCropTransform()).
//                into(holder.listItemYuwaPustaIv3);
//        holder.itemView.setTag(item);

//        }


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (OwlData) v.getTag());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.list_item_yuwa_pusta_iv1)
        ImageView listItemYuwaPustaIv1;
//        @BindView(R.id.list_item_yuwa_pusta_iv2)
//        ImageView listItemYuwaPustaIv2;
//        @BindView(R.id.list_item_yuwa_pusta_iv3)
//        ImageView listItemYuwaPustaIv3;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, OwlData owl);

    }
}
