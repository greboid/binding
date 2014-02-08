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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.AbstractButton;

public class AbstractButtonBinder<T> implements ActionListener, Binder<T> {
    private final Method actionMethod;
    private T object;
    
    public AbstractButtonBinder(final AbstractButton button, final Class<? extends T> type, final String methodName) {
        try {
            actionMethod = type.getMethod(methodName);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new IllegalArgumentException("Unable to find method", ex);
        }
        button.addActionListener(this);
    }

    @Override
    public void setObject(final T object) throws IntrospectionException, ReflectiveOperationException {
        this.object = object;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        try {
            actionMethod.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalStateException("Unable to execute method", ex);
        }
    }
    
}
