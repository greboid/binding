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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 * Binds a JTextComponent to a bean.
 */
public class JTextComponentBinder<O> implements DocumentListener, PropertyChangeListener, Binder<O> {

    private final JTextComponent text;
    private final String property;
    private O object;
    private Method read;
    private Method write;
    private Method add;
    private Method remove;

    public JTextComponentBinder(final JTextComponent list, final String property)
            throws IntrospectionException, ReflectiveOperationException {
        this.text = list;
        this.property = property;
    }

    @Override
    public void setObject(final O object) throws IntrospectionException, ReflectiveOperationException {
        if (this.object != null) {
            remove.invoke(object, property, this);
        }
        this.object = object;
        if (object == null) {
            this.read = null;
            this.write = null;
            this.add = null;
            this.remove = null;
            return;
        }
        final PropertyDescriptor propertyDescriptor
                = new PropertyDescriptor(property, object.getClass());
        read = propertyDescriptor.getReadMethod();
        write = propertyDescriptor.getWriteMethod();
        add = object.getClass().getMethod("addPropertyChangeListener", String.class,
                PropertyChangeListener.class);
        remove = object.getClass().getMethod("removePropertyChangeListener", String.class,
                PropertyChangeListener.class);
        final String value = (String) read.invoke(object);
        write.invoke(object, value);
        text.setText(value);
        add.invoke(object, property, this);
        text.getDocument().addDocumentListener(this);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateText(e);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateText(e);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String value;
        try {
            value = (String) read.invoke(object);
            text.setText(value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalStateException("Unable to update bound text: " + ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateText(e);
    }

    private void updateText(DocumentEvent e) {
        try {
            write.invoke(object, e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalStateException("Unable to update bound text: " + ex);
        }
    }
}
