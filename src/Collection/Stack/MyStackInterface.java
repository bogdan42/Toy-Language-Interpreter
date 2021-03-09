package Collection.Stack;

import java.util.Stack;

public interface MyStackInterface<T>{

    T pop();

    void push(T element);

    boolean isEmpty();

    Stack<T> getElems();
}

