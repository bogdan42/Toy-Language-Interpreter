package Model.Expressions;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Model.Exception.MyException.MyException;
import Model.Types.Type;
import Model.Values.Value;

public interface Expression {
    Value eval(MyDictionaryInterface<String, Value> tbl, HeapInterface<Value> heap) throws MyException;

    //related to the typechecker
    Type typecheck(MyDictionaryInterface<String,Type> typeEnv) throws MyException;
}

