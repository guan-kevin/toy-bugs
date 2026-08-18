package org.example;

import javax.swing.text.Segment;

public class CharSequence_UndefinedHashCode {
    public void foo() {
        CharSequence segment = new Segment();
        segment.hashCode();
    }

    public void bar() {
        CharSequence segment = new Segment();
        segment.equals(new Segment());
    }
}
