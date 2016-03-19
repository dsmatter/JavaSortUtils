package de.smatterling.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Builder class for constructing a {@link Comparator} instance by an ordered list of sub-comparators.
 * 
 * @param <T> the type of objects to compare
 */
public class ComparatorBuilder<T> {

    private List<Comparator<T>> comparators = new ArrayList<Comparator<T>>();

    /**
     * Adds a new comparator to the end of the list.
     * 
     * Comparators are ordered by priority in descending order.
     * 
     * @param comparator the comparator to add
     */
    public ComparatorBuilder<T> addComparator(Comparator<T> comparator) {
        comparators.add(comparator);
        return this;
    }

    /**
     * Adds a new comparator by using the supplied function to the end of the list.
     * 
     * Comparators are ordered by priority in descending order.
     * 
     * @param f the function to create the comparator with
     */
    public <R extends Comparable<R>> ComparatorBuilder<T> addComparatorBy(Function<T, R> f) {
        return addComparator(Comparators.comparatorBy(f));
    }

    /**
     * Adds a new comparator by using the supplied function and comparator to the end of the list.
     * 
     * Comparators are ordered by priority in descending order.
     * 
     * @param f the function to create the comparator with
     * @param comparator the comparator to use
     */
    public <R> ComparatorBuilder<T> addComparatorBy(Function<T, R> f, Comparator<R> comparator) {
        return addComparator(Comparators.comparatorBy(f, comparator));
    }

    /**
     * Adds the null comparator (considers null the "smallest" element) to the end of the list.
     * 
     * Comparators are ordered by priority in descending order.
     */
    public ComparatorBuilder<T> addNullComparator() {
        final Comparator<T> nullComparator = Comparators.nullComparator();
        return addComparator(nullComparator);
    }

    /**
     * Builds the comparator.
     * 
     * @return the built {@link Comparator} instance
     */
    public Comparator<T> build() {
        Comparator<T> result = Comparators.identityComparator();

        for (final Comparator<T> comparator : comparators) {
            result = Comparators.combineComparators(result, comparator);
        }

        return result;
    }

}
