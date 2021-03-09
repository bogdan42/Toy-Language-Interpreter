package Model.Statement;
import Collection.Dictionary.MyDictionaryInterface;
import Collection.Stack.MyStack;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class ForkStatement implements StatementInterface {
    private StatementInterface statement;

    public ForkStatement(StatementInterface statement){
        this.statement=statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symTable = state.getSymTable().cloneDict();
        return new ProgramState(new MyStack<StatementInterface>(),symTable,state.getOut(),statement,state.getFileTable(),state.getHeap());
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        statement.typeCheck(typeEnv.cloneDict());
        return typeEnv;
    }

    public String toString(){
        return "fork("+statement.toString()+")";
    }
}
