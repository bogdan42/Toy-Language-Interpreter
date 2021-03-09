package Model.Statement.FilePack;
import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statement.StatementInterface;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openRFileStatement implements StatementInterface {
    private Expression expr;
    public openRFileStatement(Expression expr){
        this.expr=expr;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = expr.eval(state.getSymTable(), state.getHeap());
        if(!value.getType().equals(new StringType()))
            throw new MyException("Type of evaluated expression should be of StringType!");
        StringValue val = (StringValue)value;
        if(state.getFileTable().get(val.getValue())!=null)
            throw new MyException("File allready exists in FileTable!");
        try{
            BufferedReader buff = new BufferedReader(new FileReader(val.getValue()));
            state.getFileTable().add(val.getValue(),buff);
        }
        catch (IOException er) {
            throw new MyException("Failed to create file: "+val.getValue()+" !");
        }
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type texpr = expr.typecheck(typeEnv);
        if(texpr.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("Argument of open file stmt. not of StringType\nError in: "+this.toString());
    }

    public String toString(){
        return "openRFile("+expr.toString()+")";
    }
}
