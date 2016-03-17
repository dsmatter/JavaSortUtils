# Java Sort Utils

Provides a builder for constructing `Comparator` instances from other comparators as well as related utility functions.

# Example

```java
final Comparator<Person> personComparator = new ComparatorBuilder<Person>()
        .addComparatorBy(Person::getYearOfBirth)
        .addComparatorBy(Person::getLastName)
        .addComparatorBy(Person::getFirstName)
        .build();

people.sort(personComparator);
```
