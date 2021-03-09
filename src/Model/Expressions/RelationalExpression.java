package Model.Expressions;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Model.Exception.MyException.MyException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExpression implements Expression {
    private Expression ex1;
    private Expression ex2;
    private String op; // < , <= , ....

    public RelationalExpression(Expression ex1, Expression ex2, String op) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.op = op;
    }

    public String toString(){
        return ex1.toString()+op+ex2.toString();
    }
    @Override
    public Value eval(MyDictionaryInterface<String, Value> tbl, HeapInterface<Value> heap) throws MyException {
        Value val1 = ex1.eval(tbl, heap);
        if(!val1.getType().equals(new IntType()))
            throw new MyException("First expression  evaluation is not of type Int!");
        Value val2=ex2.eval(tbl, heap);
        if(!val2.getType().equals(new IntType()))
            throw new MyException("Second expression evaluation is not of type Int!");
        IntValue v1 = (IntValue)val1;
        IntValue v2 = (IntValue)val2;
        switch (op){
            case "<":
                return new BoolValue(v1.getValue()<v2.getValue());
            case "<=":
                return new BoolValue(v1.getValue()<=v2.getValue());
            case "==":
                return new BoolValue(v1.getValue()==v2.getValue());
            case "!=":
                return new BoolValue(v1.getValue()!=v2.getValue());
            case ">":
                return new BoolValue(v1.getValue()>v2.getValue());
            case ">=":
                return new BoolValue(v1.getValue()>=v2.getValue());
            default:
                throw new MyException("Invalid relational expression operator!");
        }
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1=ex1.typecheck(typeEnv);
        t2=ex2.typecheck(typeEnv);
        if(t1.equals(new IntType())) {
            if (t2.equals(new IntType()))
                return new BoolType();
            else
                throw new MyException("Second operand not of IntType\nError in: " + this.toString());
        }
        else
            throw new MyException("First operand not of IntType\nError in: "+this.toString());
    }


}
