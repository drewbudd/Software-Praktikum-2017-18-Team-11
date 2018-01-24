package de.uni_stuttgart.informatik.sopra.sopraapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.DamageDetailActivity;
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

public class DamageAdapter extends RecyclerView.Adapter<DamageAdapter.ViewHolder> {


    private List<Damage> fieldList;
    private List<Damage> filterdList;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public DamageAdapter(Context context, List<Damage> events) {
        this.fieldList = events;
        this.context = context;
        filterdList = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_damageevent, parent, false);

        ViewHolder vh = new ViewHolder(v);
        views.add(vh);

        return vh;
    }

    @Override
    public void onBindViewHolder(DamageAdapter.ViewHolder holder, int position) {
        Damage event = fieldList.get(position);
        String insuranceHolder;

        holder.associatedField = event;

        if (event.getDamageType() != null) {

            holder.damageType.setText(": " + event.getDamageType());
        }
        if (event.getOwner() != null) {
            holder.insuredPerson.setText(": " + event.getOwner().getName());
        }
        if (event.getDate() != null) {
            holder.date.setText(": " + event.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView insuredPerson;
        public Damage associatedField;
        public TextView damageType;
        public TextView gutachter;
        public TextView date;
        public ImageButton deleteFieldButton;
        public ImageButton detailButton;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            insuredPerson = itemView.findViewById(R.id.insuredPerson);
            damageType = itemView.findViewById(R.id.card_damage_type);
            gutachter = itemView.findViewById(R.id.agent);
            detailButton = itemView.findViewById(R.id.infoDamageButton);
            date = itemView.findViewById(R.id.card_damage_date);

            detailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapActivity.dataService.setDetailDamage(fieldList.get(getAdapterPosition()));
                    Intent detailIntent = new Intent(context, DamageDetailActivity.class);
                    context.startActivity(detailIntent);
                }
            });

            deleteFieldButton = itemView.findViewById(R.id.deleteDamageButton);
            deleteFieldButton.setVisibility(View.GONE);


            if (UserService.getInstance(LoginActivity.getCurrentContext()).getCurrentUser().isGutachter()) {
                deleteFieldButton.setVisibility(View.VISIBLE);
                deleteFieldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MapActivity.dataService.deleteDamage(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition(), fieldList.size());
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
