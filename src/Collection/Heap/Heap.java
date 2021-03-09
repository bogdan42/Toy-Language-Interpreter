package Collection.Heap;

import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Heap<Value> implements HeapInterface<Value> {
    private HashMap<Integer,Value> heap;
    private ConcurrentHashMap<Integer,Value> heap2;
    private Semaphore semaphore;
    public Heap(){
        this.heap = new HashMap<>();
        this.heap2 = new ConcurrentHashMap<>();
        this.semaphore = new Semaphore(1);
    }


    @Override
    public Value get(Integer integer) {
        return heap2.get(integer);
    }

    @Override
    public void add(Integer k, Value v) {
        heap.put(k,v);
        heap2.put(k,v);
    }

    @Override
    public int size() {
        return heap2.size();
    }

    @Override
    public void setDict(HashMap<Integer, Value> k) {
        try {
            semaphore.acquire();
            this.heap = k;
            this.heap2=new ConcurrentHashMap<>();
            for(Integer key: k.keySet())
                heap2.put(key,k.get(key));
            semaphore.release();
        }
        catch (InterruptedException e){
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public void remove(Integer k) {
        heap.remove(k);
        heap2.remove(k);
    }

    @Override
    public boolean containsKey(Integer integer) {
        return heap2.containsKey(integer);
    }

    @Override
    public Collection<Value> values() {
        return heap2.values();
    }

    @Override
    public boolean containsValue(Value val) {
        return heap2.containsValue(val);
    }

    @Override
    public Set<Integer> keySet() {
        return heap2.keySet();
    }

    @Override
    public MyDictionaryInterface<Integer, Value> cloneDict() {
        return null;
    }


    @Override
    public void update(Integer k, Value v) {
        heap.replace(k,v);
        heap2.replace(k,v);
    }

    @Override
    public synchronized Integer generateAddress() {
        try {
            semaphore.acquire();
            while (true) {
                int k = new Random().nextInt(9999);
                if (!heap.containsKey(k)){
                    semaphore.release();
                    return k;}
            }
        }
        catch (InterruptedException e){
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public void setContent(Map<Integer,Value> newH) {
        try {
            semaphore.acquire();
            this.heap = (HashMap<Integer, Value>) newH;
            this.heap2=new ConcurrentHashMap<>();
            for(Integer key: newH.keySet())
                heap2.put(key,newH.get(key));
            semaphore.release();
        }
        catch (InterruptedException e){
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public Map<Integer, Value> getContent() {

        return (Map<Integer,Value>) heap2;
    }

    public String toString(){
        String heapVals="";
        for(Integer key:heap.keySet())
            heapVals=heapVals.concat("["+Integer.toString(key)+"->"+heap.get(key).toString()+"] ");
        return heapVals;
    }
}

