package Model.Expressions;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Model.Exception.MyException.MyException;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements Expression {

    private Expression ex1;
    private Expression ex2;
    private int operation;//1 --and 2--or

    public LogicExpression(Expression e1,Expression e2,int op){
        this.ex1=e1;
        this.ex2=e2;
        this.operation=op;
    }
    @Override
    public Value eval(MyDictionaryInterface<String, Value> tbl, HeapInterface<Value> heap) throws MyException {
        Value v1,v2;
        v1=ex1.eval(tbl, heap);
        if(v1.getType().equals(new BoolType())){
            v2=ex2.eval(tbl, heap);
            if(v2.getType().equals(new BoolType())){
                BoolValue b1=(BoolValue)v1;
                BoolValue b2=(BoolValue)v2;
                boolean va1=b1.getValue();
                boolean va2=b2.getValue();
                if(operation==1){
                    return new BoolValue(va1&&va2);
                }
                return new BoolValue(va1||va2);
            }
            else
                throw  new MyException("TYPE ERROR: second operand is not bool\n");
        }
        else
            throw new MyException("TYPE ERROR: first operand not bool\n");

    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type t1,t2;

        t1=ex1.typecheck(typeEnv);
        t2=ex2.typecheck(typeEnv);

        if(t1.equals(new BoolType())){
            if(t2.equals(new BoolType()))
                return new BoolType();
            else
                throw new MyException("Second operand is not of type BoolType\nDetected in line: "+this.toString());
        }
        else
            throw new MyException("First operand not of type BoolType\nDetected in line: "+this.toString());
    }

    @Override
    public String toString() {
        String s = ex1.toString();
        if(operation==1)
            s=s.concat(" AND ");
        else
            s=s.concat(" OR ");
        return s.concat(ex2.toString());
    }
}

