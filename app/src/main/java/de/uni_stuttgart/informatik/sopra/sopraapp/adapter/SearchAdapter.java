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
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 * <p>
 * <p>
 * //TODO implement Adapater, aufbau
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    private List<Damage> damageList;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public SearchAdapter(Context context, List<Damage> events) {
        this.damageList = events;
        this.context = context;
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
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Damage event = damageList.get(position);
        String insuranceHolder;

        holder.associateDamage = event;

        holder.damageType.setText(event.getDamageType());
        holder.inscuredPerson.setText("Blabla");

    }

    @Override
    public int getItemCount() {
        return damageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView inscuredPerson;
        public Damage associateDamage;
        public TextView damageType;

        public ViewHolder(View itemView) {
            super(itemView);

            card = (CardView) itemView;
            inscuredPerson = itemView.findViewById(R.id.insuredPerson);
            damageType = itemView.findViewById(R.id.card_damage_type);
        }
    }
}
