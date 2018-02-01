package com.nepal.naxa.smartnaari.data_glossary.muth_busters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nepal.naxa.smartnaari.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<String> mData;
    List<WordsWithDetailsModel> wordsWithDetailsList;

    public void add(String s,int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,s);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.section_text_child);
        }
    }

    public SimpleAdapter(Context context, String [] data) {
        mContext = context;
        if (data != null)
            mData = new ArrayList<String>(Arrays.asList(data));
        else mData = new ArrayList<String>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.section_recycler_child, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).trim());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,"Position ="+position,Toast.LENGTH_SHORT).show();
//                view.getContext().startActivity(new Intent(view.getContext(),asabani_cat.class));
                Intent intent = new Intent(view.getContext(), DataGlossaryWordDetailsActivity.class);
                intent.putExtra("wordsWithDetails", WordsWithDetailsActivity.wordsWithDetailsList.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}