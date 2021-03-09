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
import java.io.IOException;

public class closeRFileStatement implements StatementInterface {
    private Expression expression;

    public closeRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value val = expression.eval(state.getSymTable(), state.getHeap());
        if(!val.getType().equals(new StringType()))
            throw new MyException("Error in CloseRFile ---- Expr. Type noy of TYPE:String");
        String fileKey = ((StringValue)val).getValue();
        BufferedReader file = state.getFileTable().get(fileKey);
        if(file==null)
            throw new MyException("File "+fileKey+" not registered in FileTable!");
        try{
            file.close();
            state.getFileTable().remove(fileKey);
        }
        catch (IOException er) {
            throw new MyException("Failed to close file!");
        }
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type extype=expression.typecheck(typeEnv);

        if(extype.equals(new StringType()))
            return typeEnv;
        throw new MyException("File name should be of StringType\nError in: "+this.toString());
    }

    public String toString(){
        return "closeRFile("+expression.toString()+")";
    }
}
