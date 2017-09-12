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
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

public class YuwaQuestionAdapter extends RecyclerView.Adapter<YuwaQuestionAdapter.ViewHolder> implements View.OnClickListener {


    private List<YuwaQuery> items;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public YuwaQuestionAdapter(List<YuwaQuery> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        int layoutRes;

        switch (viewType) {
            case VIEW_TYPES.Footer:
                layoutRes = R.layout.list_item_yuwa_pusta_questions;
                break;
            default:
                layoutRes = R.layout.list_item_yuwa_pusta_questions;
                break;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        YuwaQuery item = items.get(position);


    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (items.get(position).isFooter())
            return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;

    }

    @Override
    public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (YuwaQuery) v.getTag());
    }

    protected  class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.list_item_yuwa_pusta_question_tv_question)
        TextView question;
        @BindView(R.id.list_item_yuwa_pusta_question_tv_answer)
        TextView answer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    protected  class FooterHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.list_item_yuwa_pusta_question_tv_question)
        TextView question;
        @BindView(R.id.list_item_yuwa_pusta_question_tv_answer)
        TextView answer;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    private class VIEW_TYPES {
        public static final int Normal = 2;
        public static final int Footer = 3;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, YuwaQuery yuwaQuery);

    }

}
