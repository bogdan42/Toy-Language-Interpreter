package Model;

import Collection.Dictionary.MyDictionary;
import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.Heap;
import Collection.Heap.HeapInterface;
import Collection.List.MyListInterface;
import Collection.Stack.MyStackInterface;
import Model.Exception.MyException.MyException;
import Model.Statement.StatementInterface;
import Model.Values.Value;

import java.io.BufferedReader;
import java.util.List;

public class ProgramState {

    private MyStackInterface<StatementInterface> exeStack;
    private MyDictionaryInterface<String, Value> symTable;
    private MyListInterface<Value> out;
    private StatementInterface originalProgram;
    private MyDictionaryInterface<String, BufferedReader> fileTable;
    private HeapInterface<Value> heap;
    private static Integer GlobalMaxThreadID=0;
    private Integer threadID;

    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symTable, MyListInterface<Value> out, StatementInterface originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram; //should be  a deep copy..
        this.fileTable = new MyDictionary<>();
        this.heap=new Heap<Value>();
        this.threadID=generateThreadID();
        exeStack.push(originalProgram);
    }

    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symTable, MyListInterface<Value> out, StatementInterface originalProgram, MyDictionaryInterface<String, BufferedReader> fileTable, HeapInterface<Value> heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        exeStack.push(originalProgram);
        this.threadID=generateThreadID();
    }

    public MyStackInterface<StatementInterface> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyStackInterface<StatementInterface> exeStack) {
        this.exeStack = exeStack;
    }

    public MyDictionaryInterface<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyDictionaryInterface<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyListInterface<Value> getOut() {
        return out;
    }

    public void setOut(MyListInterface<Value> out) {
        this.out = out;
    }

    public StatementInterface getOriginalProgram() {
        return originalProgram;
    }

    public void setHeap(HeapInterface<Value> h){this.heap=h;}

    public void setOriginalProgram(StatementInterface originalProgram) {
        this.originalProgram = originalProgram;
    }

    public HeapInterface<Value> getHeap(){return this.heap;}
    public MyDictionaryInterface<String, BufferedReader> getFileTable(){
        return fileTable;
    }
    public String toString(){
        return "Thread ID:\n"+threadID+"\n"+"ExeStack:\n"+exeStack+"\nSymTable:\n"+symTable+"\nOut:\n"+out+"\nFileTable:\n"+fileTable+"\n"+
                "Heap:\n"+heap+"\n";
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep(){
        if(exeStack.isEmpty())
            throw new MyException("Execution Stack is Empty!");
        StatementInterface currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    private synchronized Integer generateThreadID(){
        GlobalMaxThreadID++;
        return GlobalMaxThreadID;
    }

    public Integer getThreadID(){
        return this.threadID;
    }
}

