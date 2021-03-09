package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Stack.MyStackInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStatement implements StatementInterface {
    private String id;
    private Expression exp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expression getExp() {
        return exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

    public  AssignStatement(String id, Expression exp){
        this.id=id;
        this.exp=exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        MyDictionaryInterface<String, Value> symTable = state.getSymTable();

        Value val = exp.eval(symTable, state.getHeap());

        if(symTable.containsKey(id)){
            Type typeId = (symTable.get(id)).getType();
            if(val.getType().equals(typeId)){
                symTable.update(id,val);
            }
            else
                throw new MyException("declared type of variable "+id+" and type of the assigned expression do not match");
        }
        else
            throw new MyException("the used variable" +id + " was not declared before");
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.get(id);
        if(typeVar==null)
            throw new MyException("Variable can not be found!\nError in: "+this.toString());
        Type typeExpr = exp.typecheck(typeEnv);

        if(typeVar.equals(typeExpr))
            return typeEnv;
        else
            throw new MyException("Right hand side not of the same type with left hand side!\nIn the assignment "+this.toString());
    }

    public String toString(){
        return id+"="+exp.toString();
    }
}
