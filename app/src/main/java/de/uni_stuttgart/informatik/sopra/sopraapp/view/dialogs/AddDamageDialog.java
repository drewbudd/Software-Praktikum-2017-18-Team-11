package de.uni_stuttgart.informatik.sopra.sopraapp.view.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;


public class AddDamageDialog extends DialogFragment {

    private Spinner damageType;

    public AddDamageDialog () {
        // required empty constructor
    }

    public static AddDamageDialog newInstance(String title, double size) {
        AddDamageDialog frag = new AddDamageDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putDouble("size", Math.round(size * 100)/100.0);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_add_damage, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        damageType = view.findViewById(R.id.text_damage_typeText);

        TextView sizeText = view.findViewById(R.id.damage_size);
        sizeText.setText("" + getArguments().getDouble("size"));

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public String getDamageType() {
        return damageType.getSelectedItem().toString();
    }

}
