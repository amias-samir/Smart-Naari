/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nepal.naxa.smartnaari.passion_of_life.binder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.bumptech.glide.Glide;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.passion_of_life.model.Article;

/**
 * Created on 10/9/17
 * by susan.invents@gmail.com
 */
public class NewsTextPhotoBinder extends ItemBinder<Article, NewsTextPhotoBinder.ViewHolder> {

//  public Context context;

    View rootView;
    Typeface face;

    public NewsTextPhotoBinder(ItemDecorator itemDecorator) {
        super(itemDecorator);
    }

    @Override
    public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_only_photo_text, null);
        face = Typeface.createFromAsset(rootView.getContext().getAssets(), "RockwellFett.ttf");

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void bind(ViewHolder holder, Article item) {

//        holder.tvTitle.setTypeface(face);
        holder.tvTitle.setText(item.getTitle());
        holder.tvTime.setText(item.getLastUpdated());
        holder.tvCategory.setText(item.getCategory());
        holder.ivCover.setBackgroundColor(item.getCategoryColor());
        holder.ivCover.setImageResource(item.getCoverImageId());

        Context context = holder.itemView.getContext();
        Glide.with(context).load(item.getImageUrl()).into(holder.ivCover);
    }

    @Override
    public int getSpanSize(int maxSpanCount) {
        return maxSpanCount;
    }

    @Override
    public boolean canBindData(Object item) {
        return item instanceof Article && ((Article) item).getCategory().equals("TEXT");
    }

    static class ViewHolder extends BaseViewHolder<Article> {

        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvCategory;
        private ImageView ivCover;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
        }
    }
}