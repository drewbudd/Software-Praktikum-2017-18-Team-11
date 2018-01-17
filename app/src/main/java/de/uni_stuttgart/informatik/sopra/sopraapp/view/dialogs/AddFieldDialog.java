package de.uni_stuttgart.informatik.sopra.sopraapp.view.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;


public class AddFieldDialog extends DialogFragment {


    public AddFieldDialog() {
        // required empty constructor
    }

    public static AddFieldDialog newInstance(String title, double size) {
        AddFieldDialog frag = new AddFieldDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putDouble("size", Math.round(size * 100)/100.0);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_add_field, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText mEditText = view.findViewById(R.id.text_field_name);

        String title = getArguments().getString("title");
        getDialog().setTitle(title);

        TextView sizeText = view.findViewById(R.id.label_field_size);
        sizeText.setText("" + getArguments().getDouble("size"));

        Spinner contract_spinner = view.findViewById(R.id.contract_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.contract_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contract_spinner.setAdapter(adapter);

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
