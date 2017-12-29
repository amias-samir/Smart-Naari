package com.nepal.naxa.smartnaari.passion_of_life.heteregenouscomplexrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nepal.naxa.smartnaari.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;


/**
 * Created by samir on 12/27/2017.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder {

    private TextView head;
    private HtmlTextView body;
    private ImageView image;
    private TextView author;
    private TextView expandableTitle ;

    public ViewHolder1(View v) {
        super(v);
        head = (TextView) v.findViewById(R.id.tv_title_black);
        author = (TextView) v.findViewById(R.id.tv_title_white);
        expandableTitle = (TextView) v.findViewById(R.id.iv_expand_title);
        body = (HtmlTextView) v.findViewById(R.id.tv_content);
        image = (ImageView) v.findViewById(R.id.iv_cover);
    }

    public TextView getHead() {
        return head;
    }

    public void setHead(TextView head) {
        this.head = head;
    }

    public HtmlTextView getBody() {
        return body;
    }

    public void setBody(HtmlTextView body) {
        this.body = body;
    }


    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getAuthor() {
        return author;
    }

    public void setAuthor(TextView author) {
        this.author = author;
    }


    public TextView getExpandableTitle() {
        return expandableTitle;
    }

    public void setExpandableTitle(TextView expandableTitle) {
        this.expandableTitle = expandableTitle;
    }


}