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

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.nepal.naxa.smartnaari.R;
import com.nepal.naxa.smartnaari.passion_of_life.model.Article;

/**
 * Created on 10/9/17
 * by susan.invents@gmail.com
 */
public class NewsPhotoExpandableBinder extends ItemBinder<Article, NewsPhotoExpandableBinder.ViewHolder> {

//  public Context context;

  View rootView;
  Typeface face;
  public NewsPhotoExpandableBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_photo_expandable, null);
    face = Typeface.createFromAsset(rootView.getContext().getAssets(), "RockwellFett.ttf");

    // create ViewHolder
    ViewHolder viewHolder = new ViewHolder(rootView);

    return viewHolder;
  }

  @Override
  public void bind(ViewHolder holder, Article item) {

//        holder.tvTitle.setTypeface(face);
    holder.tvTitleBlack.setText(item.getTitle());
    holder.tvTitleWhite.setText(item.getTitle());
    holder.tvTime.setText(item.getLastUpdated());
    holder.tvCategory.setText(item.getCategory());
    holder.ivCover.setBackgroundColor(item.getCategoryColor());
    holder.ivCover.setImageResource(item.getCoverImageId());

    holder.ivIndicator.setImageResource(
            holder.isItemExpanded() ? R.drawable.ic_circle_up_arrow_white : R.drawable.ic_circle_down_white);
    holder.linearContent.setVisibility(holder.isItemExpanded() ? View.VISIBLE : View.GONE);
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Article && ((Article) item).getCategory().equals("EXPAND");
  }

  static class ViewHolder extends BaseViewHolder<Article> {

    private TextView tvTitleBlack, tvTitleWhite, tvContent;
    private TextView tvTime;
    private TextView tvCategory;
    private ImageView ivCover, ivIndicator;
    private LinearLayout linearContent;

    ViewHolder(View itemView) {
      super(itemView);
      linearContent = (LinearLayout) itemView.findViewById(R.id.linear_content);
      tvTitleBlack = (TextView) itemView.findViewById(R.id.tv_title_black);
      tvTitleWhite = (TextView) itemView.findViewById(R.id.tv_title_white);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
      ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
      ivIndicator = (ImageView) itemView.findViewById(R.id.iv_expandable_indicator);
      tvContent = (TextView) itemView.findViewById(R.id.tv_content);

      setItemClickListener(new OnItemClickListener<Article>() {
        @Override public void onItemClick(View view, Article item) {
          toggleItemExpansion();
          ivIndicator.setImageResource(
                  isItemExpanded() ? R.drawable.ic_circle_up_arrow_white : R.drawable.ic_circle_down_white);
        }
      });
    }
  }
}