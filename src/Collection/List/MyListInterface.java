package Collection.List;

import java.util.List;

public interface MyListInterface<T> {
    int size();
    boolean isEmpty();
    boolean add(T e);
    void clear();
    T get(int index);
    String toString();

    void setAll(List<T> toset);

    List<T> getAll();
}

