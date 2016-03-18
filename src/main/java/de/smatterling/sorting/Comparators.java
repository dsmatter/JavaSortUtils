package de.smatterling.sorting;

import java.util.Comparator;

/**
 * Utility functions for building and combining {@link Comparator} objects.
 */
public class Comparators {

    /**
     * Creates a comparator which compares elements using the provided comparators. The first comparator has higher
     * priority than the second (i.e. only if elements are equal under the first comparator the second comparator is
     * applied).
     * 
     * If both elements are null the comparator returns 0.
     * 
     * @param a the first comparator (higher priority)
     * @param b the second comparator
     * @return the created {@link Comparator} instance
     */
    public static <T> Comparator<T> combineComparators(final Comparator<T> a, final Comparator<T> b) {
        return new Comparator<T>() {

            @Override
            public int compare(T lhs, T rhs) {
                if (lhs == null && rhs == null) {
                    return 0;
                }

                final int r = a.compare(lhs, rhs);
                if (r == 0) {
                    return b.compare(lhs, rhs);
                }
                return r;
            }
        };
    }

    /**
     * Creates a comparator which compares elements using the provided function.
     * 
     * Elements a, b are compared by comparing f(a), f(b).
     * 
     * @param f the function
     * @return the created {@link Comparator} instance
     */
    public static <T, R extends Comparable<R>> Comparator<T> comparatorBy(final Function<T, R> f) {
        return new Comparator<T>() {

            @Override
            public int compare(T lhs, T rhs) {
                return f.apply(lhs).compareTo(f.apply(rhs));
            }
        };
    }

    /**
     * Creats a comparator from the given {@link Comparable} class.
     * 
     * @param klass the comparable class
     * @return the created {@link Comparator} instance
     */
    public static <T extends Comparable<T>> Comparator<T> fromComparable(Class<T> klass) {
        final Comparator<T> nullComparator = nullComparator();
        final Comparator<T> derivedComparator = new Comparator<T>() {

            @Override
            public int compare(T lhs, T rhs) {
                return lhs.compareTo(rhs);
            }
        };

        return combineComparators(nullComparator, derivedComparator);
    }

    /**
     * @return a comparator which considers all elements equal.
     */
    public static <T> Comparator<T> identityComparator() {
        return new Comparator<T>() {

            @Override
            public int compare(T o1, T o2) {
                return 0;
            }

        };
    }

    /**
     * @return a comparator which considers null as the "smallest" element and all other elements equal.
     */
    public static <T> Comparator<T> nullComparator() {
        return new Comparator<T>() {

            @Override
            public int compare(T lhs, T rhs) {
                if (lhs == null && rhs != null) {
                    return -1;
                } else if (lhs != null && rhs == null) {
                    return 1;
                }

                return 0;
            }

        };
    }

}
