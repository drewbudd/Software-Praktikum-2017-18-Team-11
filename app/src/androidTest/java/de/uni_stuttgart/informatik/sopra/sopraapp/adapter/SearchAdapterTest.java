package de.uni_stuttgart.informatik.sopra.sopraapp.adapter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2018/01/23
 */
public class SearchAdapterTest {

    @Rule
    public ActivityTestRule<MapActivity> mapActivityActivityTestRule = new ActivityTestRule<>(MapActivity.class);


    Context context;

    @Before
    public void init() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void onCreateViewHolder() throws Exception {

        List<Damage> damages = new ArrayList<Damage>();
        damages.add(new Damage());
        damages.add(new Damage());
        damages.add(new Damage());
        damages.add(new Damage());
        damages.add(new Damage());

        damages.get(0).setOwner(new User("stefan", ".."));
        damages.get(1).setOwner(new User("stefan", ".."));
        damages.get(2).setOwner(new User("karl", ".."));
        damages.get(3).setOwner(new User("peter", ".."));
        damages.get(4).setOwner(new User("Petra", ".."));
        for (Damage damage : damages) {
//            DataService.getInstance(context).addDamage(damage);
        }


    }

    @Test
    public void onBindViewHolder() throws Exception {
    }

    @Test
    public void getItemCount() throws Exception {
    }

    @Test
    public void getFilter() throws Exception {
    }

}