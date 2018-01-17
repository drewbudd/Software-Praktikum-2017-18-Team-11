package de.uni_stuttgart.informatik.sopra.sopraapp.view.dialogs;

import android.os.Bundle;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Stefan Zindl
 * @since 2018/01/16
 */
public class AddDamageDialogTest {
    @Test
    public void newInstance() throws Exception {
      AddDamageDialog dialog = AddDamageDialog.newInstance("Title");
       Assert.assertEquals(dialog.getArguments().get("title"),"Title");
    }

    @Test
    public void onCreateView() throws Exception {
    }

    @Test
    public void onViewCreated() throws Exception {
    }

}