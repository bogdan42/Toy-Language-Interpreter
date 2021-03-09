package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Stack.MyStackInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStatement implements StatementInterface {
    private Expression exp;
    private StatementInterface thenStatement;
    private StatementInterface elseStatement;

    public Expression getExp() {
        return exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

    public StatementInterface getThenStatement() {
        return thenStatement;
    }

    public void setThenStatement(StatementInterface thenStatement) {
        this.thenStatement = thenStatement;
    }

    public StatementInterface getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(StatementInterface elseStatement) {
        this.elseStatement = elseStatement;
    }

    public IfStatement(Expression exp, StatementInterface thenStatement, StatementInterface elseStatement) {
        this.exp = exp;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        Value val = exp.eval(state.getSymTable(), state.getHeap());

        if(val.getType().equals(new BoolType())){
            if(val.equals(new BoolValue(true))){
                stack.push(thenStatement);
            }
            else stack.push(elseStatement);
        }
        else
            throw new MyException("Conditional expression is not a boolean!\n");
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type texp = exp.typecheck(typeEnv);
        if(texp.equals(new BoolType())){
            thenStatement.typeCheck(typeEnv.cloneDict());
            elseStatement.typeCheck(typeEnv.cloneDict());
            return typeEnv;
        }
        throw new MyException("If condition should be of BoolType\nError in: "+this.toString());
    }

    public String toString(){
        return "IF("+ exp.toString()+") THEN(" +thenStatement.toString()
                +")ELSE("+elseStatement.toString()+")";
    }
}
