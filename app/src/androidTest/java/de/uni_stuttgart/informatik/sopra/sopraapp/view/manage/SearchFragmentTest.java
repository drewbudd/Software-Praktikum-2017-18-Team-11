package de.uni_stuttgart.informatik.sopra.sopraapp.view.manage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * @author Stefan Zindl
 * @since 2018/01/15
 */
public class SearchFragmentTest {
    @Test
    public void onCreate() throws Exception {

        onView(withId(R.id.searchView));
        onView(withId(R.id.recycler_search));
    }
/*
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void newInstance() throws Exception {
    }

    @Test
    public void onCreateView() throws Exception {
    }

    @Test
    public void updateAdapter() throws Exception {
    }

    @Test
    public void onButtonPressed() throws Exception {
    }

    @Test
    public void onAttach() throws Exception {
    }

    @Test
    public void onDetach() throws Exception {
    }

    @Test
    public void getSearchAdapter() throws Exception {
    }

    @Test
    public void onResume() throws Exception {
    } */

}