package org.example;

import javax.swing.text.Segment;

public class CharSequence_UndefinedHashCode {
    public void foo() {
        MyCharSequence segment = new MyCharSequence();
        segment.hashCode();
    }

    public void bar() {
        MyCharSequence segment = new MyCharSequence();
        segment.equals(new MyCharSequence());
    }

    public class MyCharSequence implements CharSequence {

        @Override
        public int hashCode() {
            return 42;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return this;
        }
    }
}
