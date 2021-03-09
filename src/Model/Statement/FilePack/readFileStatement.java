package Model.Statement.FilePack;

import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statement.StatementInterface;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;



public class readFileStatement implements StatementInterface {
    private Expression expression;
    private String variableName;

    public readFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        if(state.getSymTable().get(variableName)==null)
            throw new MyException("Variable "+variableName+" is not defined!");
        if(!state.getSymTable().get(variableName).getType().equals(new IntType()))
            throw new MyException("Type of "+variableName+" must be IntType");
        Value exprVal =expression.eval(state.getSymTable(), state.getHeap());
        if(!exprVal.getType().equals(new StringType()))
            throw new MyException("Expression has to be of Type String");
        String tableKey = ((StringValue)exprVal).getValue();
        BufferedReader file = state.getFileTable().get(tableKey);
        if(file==null)
            throw new MyException("File "+tableKey+" not found in the FileTable");
        try {
            String line = file.readLine();
            if(line != null){
                state.getSymTable().update(variableName,new IntValue(Integer.parseInt(line)));
            }
            else
                state.getSymTable().update(variableName,new IntValue());

        }
        catch (IOException er){
            throw new MyException("Failed to read from file!");
        }
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type expType,vartype;
        expType=expression.typecheck(typeEnv);
        vartype=typeEnv.get(variableName);

        if(expType.equals(new StringType())){
            if(vartype==null)
                throw new MyException("Variable does not exist!\nError in: "+this.toString());
            if(vartype.equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("Type of variable not int Type\nError in: "+this.toString());
        }else
            throw new MyException("Expression should be of StringType\nError in: "+this.toString());
    }

    public String toString(){
        return "openRFile("+expression.toString()+","+variableName+")";
    }
}
