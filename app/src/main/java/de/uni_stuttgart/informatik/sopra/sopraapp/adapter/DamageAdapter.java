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
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 *
 *
 * //TODO implement Adapater, aufbau
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 *
 */

public class DamageAdapter extends RecyclerView.Adapter<DamageAdapter.ViewHolder> {


    private List<Damage> damages;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public DamageAdapter(Context context, List<Damage> events) {
        this.damages = events;
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
        Damage event = damages.get(position);
        String insuranceHolder;

        holder.associateDamage = event;

        holder.inscuredPerson.setText(App.userService.getCurrentUser().getName());

    }

    @Override
    public int getItemCount() {
        return this.damages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView inscuredPerson;
        public Damage associateDamage;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            inscuredPerson = itemView.findViewById(R.id.insuredPerson);
        }
    }
}
