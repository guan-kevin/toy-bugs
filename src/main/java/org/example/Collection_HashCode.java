package org.example;

import java.util.AbstractCollection;
import java.util.Iterator;

public class Collection_HashCode<E> extends AbstractCollection<E> {

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Collection_HashCode;
    }

    // no hashCode()
}