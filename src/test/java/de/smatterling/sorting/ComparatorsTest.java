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
    }

    @Test
    public void testComparatorBy() {
        final Comparator<String> lengthComparator = lengthComparator();

        assertEquals(0, lengthComparator.compare("", ""));
        assertEquals(0, lengthComparator.compare("foo", "bar"));
        assertTrue(lengthComparator.compare("foo", "barbaz") < 0);
        assertTrue(lengthComparator.compare("foobars", "barbaz") > 0);
    }

    @Test
    public void testCombineComparators() {
        final Comparator<String> comparator = Comparators.combineComparators(lengthComparator(), stringComparator());

        assertEquals(0, comparator.compare("", ""));
        assertTrue(comparator.compare("", "foo") < 0);
        assertTrue(comparator.compare("foo", "bar") > 0);
        assertTrue(comparator.compare("foo", "barbaz") < 0);
    }

    private Comparator<String> lengthComparator() {
        return Comparators.comparatorBy(new Function<String, Integer>() {

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
