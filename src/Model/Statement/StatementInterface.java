package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Types.Type;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws MyException;

    //type check related
    MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String,Type> typeEnv) throws MyException;
}
