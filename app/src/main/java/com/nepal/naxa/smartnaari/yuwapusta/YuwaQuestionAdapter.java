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
import com.nepal.naxa.smartnaari.data.local.model.YuwaQuestion;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YuwaQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {




    private List<YuwaQuestion> items;
    private OnItemClickListener onItemClickListener;
    private Context context;



    public YuwaQuestionAdapter(List<YuwaQuestion> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static void updateItems() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View v;

        switch (viewType) {


            case VIEW_TYPES.Footer:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_reycler_view, parent, false);
                v.setOnClickListener(this);
                return new FooterHolder(v);

            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_yuwa_pusta_questions, parent, false);
                v.setOnClickListener(this);
                return new ViewHolder(v);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        YuwaQuestion item = items.get(position);

        if (holder instanceof ViewHolder) {
                    ((ViewHolder) holder).question.setHtml(item.getQuestion());
                  ((ViewHolder) holder).answer.setHtml(item.getAnswer());
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (false)
            return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;

    }

    @Override
    public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (YuwaQuestion) v.getTag());
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


//        @BindView(R.id.list_item_yuwa_pusta_question_tv_question)
//        TextView question;
//        @BindView(R.id.list_item_yuwa_pusta_question_tv_answer)
//        TextView answer;
//
        @BindView(R.id.list_item_yuwa_pusta_question_tv_question)
        HtmlTextView question;
        @BindView(R.id.list_item_yuwa_pusta_question_tv_answer)
        HtmlTextView answer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    protected class FooterHolder extends RecyclerView.ViewHolder {

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

        void onItemClick(View view, YuwaQuestion yuwaQuestion);

    }

}
