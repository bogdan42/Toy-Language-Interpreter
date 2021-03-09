package Model.Expressions.HeapExpressions;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class readHeapExpression implements Expression {
    private Expression expression;

    public readHeapExpression(Expression expression){
        this.expression=expression;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> tbl, HeapInterface<Value> heap) throws MyException {
        Value exprVal = expression.eval(tbl, heap);
        if(!(exprVal instanceof ReferenceValue))
            throw new MyException("Value of evaluated expression should be of ReferenceValue!\n");
        Integer address = ((ReferenceValue)exprVal).getAddress();
        if(!heap.containsKey(address))
            throw new MyException("Address of the expression is not found in the Heap!\n");
        return heap.get(address);
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type t = expression.typecheck(typeEnv);

        if(t instanceof ReferenceType)
            return ((ReferenceType)t).getInner();
        else
            throw new MyException("Operand in "+this.toString()+" should be of RefType");
    }

    public String toString(){
        return "readHeap("+expression.toString()+")";
    }
}

