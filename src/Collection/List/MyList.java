package Collection.List;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public class MyList<T> implements MyListInterface<T> {
    private ArrayList<T> list;

    public MyList(){
        this.list=new ArrayList<T>();

    }
    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean add(T e) {
        return list.add(e);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    public String toString(){
        return list.toString();
    }

    @Override
    public void setAll(List<T> toset) {
        this.list=new ArrayList<T>();
        list.addAll(toset);
    }

    @Override
    public List<T> getAll() {
        return (List<T>)list;
    }
}

