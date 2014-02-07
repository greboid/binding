/*
 * Copyright (c) 2006-2014 DMDirc Developers
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dmdirc.addons.ui_swing.dialogs.profiles;

import com.dmdirc.addons.ui_swing.components.renderers.PropertyListCellRenderer;
import com.dmdirc.addons.ui_swing.components.text.TextLabel;
import com.dmdirc.addons.ui_swing.dialogs.StandardDialog;
import com.dmdirc.addons.ui_swing.dialogs.profiles.binding.ConvertingBinder;
import com.dmdirc.addons.ui_swing.dialogs.profiles.binding.JListBinder;
import com.dmdirc.addons.ui_swing.dialogs.profiles.binding.JTextComponentBinder;
import com.dmdirc.addons.ui_swing.dialogs.profiles.binding.ListSelectionPropertyBinder;
import com.dmdirc.util.collections.ObservableList;
import com.dmdirc.util.collections.ObservableListDecorator;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * Profile editing dialog.
 */
public class ProfileManagerDialog extends StandardDialog {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3;
    private JButton addNickname;
    private JButton addProfile;
    private JButton deleteNickname;
    private JButton deleteProfile;
    private JButton editNickname;
    private JTextField ident;
    private JTextField name;
    private JList<String> nicknames;
    private JList<Profile> profiles;
    private JTextField realname;

    public ProfileManagerDialog() {
        super(null, ModalityType.MODELESS);
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        profiles = new JList<>();
        profiles.setCellRenderer(new PropertyListCellRenderer<>(profiles.getCellRenderer(), Profile.class, "name"));
        addProfile = new JButton("Add");
        deleteProfile = new JButton("Delete");
        name = new JTextField();
        nicknames = new JList<>();
        addNickname = new JButton("Add");
        editNickname = new JButton("Edit");
        deleteNickname = new JButton("Delete");
        realname = new JTextField();
        ident = new JTextField();
        setOkButton(new JButton());
        setCancelButton(new JButton());
        final List<Profile> profileList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final ObservableList<String> nicknameList = new ObservableListDecorator<>(new ArrayList<String>());
            nicknameList.add("nickname 1-" + i);
            nicknameList.add("nickname 2-" + i);
            profileList.add(new Profile("Profile: " + i, nicknameList, "realname " + i, "ident " + i));
        }
        try {
            final JListBinder<Profile> listBinder = new JListBinder<>(profiles);
            listBinder.setObject(new ObservableListDecorator<>(new ArrayList<>(profileList)));
            final JListBinder<String> nicknamesBinder = new JListBinder<>(nicknames);
            final ConvertingBinder<Profile, ObservableList<String>> profileNicknameConverter = new ConvertingBinder<>(nicknamesBinder, "nicknames");
            final JTextComponentBinder<Profile> nameBinder = new JTextComponentBinder<>(name, "name");
            final JTextComponentBinder<Profile> realnameBinder = new JTextComponentBinder<>(realname, "realname");
            final JTextComponentBinder<Profile> identBinder = new JTextComponentBinder<>(ident, "ident");
            final ListSelectionPropertyBinder<Profile> listSelection = new ListSelectionPropertyBinder<>(listBinder, Arrays.asList(nameBinder, realnameBinder, identBinder, profileNicknameConverter));
            listSelection.bind();
        } catch (ReflectiveOperationException | IntrospectionException ex) {
            throw new RuntimeException("Unable to create dialog.", ex);
        }
    }

    private void layoutComponents() {
        setLayout(new MigLayout("fill, wmin 700, wmax 700, flowy"));

        add(new TextLabel("Profiles describe the information needed to connect "
                + "to a server.  You can use a different profile for each "
                + "connection."), "spanx 3");
        add(new JScrollPane(profiles), "spany 3, growy, "
                + "wmin 200, wmax 200");
        add(addProfile, "grow");
        add(deleteProfile, "grow, wrap");
        add(new JLabel("Name: "), "align label, span 2, split 2, flowx, sgx label");
        add(name, "growx, pushx, sgx textinput");
        add(new JLabel("Nicknames: "), "align label, span 2, split 2, flowx, sgx label, aligny 50%");
        add(new JScrollPane(nicknames), "grow, push");
        add(Box.createGlue(), "flowx, span 4, split 4, sgx label");
        add(addNickname, "grow");
        add(editNickname, "grow");
        add(deleteNickname, "grow");
        add(new JLabel("Realname: "), "align label, span 2, split 2, flowx, sgx label");
        add(realname, "growx, pushx, sgx textinput");
        add(new JLabel("Ident: "), "align label, span 2, split 2, flowx, sgx label");
        add(ident, "growx, pushx, sgx textinput");
        add(getLeftButton(), "flowx, split 2, right, sg button");
        add(getRightButton(), "right, sg button");
    }
}
