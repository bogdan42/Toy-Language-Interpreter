package Model.Expressions;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Model.Exception.MyException.MyException;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithmeticExpression implements Expression {
    private Expression ex1;
    private Expression ex2;
    private int operation; //1 - plus,2 - minus, 3 - multiplication 4 - division

    public ArithmeticExpression(Expression ex1, Expression ex2, int operation) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.operation = operation;
    }
    public String toString(){
        String oper;
        switch (operation){
            case 1:
                oper = "+";
                break;
            case 2:
                oper="-";
                break;
            case 3:
                oper="*";
                break;
            default:
                oper="/";
                break;
        }
        return  ex1.toString()+oper+ex2.toString();
    }
    @Override
    public Value eval(MyDictionaryInterface<String, Value> tbl, HeapInterface<Value> heap) throws MyException {
        Value v1,v2;
        v1=ex1.eval(tbl, heap);

        if(v1.getType().equals(new IntType())){
            v2= ex2.eval(tbl, heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1=(IntValue)v1;
                IntValue i2=(IntValue)v2;
                int n1,n2;
                n1=i1.getValue();
                n2=i2.getValue();

                switch (operation){
                    case 1:
                        return new IntValue(n1+n2);
                    case 2:
                        return new IntValue(n1-n2);
                    case 3:
                        return new IntValue(n1*n2);
                    default:
                        if(n2==0)
                            throw new MyException("Division by zero!\n");
                        return  new IntValue(n1/n2);
                }
            }
            else
                throw  new MyException("TYPE ERROR: second operand not int\n");
        }
        else
            throw new MyException("TYPE ERROR: first operand not int\n");
    }

    @Override
    public Type typecheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type t1,t2;
        t1=ex1.typecheck(typeEnv);
        t2=ex2.typecheck(typeEnv);

        if(t1.equals(new IntType())){
            if(t2.equals(new IntType()))
                return new IntType();
            else
                throw new MyException("Second operand is not of type IntType!\nDetected in line: "+this.toString());
        }
        else
            throw new MyException("First operand is not of type IntType!\nDetected in line: "+this.toString());
    }


}

