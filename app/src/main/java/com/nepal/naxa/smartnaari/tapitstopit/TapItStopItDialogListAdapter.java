package com.nepal.naxa.smartnaari.tapitstopit;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import java.util.List;

/**
 * Created by samir on 2/20/2018.
 */

public class TapItStopItDialogListAdapter extends RecyclerView.Adapter<TapItStopItDialogListAdapter.ContactViewHolder> {


    private List<TapItStopItPOJO> itemList;

    public TapItStopItDialogListAdapter(List<TapItStopItPOJO> cList) {
        this.itemList = cList;
    }


    @Override
    public void onBindViewHolder(TapItStopItDialogListAdapter.ContactViewHolder contactViewHolder, int i) {
        TapItStopItPOJO ci = itemList.get(i);
        contactViewHolder.tvName.setText(i+1+". "+ci.getName().trim());

        contactViewHolder.tvNumbers.setText(ci.getContact().trim());
        Linkify.addLinks(contactViewHolder.tvNumbers, Linkify.PHONE_NUMBERS);
        contactViewHolder.tvNumbers.setTextColor(Color.parseColor("#3157be"));

//        contactViewHolder.tvNumbers.setAutoLinkMask(Linkify.PHONE_NUMBERS);
//        contactViewHolder.tvNumbers.setText(ci.getContact().trim());
//        contactViewHolder.tvNumbers.setText("012345678");
//        Linkify.addLinks(contactViewHolder.tvNumbers, Linkify.PHONE_NUMBERS);






    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.tap_it_stop_it_item_list, viewGroup, false);

        return new ContactViewHolder(itemView);
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView servicesName, tvName, tvNumbers;

        public ContactViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tap_it_stop_it_dialog_name);
            tvNumbers = (TextView) v.findViewById(R.id.tap_it_stop_it_dialog_number);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
