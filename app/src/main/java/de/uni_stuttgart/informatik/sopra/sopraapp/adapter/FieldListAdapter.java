package de.uni_stuttgart.informatik.sopra.sopraapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 * <p>
 * <p>
 * //TODO implement Adapater, aufbau
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 */

public class FieldListAdapter extends RecyclerView.Adapter<FieldListAdapter.ViewHolder> {


    private List<Field> damageList;
    private List<Field> filterdList;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public FieldListAdapter(Context context, List<Field> events) {
        this.damageList = events;
        this.context = context;
        filterdList = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_field, parent, false);

        ViewHolder vh = new ViewHolder(v);
        views.add(vh);

        return vh;
    }

    @Override
    public void onBindViewHolder(FieldListAdapter.ViewHolder holder, int position) {
        Field event = damageList.get(position);
        String insuranceHolder;

        holder.associatedField = event;

        if (event.getFieldType() != null) {

            holder.fieldType.setText(event.getFieldType());
        }
        if (event.getOwner() != null) {
            holder.inscuredPerson.setText(event.getOwner().getName());
        }
        if (event.getGutachter() != null) {
            holder.gutachter.setText(event.getGutachter().getName());
        }

    }

    @Override
    public int getItemCount() {
        return damageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView inscuredPerson;
        public Field associatedField;
        public TextView fieldType;
        public TextView gutachter;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            inscuredPerson = itemView.findViewById(R.id.insuredPerson);
            fieldType = itemView.findViewById(R.id.card_field_type);
            gutachter = itemView.findViewById(R.id.gutachter);
        }
    }
}
