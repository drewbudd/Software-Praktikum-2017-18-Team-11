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
import de.uni_stuttgart.informatik.sopra.sopraapp.Setup;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEvent;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 *
 *
 * //TODO implement Adapater, aufbau
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 *
 */

public class DamageEventAdapter extends RecyclerView.Adapter<DamageEventAdapter.ViewHolder> {


    private List<DamageEvent> damageEvents;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public DamageEventAdapter(Context context, List<DamageEvent> events) {
        this.damageEvents = events;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_damageevent, parent, false);

        ViewHolder vh = new ViewHolder(view);
        views.add(vh);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DamageEvent event = damageEvents.get(position);
        String insuranceHolder;

        holder.associateDamageEvent = event;

        holder.inscuredPerson.setText(Setup.dataService.getCurrentLoggedInUser().getName());

    }

    @Override
    public int getItemCount() {
        return this.damageEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView inscuredPerson;
        public DamageEvent associateDamageEvent;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            inscuredPerson = itemView.findViewById(R.id.insuredPerson);
        }
    }
}
