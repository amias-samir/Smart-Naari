package com.nepal.naxa.smartnaari.machupbasdina;

        import android.support.v7.widget.RecyclerView;
        import android.text.util.Linkify;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.nepal.naxa.smartnaari.R;
        import com.nepal.naxa.smartnaari.data.network.ServicesData;

        import java.util.List;

/**
 * Created by samir on 1/16/2018.
 */

public class ServicesListDialogAdapter extends RecyclerView.Adapter<ServicesListDialogAdapter.ContactViewHolder> {

    private List<ServicesData> itemList;

    public ServicesListDialogAdapter(List<ServicesData> cList) {
        this.itemList = cList;
    }


    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ServicesData ci = itemList.get(i);
        contactViewHolder.servicesName.setText(i+1+". "+ci.getOfficeName().trim());

        contactViewHolder.tvContactDutyPerson.setText("Mobile no(s). \n"+ci.getContactDutyPersonContactNumber().trim());
        Linkify.addLinks(contactViewHolder.tvContactDutyPerson, Linkify.PHONE_NUMBERS);

        contactViewHolder.tvOffielandLine.setText("Landline no(s). \n"+ci.getOfficeLandline().trim());
        Linkify.addLinks(contactViewHolder.tvOffielandLine, Linkify.PHONE_NUMBERS);


    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.services_list_near_incident_list_adapter_item_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView servicesName, tvContactDutyPerson, tvOffielandLine;

        public ContactViewHolder(View v) {
            super(v);
            servicesName = (TextView) v.findViewById(R.id.tv_services_name_near_incident);
            tvContactDutyPerson = (TextView) v.findViewById(R.id.services_dialog_contact_duty_person_no);
            tvOffielandLine = (TextView) v.findViewById(R.id.services_dialog_office_landline_no);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
