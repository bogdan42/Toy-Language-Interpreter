package Model.Expressions;


import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Model.Exception.MyException.MyException;
import Model.Types.Type;
import Model.Values.Value;

public class VariableExpression implements Expression {
    private String keyId;

    public VariableExpression(String id){
        this.keyId=id;
    }
    @Override
    public Value eval(MyDictionaryInterface<String, Value> tbl, HeapInterface<Value> heap) throws MyException {
        if(tbl.get(keyId)==null)
            throw new MyException("Variable "+keyId+" is not declared!");
        return tbl.get(keyId);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        return typeEnv.get(keyId);
    }


    public String toString(){
        return keyId;
    }
}
