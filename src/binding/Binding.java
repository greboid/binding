/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package binding;

import com.dmdirc.addons.ui_swing.dialogs.profiles.ProfileManagerDialog;

/**
 *
 */
public class Binding {

    /**
     * @param args the command line arguments
     */
    public static void main(String... args) {
        ProfileManagerDialog dialog = new ProfileManagerDialog();
        dialog.pack();
        dialog.setVisible(true);
    }

}
