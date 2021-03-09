package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.List.MyListInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStatement implements  StatementInterface {
    private Expression expression;

    public PrintStatement(Expression exp){
        this.expression=exp;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String toString(){
        return "print("+expression.toString()+")";
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyListInterface<Value> out = state.getOut();
        out.add(expression.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        if(expression.typecheck(typeEnv)==null)
            throw new MyException("Can not determine nature of expression\nError in: "+this.toString());
        return typeEnv;
    }

}
