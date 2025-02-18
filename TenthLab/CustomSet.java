import java.util.ArrayList;
import java.util.Objects;

public class CustomSet<T> implements Aggregate<T> {
    private final ArrayList<T> elements;

    public CustomSet() {
        this.elements = new ArrayList<>();
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void clear() {
        elements.clear();
    }

    public boolean add(T element) {
        if (!elements.contains(element)) {
            elements.add(element);
            return true;
        }
        return false;
    }

    public boolean remove(T element) {
        return elements.remove(element);
    }

    public boolean addAll(CustomSet<T> otherSet) {
        boolean modified = false;
        for (T element : otherSet.elements) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    public CustomSet<T> union(CustomSet<T> otherSet) {
        CustomSet<T> resultSet = new CustomSet<>();
        resultSet.addAll(this);
        resultSet.addAll(otherSet);
        return resultSet;
    }

    public CustomSet<T> intersection(CustomSet<T> otherSet) {
        CustomSet<T> resultSet = new CustomSet<>();
        for (T element : elements) {
            if (otherSet.elements.contains(element)) {
                resultSet.add(element);
            }
        }
        return resultSet;
    }

    public CustomSet<T> difference(CustomSet<T> otherSet) {
        CustomSet<T> resultSet = new CustomSet<>();
        for (T element : elements) {
            if (!otherSet.elements.contains(element)) {
                resultSet.add(element);
            }
        }
        return resultSet;
    }

    public ArrayList<T> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomSet<?> customSet = (CustomSet<?>) o;
        return elements.equals(customSet.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public String toString() {
        return "CustomSet{" +
                "elements=" + elements +
                '}';
    }

    @Override
    public MyIterator<T> createIterator() {
        return new SetIterator<>(this);
    }
}
