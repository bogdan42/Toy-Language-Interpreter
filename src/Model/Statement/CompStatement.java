package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Collection.Stack.MyStack;
import Collection.Stack.MyStackInterface;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Types.Type;

public class CompStatement implements StatementInterface {
    private StatementInterface first;
    private StatementInterface second;
    public CompStatement(StatementInterface s1,StatementInterface s2){
        this.first=s1;
        this.second=s2;
    }

    public StatementInterface getFirst() {
        return first;
    }

    public void setFirst(StatementInterface first) {
        this.first = first;
    }

    public StatementInterface getSecond() {
        return second;
    }

    public void setSecond(StatementInterface second) {
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    public String toString(){
        return "("+first.toString()+";"+second.toString()+")";
    }
}
