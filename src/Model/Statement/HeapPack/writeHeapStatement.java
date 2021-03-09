package Model.Statement.HeapPack;

import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statement.StatementInterface;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class writeHeapStatement implements StatementInterface {
    private String var_name;
    private Expression expression;

    public writeHeapStatement(String var_name, Expression expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(!state.getSymTable().containsKey(var_name))
            throw new MyException("Variable name: "+var_name+" is not found in SymTable!\n");
        if(!(state.getSymTable().get(var_name) instanceof ReferenceValue))
            throw new MyException("Variable: "+var_name+" need to be ReferenceValue!\n");
        Integer addressReferVar = ((ReferenceValue)state.getSymTable().get(var_name)).getAddress();
        if(!state.getHeap().containsKey(addressReferVar))
            throw new MyException("Address "+addressReferVar.toString()+" does not exist in the heap!\n");
        Value exprVal = expression.eval(state.getSymTable(),state.getHeap());
        Type locType = ((ReferenceValue)state.getSymTable().get(var_name)).getLocationType();
        if(!exprVal.getType().equals(locType))
            throw new MyException("Evaluated expression type does not match with location type");
        state.getHeap().update(addressReferVar,exprVal);
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type tExp,tVar;
        tExp=expression.typecheck(typeEnv);
        tVar=typeEnv.get(var_name);

        if(tVar==null)
            throw new MyException("Variable not registered!\n Error in: "+this.toString());
        if(tVar.equals(new ReferenceType(tExp))){
            return typeEnv;
        }
        else
            throw new MyException("Inavlid writeHeap Expression\nError in: "+this.toString());
    }

    public String toString(){
        return "writeHeap("+var_name+", "+expression.toString()+")";
    }
}
