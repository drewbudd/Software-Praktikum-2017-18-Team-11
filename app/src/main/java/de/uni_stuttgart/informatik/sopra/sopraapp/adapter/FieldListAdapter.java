package de.uni_stuttgart.informatik.sopra.sopraapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Stefan Zindl
 * @since 2017/11/15
 *
 *
 * //TODO implement Adapater, aufbau
 * https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
 *
 */

public class FieldListAdapter extends RecyclerView.Adapter<FieldListAdapter.ViewHolder> {



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
