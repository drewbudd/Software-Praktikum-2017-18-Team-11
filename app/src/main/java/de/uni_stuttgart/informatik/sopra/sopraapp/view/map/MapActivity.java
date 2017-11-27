package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

public class MapActivity extends AppCompatActivity implements
        MapFragment.OnFragmentInteractionListener, ManageServiceFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
