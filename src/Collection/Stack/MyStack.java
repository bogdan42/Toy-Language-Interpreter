package Collection.Stack;

import java.util.Stack;

public class MyStack<T> implements MyStackInterface<T> {
    private Stack<T> elems;

    public MyStack(){
        this.elems=new Stack<T>();
    }
    @Override
    public T pop() {
        return this.elems.pop();
    }

    @Override
    public void push(T element) {
        this.elems.push(element);
    }

    public String toString(){
        return elems.toString();
    }

    @Override
    public boolean isEmpty(){
        return elems.isEmpty();
    }

    @Override
    public Stack<T> getElems() {
        return elems;
    }
}
