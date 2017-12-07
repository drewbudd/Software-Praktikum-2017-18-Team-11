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
import de.uni_stuttgart.informatik.sopra.sopraapp.model.Contract;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 * <p>
 * <p>
 * //TODO implement Adapater, aufbau
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 */

public class ContractListAdapter extends RecyclerView.Adapter<ContractListAdapter.ViewHolder> {


    private List<Contract> damageList;
    private List<Contract> filterdList;
    private List<ViewHolder> views = new ArrayList<>();
    private Context context;

    public ContractListAdapter(Context context, List<Contract> events) {
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
    public void onBindViewHolder(ContractListAdapter.ViewHolder holder, int position) {
        Contract event = damageList.get(position);
        String insuranceHolder;

        holder.associatedContract = event;
        holder.contractType.setText(event.getContractType());
    }

    @Override
    public int getItemCount() {
        return damageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView contractType;
        public Contract associatedContract;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            contractType = itemView.findViewById(R.id.contractType);
        }
    }
}
