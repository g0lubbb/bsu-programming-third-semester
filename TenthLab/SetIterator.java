import java.util.ArrayList;

public class SetIterator<T> implements MyIterator<T> {
    private final CustomSet<T> set;
    private int currentIndex;

    public SetIterator(CustomSet<T> set) {
        this.set = set;
        this.currentIndex = 0;
    }

    @Override
    public void first() {
        currentIndex = 0;
    }

    @Override
    public void next() {
        currentIndex++;
    }

    @Override
    public boolean isDone() {
        return currentIndex >= set.size();
    }

    @Override
    public T currentItem() {
        if (isDone()) {
            throw new IndexOutOfBoundsException("Iterator out of bounds");
        }
        return set.getElements().get(currentIndex);
    }
}
