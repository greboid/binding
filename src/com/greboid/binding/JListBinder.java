/*
 * Copyright (c) 2006-2014 Greg 'Greboid' Holmes
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

package com.greboid.binding;

import com.dmdirc.util.collections.ListObserver;
import com.dmdirc.util.collections.ListenerList;
import com.dmdirc.util.collections.ObservableList;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Binds a JList to a bean.
 *
 * @param <T> Type contained in binder
 */
public class JListBinder<T> implements ListSelectionListener, ListDataListener, ListObserver, Binder<ObservableList<T>> {

    private final ListenerList listeners;
    private final JList<T> list;
    private final DefaultListModel<T> model;
    private ObservableList<T> object;

    public JListBinder(final JList<T> list) throws IntrospectionException {
        this.listeners = new ListenerList();
        this.model = new DefaultListModel<>();
        this.list = list;
    }

    @Override
    public void setObject(final ObservableList<T> object) {
        if (this.object != null) {
            list.getModel().removeListDataListener(this);
            list.getSelectionModel().removeListSelectionListener(this);
        }
        this.object = (ObservableList<T>) object;
        model.clear();
        list.setModel(model);
        for (T item : this.object) {
            model.addElement(item);
        }
        list.getModel().addListDataListener(this);
        list.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void onItemsAdded(final Object source, final int startIndex, final int endIndex) {
        final List<T> subList = object.subList(startIndex, endIndex);
        int index = startIndex;
        for (T item : subList) {
            model.add(index++, item);
        }
        model.removeRange(startIndex, endIndex);
    }

    @Override
    public void onItemsChanged(final Object source, final int startIndex, final int endIndex) {
        System.out.println("Items changed...");
    }

    @Override
    public void onItemsRemoved(final Object source, final int startIndex, final int endIndex) {
        model.removeRange(startIndex, endIndex);
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        listeners.getCallable(ListSelectionListener.class).valueChanged(e);
    }

    public void addListener(final ListSelectionListener listener) {
        listeners.add(ListSelectionListener.class, listener);
    }

    public void removeListener(final ListSelectionListener listener) {
        listeners.remove(ListSelectionListener.class, listener);
    }

    public JList<T> getList() {
        return list;
    }

    @Override
    public void intervalAdded(ListDataEvent e) {
        for (int i = e.getIndex0(); i < e.getIndex1(); i++) {
            @SuppressWarnings("unchecked")
            final T value = (T) model.getElementAt(i);
            object.add(i, value);
        }
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        for (int i = e.getIndex0(); i < e.getIndex1(); i++) {
            object.remove(i);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void contentsChanged(ListDataEvent e) {
        model.clear();
        list.setModel(model);
        for (T item : this.object) {
            model.addElement(item);
        }
    }
}
