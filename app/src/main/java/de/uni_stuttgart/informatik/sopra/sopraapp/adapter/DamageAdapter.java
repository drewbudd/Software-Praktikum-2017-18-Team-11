package de.uni_stuttgart.informatik.sopra.sopraapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 * <p>
 * <p>
 * Adapter for all fields
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 */

public class DamageAdapter extends RecyclerView.Adapter<DamageAdapter.ViewHolder> {


    private List<Field> fieldList;
    private List<Field> filterdList;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public DamageAdapter(Context context, List<Field> events) {
        this.fieldList = events;
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
    public void onBindViewHolder(DamageAdapter.ViewHolder holder, int position) {
        Field event = fieldList.get(position);
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
        return fieldList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView inscuredPerson;
        public Field associatedField;
        public TextView fieldType;
        public TextView gutachter;
        public ImageButton deleteFieldButton;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            inscuredPerson = itemView.findViewById(R.id.insuredPerson);
            fieldType = itemView.findViewById(R.id.card_field_type);
            gutachter = itemView.findViewById(R.id.gutachter);
            deleteFieldButton = itemView.findViewById(R.id.deleteFieldButton);
            deleteFieldButton.setVisibility(View.GONE);
            if(UserService.getInstance().getCurrentUser().isGutachter()) {
                deleteFieldButton.setVisibility(View.VISIBLE);
                deleteFieldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MapActivity.dataService.deleteFieldById(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition(), fieldList.size());
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
