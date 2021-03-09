package Collection.Dictionary;

import Model.Exception.MyException.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;


public class MyDictionary<Key,Value> implements MyDictionaryInterface<Key,Value> {

    private HashMap<Key,Value> dict;//initially used
    //but now concurrentHashMap is better
    private ConcurrentHashMap<Key,Value> d;
    private Semaphore sem;

    public void setDict(HashMap<Key,Value> k){
        try {
            sem.acquire();

            this.dict = k;
            this.d = new ConcurrentHashMap<>();
            for (Key key : k.keySet()) {
                d.put(key, k.get(key));
            }

            sem.release();
        }
        catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }
    }
    public MyDictionary(){
        this.dict=new HashMap<Key,Value>();
        this.d=new ConcurrentHashMap<>();
        this.sem = new Semaphore(1);
    }
    @Override
    public Value get(Key key) {
        return d.get(key);
        //return this.dict.get(key);
    }

    @Override
    public void add(Key k, Value v) {
        this.d.put(k,v);
        this.dict.put(k,v);
    }

    @Override
    public int size() {
        return d.size();
        //return dict.size();
    }

    @Override
    public void remove(Key k) {
        dict.remove(k);
        d.remove(k);
    }

    @Override
    public boolean containsKey(Key key) {
        return d.containsKey(key);
        // return dict.containsKey(key);
    }

    @Override
    public Collection<Value> values() {
        return d.values();
    }

    @Override
    public boolean containsValue(Value val) {
        return d.containsValue(val);
    }

    @Override
    public Set<Key> keySet() {
        return d.keySet();
    }

    @Override
    public MyDictionaryInterface<Key, Value> cloneDict() {
        try {
            sem.acquire();
            MyDictionaryInterface<Key, Value> clone = new MyDictionary<Key, Value>();
            HashMap<Key, Value> nv = new HashMap<>();
            for (Key k : d.keySet())
                nv.put(k, dict.get(k));
            clone.setDict(nv);
            sem.release();
            return clone;
        }
        catch (InterruptedException e){
            throw new MyException(e.getMessage());
        }
    }

    public String  toString(){
        return this.d.toString();
    }

    @Override
    public void update(Key k,Value v){
        this.dict.replace(k,v);
        this.d.replace(k,v);
    }
}



