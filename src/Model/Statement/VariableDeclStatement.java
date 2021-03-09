package Model.Statement;

import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.ReferenceValue;
import Model.Values.Value;

import java.util.ArrayList;

public class VariableDeclStatement implements StatementInterface {
    private String name;
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public VariableDeclStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyDictionaryInterface<String, Value> symTable = state.getSymTable();
        if(symTable.containsKey(name))
            throw new MyException("This variable has been already declared!\n");
        Value v=null;
        if(type.equals(new BoolType())){
            v= new BoolType().defaultValue();
        }
        else if(type.equals(new IntType()))
            v=new IntType().defaultValue();
        else if(type.equals(new StringType()))
            v = new StringType().defaultValue();
        else if(type instanceof ReferenceType){
            Type ref= ((ReferenceType)type).getInner();
            v= new ReferenceType(ref).defaultValue();
        }

        symTable.add(name,v);
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        typeEnv.add(name,type);
        return typeEnv;
    }

    public String toString(){
        if(type.equals(new BoolType())){
            return "bool "+name;
        }
        else if (type.equals(new StringType()))
            return "string "+name;
        else if (type instanceof IntType)
            return "int "+name;
        else if (type instanceof ReferenceType)
            return "Ref "+((ReferenceType)type).getInner().toString()+" "+name;
        return "NULL "+name;
    }

}
