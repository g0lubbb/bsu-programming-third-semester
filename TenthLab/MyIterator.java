public interface MyIterator<T> {
    void first();
    void next();
    boolean isDone();
    T currentItem();
}
