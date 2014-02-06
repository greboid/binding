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

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Profile wrapper class.
 */
public class Profile {

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
    private List<String> nicknames;
    /**
     * Has this profile been marked deleted?
     */
    private boolean deleted = false;

    public Profile() {
        this("New Profile", new ArrayList<>(), "", "");
    }

    public Profile(final String name, final List<String> nicknames, final String realname, final String ident) {
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
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(final String realname) {
        this.realname = realname;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(final String ident) {
        this.ident = ident;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(final List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Adds a nickname to this profile.
     *
     * @param nickname A new nickname for the profile
     */
    public void addNickname(final String nickname) {
        if (!nicknames.contains(nickname)) {
            nicknames.add(nickname);
        }
    }

    /**
     * Adds a nickname to this profile.
     *
     * @param nickname A new nickname for the profile
     * @param position Position for the new alternate nickname
     */
    public void addNickname(final String nickname, final int position) {
        if (!nicknames.contains(nickname)) {
            nicknames.add(position, nickname);
        }
    }

    /**
     * Deletes a nickname from this profile.
     *
     * @param nickname An existing nickname from the profile
     */
    public void delNickname(final String nickname) {
        nicknames.remove(nickname);
    }

    /**
     * Edits a nickname in the list.
     *
     * @param nickname Nickname to edit
     * @param newNickname Edited nickname
     */
    public void editNickname(final String nickname, final String newNickname) {
        if (nickname.isEmpty() || newNickname.isEmpty()) {
            return;
        }
        if (!nickname.equals(newNickname)) {
            final int index = nicknames.indexOf(nickname);
            nicknames.remove(nickname);
            nicknames.add(index, newNickname);
        }
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
        //Do stuff
    }

    public void removePropertyChangeListener(final String property, final PropertyChangeListener listener) {
        //Do stuff
    }
}
