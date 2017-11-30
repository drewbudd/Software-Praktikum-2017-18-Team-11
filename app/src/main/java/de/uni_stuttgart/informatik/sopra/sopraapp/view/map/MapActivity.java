package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.ContractsFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.DamagesFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.FieldsFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.ManageServiceFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.SearchFragment;

public class MapActivity extends AppCompatActivity implements
        MapFragment.OnFragmentInteractionListener, ManageServiceFragment.OnFragmentInteractionListener, ContractsFragment.OnFragmentInteractionListener, DamagesFragment.OnFragmentInteractionListener, FieldsFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
