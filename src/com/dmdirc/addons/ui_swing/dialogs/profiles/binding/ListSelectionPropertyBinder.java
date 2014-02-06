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

package com.dmdirc.addons.ui_swing.dialogs.profiles.binding;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 */
public class ListSelectionPropertyBinder<T> implements ListSelectionListener {

    private final JListBinder<T> listBinder;
    private final Collection<Binder<T>> propertyBinders;

    public ListSelectionPropertyBinder(final JListBinder<T> listBinder,
            final Collection<Binder<T>> propertyBinders) {
        this.listBinder = listBinder;
        this.propertyBinders = propertyBinders;
    }

    public void bind() {
        listBinder.addListener(this);
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        for (Binder<T> binder : propertyBinders) {
            try {
                binder.setObject(listBinder.getList().getSelectedValue());
            } catch (IntrospectionException | ReflectiveOperationException ex) {
                Logger.getLogger(ListSelectionPropertyBinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
