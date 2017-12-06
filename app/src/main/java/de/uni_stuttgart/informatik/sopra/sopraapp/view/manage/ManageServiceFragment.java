package de.uni_stuttgart.informatik.sopra.sopraapp.view.manage;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageServiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageServiceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private TabLayout tabLayout;

    private OnFragmentInteractionListener mListener;

    public ManageServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageServiceFragment newInstance(String param1, String param2) {
        ManageServiceFragment fragment = new ManageServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_manage, container, false);

        tabLayout = rootView.findViewById(R.id.tab_layout);

        replaceFragment(new FieldsFragment());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                com.sothree.slidinguppanel.SlidingUpPanelLayout slidingLayout = getActivity().findViewById(R.id.sliding_layout);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                switch(tab.getPosition()) {
                    case 0:
                        replaceFragment(new FieldsFragment());
                        break;
                    case 1:
                        replaceFragment(new DamagesFragment());
                        break;
                    case 2:
                        replaceFragment(new ContractsFragment());
                        break;
                    case 3:
                        replaceFragment(new SearchFragment());
                        break;
                    default:
                        // do nothing
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                com.sothree.slidinguppanel.SlidingUpPanelLayout slidingLayout = getActivity().findViewById(R.id.sliding_layout);
                if (slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                }
            }
        });

        return rootView;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.info_fragment, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public SearchFragment getSearchFragment() {
        return (SearchFragment) getFragmentManager().findFragmentById(R.id.searchView);
    }
}
