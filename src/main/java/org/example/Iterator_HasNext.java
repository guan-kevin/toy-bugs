package org.example;

import java.util.Iterator;
import java.util.List;

public class Iterator_HasNext {

    public String getFirstExpected(List<String> list) {
        Iterator<String> iter = list.iterator();
        if (iter.hasNext()) {
            return iter.next();
        }
        return null;
    }

    public String getFirstSafe(List<String> list) {
        if (list.size() > 0) {
            return list.iterator().next();
        }
        return null;
    }

    public String getFirstSafe2(List<String> list) {
        if (!list.isEmpty()) {
            return list.iterator().next();
        }
        return null;
    }

    public String getSafeUnsafe(List<String> list) {
        return list.iterator().next();
    }
}
