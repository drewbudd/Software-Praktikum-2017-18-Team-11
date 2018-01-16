package de.uni_stuttgart.informatik.sopra.sopraapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 * <p>
 * <p>
 * Adapter for the searchList
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {


    private List<Damage> damageList = new ArrayList<>();
    private List<Damage> filterdList = new ArrayList<>();
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;
    private ValueFilter valueFilter;

    public SearchAdapter(Context context, List<Damage> events) {
        this.damageList = events;
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
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Damage event = damageList.get(position);
        String insuranceHolder;

        holder.associateDamage = event;

        holder.damageType.setText(event.getDamageType());
        holder.inscuredPerson.setText(event.getOwner().getName());

    }

    @Override
    public int getItemCount() {
        return damageList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
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

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List filterList = new ArrayList();

                for (Damage damage : damageList) {
                    if (damage.getDamageType() != null) {
                        if (damage.getDamageType().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterList.add(damage);
                        }
                    }
                    if (damage.getOwner() != null) {
                        if (damage.getOwner().getName() != null) {
                            if (damage.getOwner().getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                filterList.add(damage);
                            }
                        }

                    }
                }

                results.count = filterList.size();
                results.values = filterList;
            } else {
                List<Damage> damages = MapActivity.dataService.getDamages();
                results.count =  damages.size();
                results.values = damages;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if ((List) results.values == null) {

            } else {

                damageList = (List) results.values;
            }
            notifyDataSetChanged();
        }


    }
}
