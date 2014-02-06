/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dmdirc.addons.ui_swing.dialogs.profiles.binding;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 *
 */
public class ConvertingBinder<T, O> implements Binder<T> {

    private final Binder<O> to;
    private final String property;

    public ConvertingBinder(final Binder<O> to, final String property) {
        this.to = to;
        this.property = property;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setObject(T object) throws IntrospectionException, ReflectiveOperationException {
        final PropertyDescriptor propertyDescriptor
                = new PropertyDescriptor(property, object.getClass());
        final Method read = propertyDescriptor.getReadMethod();
        to.setObject((O) read.invoke(object));
    }

}
