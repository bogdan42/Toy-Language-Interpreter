package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Types.Type;

public class NopStatement implements StatementInterface {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    public String toString(){
        return "nop";
    }
}
