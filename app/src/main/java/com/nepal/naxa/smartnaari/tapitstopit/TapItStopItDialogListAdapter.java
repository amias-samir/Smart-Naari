package com.nepal.naxa.smartnaari.tapitstopit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
    public void onBindViewHolder(final TapItStopItDialogListAdapter.ContactViewHolder contactViewHolder, int i) {
        TapItStopItPOJO ci = itemList.get(i);
        contactViewHolder.tvName.setText(i+1+". "+ci.getName().trim());

        SpannableString contact1 = new SpannableString(ci.getContact().trim());
        contact1.setSpan(new UnderlineSpan(), 0, contact1.length(), 0);
        contactViewHolder.tvNumbers.setText(contact1);
        contactViewHolder.tvNumbers.setTextColor(Color.parseColor("#3157be"));
//        Linkify.addLinks(contactViewHolder.tvNumbers, Linkify.PHONE_NUMBERS);
        contactViewHolder.tvNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                // Send phone number to intent as data
                intent.setData(Uri.parse("tel:" + contactViewHolder.tvNumbers.getText().toString()));
                // Start the dialer app activity with number
                contactViewHolder.tvNumbers.getContext().startActivity(intent);
            }
        });

        if(ci.getContact2().equals("")){
        }else {
            SpannableString contact2 = new SpannableString(ci.getContact2().trim());
            contact2.setSpan(new UnderlineSpan(), 0, contact2.length(), 0);
            contactViewHolder.tvNumbers2.setText(contact2);
            contactViewHolder.tvNumbers2.setVisibility(View.VISIBLE);
//            contactViewHolder.tvNumbers2.setText(ci.getContact2().trim());
            contactViewHolder.tvNumbers2.setTextColor(Color.parseColor("#3157be"));
            contactViewHolder.tvNumbers2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    // Send phone number to intent as data
                    intent.setData(Uri.parse("tel:" + contactViewHolder.tvNumbers2.getText().toString()));
                    // Start the dialer app activity with number
                    contactViewHolder.tvNumbers2.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.tap_it_stop_it_item_list, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

//    @Override
//    public void onClick(View v) {
//
//        int id = v.getId();
//        switch (id){
//            case R.id.tap_it_stop_it_dialog_number:
//
//                break;
//
//            case R.id.tap_it_stop_it_dialog_number2 :
//
//                break;
//        }
//
//    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView servicesName, tvName, tvNumbers, tvNumbers2;

        public ContactViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tap_it_stop_it_dialog_name);
            tvNumbers = (TextView) v.findViewById(R.id.tap_it_stop_it_dialog_number);
            tvNumbers2 = (TextView) v.findViewById(R.id.tap_it_stop_it_dialog_number2);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
