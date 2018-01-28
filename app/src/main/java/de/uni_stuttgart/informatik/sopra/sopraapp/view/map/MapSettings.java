package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

public class MapSettings extends ListActivity {
    private String[] entries = {"Logout", "Change Language"};
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_settings);
        l = getListView();
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entries);
        l.setAdapter(stringAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

    }
}
