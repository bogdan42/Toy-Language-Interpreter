package Collection.Heap;

import Collection.Dictionary.MyDictionaryInterface;

import java.util.Map;

public interface HeapInterface<Value> extends MyDictionaryInterface<Integer, Value> {
    Integer generateAddress();
    void setContent(Map<Integer,Value> newH);

    Map<Integer,Value> getContent();
}

