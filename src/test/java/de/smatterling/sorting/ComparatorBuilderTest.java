package de.smatterling.sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

public class ComparatorBuilderTest {

    @Test
    public void testBuilder() {
        final ComparatorBuilder<SomeData> builder = new ComparatorBuilder<ComparatorBuilderTest.SomeData>();

        // Java 8 equivalent: builder.addComparatorBy(SomeData::getS)
        builder.addComparatorBy(new Function<ComparatorBuilderTest.SomeData, String>() {
            public String apply(SomeData t) {
                return t.getS();
            }
        }).addComparatorBy(new Function<ComparatorBuilderTest.SomeData, Integer>() {
            public Integer apply(SomeData t) {
                return t.getI();
            }
        }).addComparatorBy(new Function<ComparatorBuilderTest.SomeData, Double>() {
            public Double apply(SomeData t) {
                return t.getD();
            }
        });

        final Comparator<SomeData> comparator = builder.build();
        assertEquals(0, comparator.compare(new SomeData("", 0, 0), new SomeData("", 0, 0)));
        assertTrue(comparator.compare(new SomeData("a", 0, 0), new SomeData("a", 0, 1)) < 0);
        assertTrue(comparator.compare(new SomeData("a", 1, 0), new SomeData("a", 0, 1)) > 0);
        assertTrue(comparator.compare(new SomeData("b", 1, 0), new SomeData("a", 0, 1)) > 0);
    }

    public static class SomeData {
        private String s;
        private int i;
        private double d;

        public SomeData(String s, int i, double d) {
            this.s = s;
            this.i = i;
            this.d = d;
        }

        public String getS() {
            return s;
        }

        public int getI() {
            return i;
        }

        public double getD() {
            return d;
        }

    }
}
