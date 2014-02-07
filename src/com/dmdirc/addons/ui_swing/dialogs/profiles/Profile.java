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

import com.dmdirc.util.collections.ObservableList;
import com.dmdirc.util.collections.ObservableListDecorator;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Profile wrapper class.
 */
public class Profile {

    /**
     * Property change support.
     */
    private final PropertyChangeSupport pcs;
    /**
     * Profile Name, must be a sanitised filename.
     */
    private String name;
    /**
     * Real name.
     */
    private String realname;
    /**
     * Ident.
     */
    private String ident;
    /**
     * Nicknames.
     */
    private ObservableList<String> nicknames;
    /**
     * Has this profile been marked deleted?
     */
    private boolean deleted = false;

    public Profile() {
        this("New Profile", new ObservableListDecorator(new ArrayList<String>()), "", "");
    }

    public Profile(final String name, final ObservableList<String> nicknames, final String realname, final String ident) {
        pcs = new PropertyChangeSupport(this);
        this.name = name;
        this.nicknames = nicknames;
        this.realname = realname;
        this.ident = ident;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
        pcs.firePropertyChange("name", name, name);
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(final String realname) {
        this.realname = realname;
        pcs.firePropertyChange("realname", realname, realname);
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(final String ident) {
        this.ident = ident;
        pcs.firePropertyChange("ident", ident, ident);
    }

    public ObservableList<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(final ObservableList<String> nicknames) {
        this.nicknames = nicknames;
        pcs.firePropertyChange("nicknames", nicknames, nicknames);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
        pcs.firePropertyChange("deleted", deleted, deleted);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.realname);
        hash = 59 * hash + Objects.hashCode(this.ident);
        hash = 59 * hash + Objects.hashCode(this.nicknames);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profile other = (Profile) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.realname, other.realname)) {
            return false;
        }
        if (!Objects.equals(this.ident, other.ident)) {
            return false;
        }
        if (!Objects.equals(this.nicknames, other.nicknames)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Profile{" + "name=" + name + ", realname=" + realname
                + ", ident=" + ident + ", nicknames=" + nicknames + ", deleted=" + deleted + '}';
    }

    public void addPropertyChangeListener(final String property, final PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }

    public void removePropertyChangeListener(final String property, final PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
