package Model.Expressions;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Model.Exception.MyException.MyException;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExpression implements Expression {
    Value val;

    public ValueExpression(Value val){
        this.val=val;
    }
    @Override
    public Value eval(MyDictionaryInterface<String, Value> tbl, HeapInterface<Value> heap) throws MyException {
        return val;
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        return val.getType();
    }


    public String toString(){
        return  val.toString();
    }
}
