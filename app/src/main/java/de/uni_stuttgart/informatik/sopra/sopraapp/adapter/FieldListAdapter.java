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
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 * <p>
 * <p>
 * Adapter for all fields
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 */

public class FieldListAdapter extends RecyclerView.Adapter<FieldListAdapter.ViewHolder> {


    private List<Field> fieldList;
    private List<Field> filterdList;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public FieldListAdapter(Context context, List<Field> events) {
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
    public void onBindViewHolder(FieldListAdapter.ViewHolder holder, int position) {
        Field event = fieldList.get(position);
        String insuranceHolder;

        holder.associatedField = event;

        if (event.getFieldType() != null) {
            holder.fieldType.setText(": " + event.getFieldType());
        }
        if (event.getOwner() != null) {
            holder.insuredPerson.setText(event.getOwner().getName());
        }
        if (event.getAgent() != null) {
            holder.agent.setText(event.getAgent().getName());
        }
        holder.size.setText(": " + (Math.round(event.getSize() * 10) / 10.0) + " " + MapActivity.getInstance().getResources().getString(R.string.size_units));

    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView insuredPerson;
        public Field associatedField;
        public TextView fieldType;
        public TextView agent;
        public TextView size;
        public ImageButton deleteFieldButton;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            insuredPerson = itemView.findViewById(R.id.insuredPerson);
            fieldType = itemView.findViewById(R.id.card_field_type);
            agent = itemView.findViewById(R.id.agent);
            size = itemView.findViewById(R.id.card_field_size);
            deleteFieldButton = itemView.findViewById(R.id.deleteFieldButton);
            deleteFieldButton.setVisibility(View.GONE);
            if(UserService.getInstance(LoginActivity.getCurrentContext()).getCurrentUser().isGutachter()) {
                deleteFieldButton.setVisibility(View.VISIBLE);
                deleteFieldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MapActivity.dataService.deleteField(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition(), fieldList.size());
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
