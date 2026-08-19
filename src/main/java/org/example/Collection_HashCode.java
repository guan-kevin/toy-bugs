package org.example;

public class Collection_HashCode {
    private int a;
    public Collection_HashCode(int a) {
        this.a = a;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Collection_HashCode that = (Collection_HashCode) obj;
        return a == that.a;
    }
}
