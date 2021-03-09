package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Stack.MyStack;
import Collection.Stack.MyStackInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;

public class WhileStatement implements StatementInterface{
    private Expression whileCondition;
    private StatementInterface whileBody;

    public WhileStatement(Expression whileCondition, StatementInterface whileBody) {
        this.whileCondition = whileCondition;
        this.whileBody = whileBody;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(!whileCondition.eval(state.getSymTable(),state.getHeap()).getType().equals(new BoolType()))
            throw new MyException("Evaluation of while condition: "+whileCondition.toString()+" should be of BoolType!\n");
        Boolean b =((BoolValue)whileCondition.eval(state.getSymTable(),state.getHeap())).getValue();
        MyStackInterface<StatementInterface> stack;
        if(b){
            stack = state.getExeStack();
            stack.push(this);
            state.setExeStack((MyStack<StatementInterface>)stack);
            whileBody.execute(state);
        }
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type condt = whileCondition.typecheck(typeEnv);

        if(condt.equals(new BoolType())){
            whileBody.typeCheck(typeEnv.cloneDict());
        }else
            throw new MyException("While Condition should be of BoolType\nError in: "+this.toString());
        return typeEnv;
    }

    public String toString(){
        return "while("+whileCondition.toString()+"){ "+whileBody.toString()+" }";
    }
}
