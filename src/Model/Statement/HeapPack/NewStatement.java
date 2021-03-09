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

public class NewStatement implements StatementInterface {
    private String varName;
    private Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        if(!state.getSymTable().containsKey(varName))
            throw new MyException("Variable: "+varName+" can not be found in SymTable!\n");
        if(!(state.getSymTable().get(varName).getType() instanceof ReferenceType))
            throw new MyException("Associated type to: "+varName+" should be of ReferenceType!\n");
        Value expr = expression.eval(state.getSymTable(), state.getHeap());
        ReferenceValue refVal = ((ReferenceValue)state.getSymTable().get(varName));
        Type refType = ((ReferenceType)refVal.getType()).getInner();
        if(!expr.getType().equals(refType))
            throw new MyException("Referenced type and type of the expression need to match!\n");
        Integer address = state.getHeap().generateAddress();
        state.getHeap().add(address,expr);
        state.getSymTable().update(varName,new ReferenceValue(refType,address));
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type tvar,texp;
        tvar=typeEnv.get(varName);
        if(tvar==null)
            throw new MyException("Var cant be found!\nError in: "+this.toString());
        texp=expression.typecheck(typeEnv);

        if(tvar.equals(new ReferenceType(texp))){
            return typeEnv;
        }
        else
            throw new MyException("Right hand side and left side have different types\nMake sure also that expression is of type RefType\nError in: "+this.toString());

    }

    public String toString(){
        return "new("+varName+","+expression.toString()+")";
    }
}
