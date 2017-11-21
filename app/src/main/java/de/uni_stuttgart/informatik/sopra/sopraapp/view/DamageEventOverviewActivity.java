package de.uni_stuttgart.informatik.sopra.sopraapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.Setup;
import de.uni_stuttgart.informatik.sopra.sopraapp.adapter.DamageEventAdapter;

public class DamageEventOverviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_ovactvity);

        recyclerView = findViewById(R.id.damageEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        recyclerView.setAdapter(new DamageEventAdapter(this, Setup.dataService.getAllDamageEvents()));

    }
}
