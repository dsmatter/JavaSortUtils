package de.smatterling.sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

public class ComparatorsTest {

    @Test
    public void testIdentityComparator() {
        final Comparator<String> comparator = Comparators.identityComparator();

        assertEquals(0, comparator.compare("", ""));
        assertEquals(0, comparator.compare("", "foo"));
        assertEquals(0, comparator.compare("foo", ""));
        assertEquals(0, comparator.compare("foo", "bar"));
        assertEquals(0, comparator.compare(null, "bar"));
        assertEquals(0, comparator.compare(null, null));
    }

    @Test
    public void testNullComparator() {
        final Comparator<String> comparator = Comparators.nullComparator();

        assertEquals(0, comparator.compare("", ""));
        assertEquals(0, comparator.compare("foo", "bar"));
        assertEquals(0, comparator.compare(null, null));
        assertEquals(-1, comparator.compare(null, "bar"));
        assertEquals(1, comparator.compare("", null));
    }

    @Test
    public void testComparatorByWithNulls() {
        final Function<String, Integer> fooLengthGetter = new Function<String, Integer>() {

            @Override
            public Integer apply(String t) {
                if (t.equals("foo")) {
                    return null;
                }
                return t.length();
            }
        };

        final Comparator<String> fooComparator = new ComparatorBuilder<String>()
                .addNullComparator()
                .addComparatorBy(fooLengthGetter)
                .build();

        assertEquals(0, fooComparator.compare("foo", "foo"));
        assertTrue(fooComparator.compare("foo", "bar") < 0);
        assertTrue(fooComparator.compare("bar", "foo") > 0);
        assertTrue(fooComparator.compare("foobar", "bar") > 0);
    }

    @Test
    public void testComparatorBy() {
        final Comparator<String> lengthComparator = lengthComparator();

        assertEquals(0, lengthComparator.compare("", ""));
        assertEquals(0, lengthComparator.compare("foo", "bar"));
        assertTrue(lengthComparator.compare("foo", "barbaz") < 0);
        assertTrue(lengthComparator.compare("foobars", "barbaz") > 0);
        assertTrue(lengthComparator.compare(null, "barbaz") < 0);
    }

    @Test
    public void testComparatorByWithComparator() {
        final Comparator<Integer> comparator = Comparators.comparatorBy(new Function<Integer, String>() {

            @Override
            public String apply(Integer i) {
                if (i == null) {
                    return "null";
                }
                return String.valueOf(i);
            }

        }, lengthComparator());

        assertEquals(0, comparator.compare(null, null));
        assertEquals(0, comparator.compare(1, 1));
        assertEquals(0, comparator.compare(5, 9));
        assertTrue(comparator.compare(990, 32) > 0);
        assertTrue(comparator.compare(8, 12) < 0);
    }

    @Test
    public void testCombineComparators() {
        final Comparator<String> comparator = Comparators.combineComparators(lengthComparator(), stringComparator());

        assertEquals(0, comparator.compare("", ""));
        assertTrue(comparator.compare("", "foo") < 0);
        assertTrue(comparator.compare("foo", "bar") > 0);
        assertTrue(comparator.compare("foo", "barbaz") < 0);
        assertTrue(comparator.compare(null, "barbaz") < 0);
    }

    private Comparator<String> lengthComparator() {
        return Comparators.comparatorBy(new Function<String, Integer>() {

            @Override
            public Integer apply(String s) {
                if (s == null) {
                    return 0;
                }
                return s.length();
            }

        });
    }

    private Comparator<String> stringComparator() {
        return Comparators.fromComparable(String.class);
    }

}
