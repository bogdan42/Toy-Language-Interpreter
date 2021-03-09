package View;

import Collection.Dictionary.MyDictionary;
import Collection.List.MyList;
import Collection.Stack.MyStack;
import ControllerPack.Controller;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.HeapExpressions.readHeapExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState;
import Model.Statement.*;
import Model.Statement.FilePack.closeRFileStatement;
import Model.Statement.FilePack.openRFileStatement;
import Model.Statement.FilePack.readFileStatement;
import Model.Statement.HeapPack.NewStatement;
import Model.Statement.HeapPack.writeHeapStatement;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import RepositoryPack.Repository;
import RepositoryPack.RepositoryInterface;
import View.CommandPckg.ExitCommand;
import View.CommandPckg.RunExampleCommand;
import View.TextMenuPckg.TextMenu;
import com.sun.glass.ui.Window;

import java.util.ArrayList;

public class Interpreter {

    public static StatementInterface ex1(){
        return new CompStatement(new VariableDeclStatement("v",new IntType()),
                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    public static StatementInterface ex2() {
        return new CompStatement( new VariableDeclStatement("a",new IntType()),
                new CompStatement(new VariableDeclStatement("b",new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),3),1)),
                                new CompStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)),1)), new PrintStatement(new VariableExpression("b"))))));

    }

    public static StatementInterface ex3(){
        return new CompStatement(new VariableDeclStatement("a",new BoolType()),
                new CompStatement(new VariableDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));
    }

    public static StatementInterface ex4(){
        //example that wil declare a var
        //and will print it
        //int a; print(a);
        return  new CompStatement(new VariableDeclStatement("a",new IntType()),new PrintStatement(new VariableExpression("a")));

    }

    public static StatementInterface ex5(){
        //example that will throw an exception
        return new CompStatement(new VariableDeclStatement("v",new IntType()),
                new CompStatement(new AssignStatement("v",new ValueExpression(new BoolValue())),
                        new PrintStatement(new VariableExpression("v"))));
    }

    public static StatementInterface ex6(){
        return new CompStatement( new VariableDeclStatement("a",new IntType()),
                new CompStatement(new VariableDeclStatement("b",new BoolType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)),3),1)),
                                new CompStatement(new AssignStatement("b",new ArithmeticExpression(new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)),1)), new PrintStatement(new VariableExpression("b"))))));
    }

    public static StatementInterface ex7(){
        return new CompStatement(new VariableDeclStatement("a",new IntType()),
                new CompStatement(new VariableDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new IntValue(5))),
                                new CompStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));
    }

    public static StatementInterface ex8(){
        /*
        int a; nop; a=10+a;print(a);
         */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),new CompStatement(new NopStatement(),
                new CompStatement(new AssignStatement("a",new ArithmeticExpression(new ValueExpression(new IntValue(10)),new VariableExpression("a"),1)),
                        new PrintStatement(new VariableExpression("a")))));
    }

    public static StatementInterface ex9(){
        /*
        int a;
        nop;
        bool b;
        b=true;
        if(b) THEN (a=10;b=false) ELSE (a=-10;b=true;)
        print(a);
        print(b);
         */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),new CompStatement(new NopStatement(),new CompStatement(
                new VariableDeclStatement("b",new BoolType()), new CompStatement( new AssignStatement("b",new ValueExpression(new BoolValue(true))),
                new CompStatement(new IfStatement(new VariableExpression("b"),new CompStatement(new AssignStatement("a",new ValueExpression(new IntValue(10))),new AssignStatement("b",new ValueExpression(new BoolValue(false)))),
                        new CompStatement(new AssignStatement("a",new ValueExpression(new IntValue(-10))),new AssignStatement("b",new ValueExpression(new BoolValue(false))))),
                        new CompStatement(new PrintStatement(new VariableExpression("a")),new PrintStatement(new VariableExpression("b"))))))));
    }

    private static StatementInterface ex10(){
        return new CompStatement(new VariableDeclStatement("a",new IntType()),new CompStatement(new PrintStatement(new VariableExpression("b")),new NopStatement()));
    }

    private static StatementInterface ex11(){
        /*
        string s;
        s="ana";
        print(s);
         */
        return new CompStatement(new VariableDeclStatement("s",new StringType()),
                new CompStatement(new AssignStatement("s",new ValueExpression(new StringValue("ana"))),new PrintStatement(new VariableExpression("s"))));
    }

    private static StatementInterface ex12(){
        return new CompStatement(new VariableDeclStatement("varf",new StringType()),
                new CompStatement(new AssignStatement("varf",new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(new VariableDeclStatement("varc",new IntType()),
                                new CompStatement(new openRFileStatement(new VariableExpression("varf")),
                                        new CompStatement(new readFileStatement(new VariableExpression("varf"),"varc"),
                                                new CompStatement( new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new readFileStatement(new VariableExpression("varf"),"varc"),
                                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),new closeRFileStatement(new VariableExpression("varf"))))))))));
    }

    private static StatementInterface ex13(){
        /*
        int a;
        int b;
        a=10;
        b=30;
        print(a<30);
         */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),
                new CompStatement( new VariableDeclStatement("b",new IntType()),
                        new CompStatement( new AssignStatement("a",new ValueExpression(new IntValue(10))),
                                new CompStatement(new AssignStatement("b",new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new RelationalExpression(new VariableExpression("a"),new VariableExpression("b"),"<"))
                                ))));
    }

    private static StatementInterface ex14(){
        return new CompStatement(new VariableDeclStatement("a",new IntType()),
                new CompStatement(new VariableDeclStatement("b",new BoolType()),
                        new PrintStatement(new RelationalExpression(new VariableExpression("a"),new VariableExpression("b"),"<"))

                ));
    }

    private static StatementInterface ex15(){
        return new CompStatement(new VariableDeclStatement("v",new ReferenceType(new IntType())),
                new CompStatement(new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompStatement(new VariableDeclStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStatement(new NewStatement("a",new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new VariableExpression("a")))))));
    }

    private  static StatementInterface ex16(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        return new CompStatement(new VariableDeclStatement("v",new ReferenceType(new IntType())),
                new CompStatement(new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompStatement(new VariableDeclStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStatement(new NewStatement("a",new VariableExpression("v")),new CompStatement(new PrintStatement(new readHeapExpression(new VariableExpression("v"))),
                                        new PrintStatement(new ArithmeticExpression(new readHeapExpression(new readHeapExpression(new VariableExpression("a"))),new ValueExpression(new IntValue(5)),1)))))));
    }

    private static StatementInterface ex17(){
        return new CompStatement(new VariableDeclStatement("v",new ReferenceType(new IntType())),
                new CompStatement(new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompStatement(new writeHeapStatement("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression(new readHeapExpression(new VariableExpression("v")),new ValueExpression(new IntValue(5)),1)))));

    }

    private static StatementInterface ex18(){
        //Example: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompStatement(new VariableDeclStatement("v",new IntType()),
                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression(new VariableExpression("v"),new ValueExpression(new IntValue(1)),2)))),
                                new PrintStatement(new VariableExpression("v")))));
    }

    private  static StatementInterface ex19(){
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompStatement(new VariableDeclStatement("v",new ReferenceType(new IntType())),
                new CompStatement(new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompStatement(new VariableDeclStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStatement(new NewStatement("a",new VariableExpression("v")),new CompStatement(
                                        new NewStatement("v",new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new readHeapExpression(new readHeapExpression(new VariableExpression("a"))))
                                )))));
    }

    private static StatementInterface ex20(){
        //ref int v; new(v,20), print(rH(v)),new(v,40),print(rH(v))
        return new CompStatement(new VariableDeclStatement("v",new ReferenceType(new IntType())),
                new CompStatement(new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new readHeapExpression(new VariableExpression("v"))),
                                new CompStatement(new NewStatement("v",new ValueExpression(new IntValue(40))),new PrintStatement(new readHeapExpression(new VariableExpression("v")))))));
    }

    private static StatementInterface ex21(){
        return new CompStatement(new VariableDeclStatement("v",new IntType()),new CompStatement(new VariableDeclStatement("a",new ReferenceType(new IntType())),
                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(10))),new CompStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                        new CompStatement(new ForkStatement(new CompStatement(new writeHeapStatement("a",new ValueExpression(new IntValue(30))),
                                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(32))), new CompStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new readHeapExpression(new VariableExpression("a"))))))),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new readHeapExpression(new VariableExpression("a")))))))));
    }

    private static  StatementInterface ex22(){
        return new CompStatement(new VariableDeclStatement("a",new IntType()),new ForkStatement(new PrintStatement(new VariableExpression("s"))));
    }

    private static StatementInterface ex23(){
        return new CompStatement(new VariableDeclStatement("v",new IntType()),new CompStatement(new VariableDeclStatement("a",new ReferenceType(new IntType())),
                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(10))),new CompStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                        new CompStatement(new ForkStatement(new CompStatement(new writeHeapStatement("a",new ValueExpression(new IntValue(30))),
                                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(32))), new CompStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new readHeapExpression(new VariableExpression("a"))))))),
                                new CompStatement(new PrintStatement(new ValueExpression(new StringValue("I love FORKS :D"))),
                                        new CompStatement(new ForkStatement(new CompStatement(new writeHeapStatement("a",new ValueExpression(new IntValue(30))),
                                                new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(32))), new CompStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new readHeapExpression(new VariableExpression("a"))))))),
                                                new CompStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new readHeapExpression(new VariableExpression("a")))))))))));
    }

    private static StatementInterface ex24(){
        /*
        int a;
        bool b;
        if(b){
          int c;
          }
        else{
            int q;
        }
        print(c);
         */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),
                new CompStatement(new VariableDeclStatement("b",new BoolType()),
                        new CompStatement(new IfStatement(new VariableExpression("b"),new VariableDeclStatement("c",new IntType()),new VariableDeclStatement("q",new IntType())),
                                new PrintStatement(new VariableExpression("c"))) ));
    }

    private static StatementInterface ex25(){
        /*
        int a;
        openFile(a);
        print(aaa);
         */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),new CompStatement(new openRFileStatement(new VariableExpression("a")),
                new PrintStatement(new VariableExpression("aaaa"))));
    }

    private static StatementInterface ex26(){
        /*
        BoolType b;
        String s;
        s=file;
        openFile(s)
        readFile(s,b);
         */
        return new CompStatement(new VariableDeclStatement("b",new BoolType()),
                new CompStatement(new VariableDeclStatement("s",new StringType()),
                        new CompStatement(new AssignStatement("s",new ValueExpression(new StringValue("file"))),
                                new CompStatement(new openRFileStatement(new VariableExpression("s")),
                                        new readFileStatement(new VariableExpression("s"),"b")))));
    }

    private static StatementInterface ex27(){
        /*
        Ref int v;
        new(v,"off")
         */
        return new CompStatement(new VariableDeclStatement("v",new ReferenceType(new IntType())),new NewStatement("v",new ValueExpression(new StringValue("ss"))));
    }

    private static StatementInterface ex28(){
        /*
        closeRFile("a");
         */
        return new closeRFileStatement(new ValueExpression(new StringValue("a")));
    }

    private static StatementInterface ex29(){
        /*
        int a;
        closeRFile(a);
         */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),new closeRFileStatement(new VariableExpression("a")));
    }

    private static StatementInterface ex30(){
      /*
      int a;
      new(a,20);
       */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),new NewStatement("a",new ValueExpression(new IntValue(20))));
    }

    private static StatementInterface ex31(){
        /*
        Ref int a;
        new(a,20);
        writeHeap(a,"20");
         */
        return new CompStatement(new VariableDeclStatement("a",new ReferenceType(new IntType())),
                new CompStatement(new NewStatement("a",new ValueExpression(new IntValue(20))),
                        new writeHeapStatement("a",new ValueExpression(new StringValue("20")))));
    }

    private static StatementInterface ex32(){
        /*
        int a;
        if(a<false)
            print("ss")
        else
            nop;

         */
        return new CompStatement(new VariableDeclStatement("a",new IntType()),
                new IfStatement(new RelationalExpression(new VariableExpression("a"),new ValueExpression(new BoolValue()),"<"),new PrintStatement(new ValueExpression(new StringValue("ss"))),new NopStatement())
        );
    }

    //int a; int b; int c;
    //a=1;b=2;c=5;
    //(switch(a*10)
    // (case (b*c) : print(a);print(b))
    // (case (10) : print(100);print(200))
    // (default : print(300)));
    //print(300)
    //The final Out should be {1,2,300}

    private static StatementInterface ex33(){
        return new CompStatement(new VariableDeclStatement("a",new IntType()),
                new CompStatement(new VariableDeclStatement("b", new IntType()),
                        new CompStatement(new VariableDeclStatement("c", new IntType()),
                                new CompStatement(new AssignStatement("a", new ValueExpression( new IntValue(1))),
                                        new CompStatement( new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompStatement(new SwitchStatement(new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(10)), 3), new ArithmeticExpression(new VariableExpression("b"), new VariableExpression("c"), 3), new ValueExpression(new IntValue(10)), new CompStatement( new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))),new PrintStatement(new ValueExpression(new IntValue(200)))), new PrintStatement(new ValueExpression(new IntValue(300)))), new PrintStatement(new ValueExpression(new IntValue(300))))))))));
    }
    //bool a; int b; int c;
    //a=true;b=2;c=5;
    //(switch(a)
    // (case (b*c) : print(a);print(b))
    // (case (10) : print(100);print(200))
    // (default : print(300)));
    //print(300)
    //It should appear the typecheck error from the switch that one of the "Expressions is not the same type with the others"
    private static StatementInterface ex34(){
        return new CompStatement(new VariableDeclStatement("a",new BoolType()),
                new CompStatement(new VariableDeclStatement("b", new IntType()),
                        new CompStatement(new VariableDeclStatement("c", new IntType()),
                                new CompStatement(new AssignStatement("a", new ValueExpression( new BoolValue(true))),
                                        new CompStatement( new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompStatement(new SwitchStatement(new VariableExpression("a"), new ArithmeticExpression(new VariableExpression("b"), new VariableExpression("c"), 3), new ValueExpression(new IntValue(10)), new CompStatement( new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))),new PrintStatement(new ValueExpression(new IntValue(200)))), new PrintStatement(new ValueExpression(new IntValue(300)))), new PrintStatement(new ValueExpression(new IntValue(300))))))))));
    }
    //int a; int b; int c;
    //a=1;b=2;c=5;
    //(switch(a*11)
    // (case (b*c) : print(a);print(b))
    // (case (10) : print(100);print(200))
    // (default : print(300)));
    //print(300)
    //The final Out should be {300,300}

    private static StatementInterface ex35(){
        return new CompStatement(new VariableDeclStatement("a",new IntType()),
                new CompStatement(new VariableDeclStatement("b", new IntType()),
                        new CompStatement(new VariableDeclStatement("c", new IntType()),
                                new CompStatement(new AssignStatement("a", new ValueExpression( new IntValue(1))),
                                        new CompStatement( new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompStatement(new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompStatement(new SwitchStatement(new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(11)), 3), new ArithmeticExpression(new VariableExpression("b"), new VariableExpression("c"), 3), new ValueExpression(new IntValue(10)), new CompStatement( new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),new CompStatement(new PrintStatement(new ValueExpression(new IntValue(100))),new PrintStatement(new ValueExpression(new IntValue(200)))), new PrintStatement(new ValueExpression(new IntValue(300)))), new PrintStatement(new ValueExpression(new IntValue(300))))))))));
    }

    public static ArrayList<StatementInterface> getAll(){
        ArrayList<StatementInterface> s = new ArrayList<>();
        s.add(ex1());
        s.add(ex2());
        s.add(ex3());
        s.add(ex4());
        s.add(ex5());
        s.add(ex6());
        s.add(ex7());
        s.add(ex8());
        s.add(ex9());
        s.add(ex10());
        s.add(ex11());
        s.add(ex12());
        s.add(ex13());
        s.add(ex14());
        s.add(ex15());
        s.add(ex16());
        s.add(ex17());
        s.add(ex18());
        s.add(ex19());
        s.add(ex20());
        s.add(ex21());
        s.add(ex22());
        s.add(ex23());
        s.add(ex24());
        s.add(ex25());
        s.add(ex26());
        s.add(ex27());
        s.add(ex28());
        s.add(ex29());
        s.add(ex30());
        s.add(ex31());
        s.add(ex32());
        s.add(ex33());
        s.add(ex34());
        s.add(ex35());
        return s;
    }

    public static void main(String[] args) {

        ProgramState prog1 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex1());
        RepositoryInterface repo1 = new Repository(prog1,"log1.txt");
        Controller controller1 = new Controller(repo1);

        ProgramState prog2 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex2());
        RepositoryInterface repo2 = new Repository(prog2,"log2.txt");
        Controller controller2 = new Controller(repo2);

        ProgramState prog3 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex3());
        RepositoryInterface repo3 = new Repository(prog3,"log3.txt");
        Controller controller3 = new Controller(repo3);

        ProgramState prog4 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex4());
        RepositoryInterface repo4 = new Repository(prog4,"log4.txt");
        Controller controller4 = new Controller(repo4);

        ProgramState prog5 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex5());
        RepositoryInterface repo5 = new Repository(prog5,"log5.txt");
        Controller controller5 = new Controller(repo5);

        ProgramState prog6 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex6());
        RepositoryInterface repo6 = new Repository(prog6,"log6.txt");
        Controller controller6 = new Controller(repo6);

        ProgramState prog7 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex7());
        RepositoryInterface repo7 = new Repository(prog7,"log7.txt");
        Controller controller7 = new Controller(repo7);

        ProgramState prog8 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex8());
        RepositoryInterface repo8 = new Repository(prog8,"log8.txt");
        Controller controller8 = new Controller(repo8);

        ProgramState prog9 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex9());
        RepositoryInterface repo9 = new Repository(prog9,"log9.txt");
        Controller controller9 = new Controller(repo9);

        ProgramState prog10 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex10());
        RepositoryInterface repo10 = new Repository(prog10,"log10.txt");
        Controller controller10 = new Controller(repo10);

        ProgramState prog11 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex11());
        RepositoryInterface repo11 = new Repository(prog11,"log11.txt");
        Controller controller11 = new Controller(repo11);

        ProgramState prog12 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex12());
        RepositoryInterface repo12 = new Repository(prog12,"log12.txt");
        Controller controller12 = new Controller(repo12);

        ProgramState prog13 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex13());
        RepositoryInterface repo13 = new Repository(prog13,"log13.txt");
        Controller controller13 = new Controller(repo13);

        ProgramState prog14 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex14());
        RepositoryInterface repo14 = new Repository(prog14,"log14.txt");
        Controller controller14 = new Controller(repo14);

        ProgramState prog15 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex15());
        RepositoryInterface repo15 = new Repository(prog15,"log15.txt");
        Controller controller15 = new Controller(repo15);

        ProgramState prog16 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex16());
        RepositoryInterface repo16 = new Repository(prog16,"log16.txt");
        Controller controller16 = new Controller(repo16);

        ProgramState prog17 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex17());
        RepositoryInterface repo17 = new Repository(prog17,"log17.txt");
        Controller controller17 = new Controller(repo17);

        ProgramState prog18 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex18());
        RepositoryInterface repo18 = new Repository(prog18,"log18.txt");
        Controller controller18 = new Controller(repo18);

        ProgramState prog19 = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex19());
        RepositoryInterface repo19 = new Repository(prog19,"log19.txt");
        Controller controller19 = new Controller(repo19);

        ProgramState prog20= new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex20());
        RepositoryInterface repo20 = new Repository(prog20,"log20.txt");
        Controller controller20 = new Controller(repo20);

        ProgramState prog21= new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex21());
        RepositoryInterface repo21 = new Repository(prog21,"log21.txt");
        Controller controller21 = new Controller(repo21);

        ProgramState prog22= new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex22());
        RepositoryInterface repo22 = new Repository(prog22,"log22.txt");
        Controller controller22 = new Controller(repo22);

        ProgramState prog23= new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex23());
        RepositoryInterface repo23 = new Repository(prog23,"log23.txt");
        Controller controller23 = new Controller(repo23);

        ProgramState prog33= new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex33());
        RepositoryInterface repo33 = new Repository(prog33,"log33.txt");
        Controller controller33 = new Controller(repo33);

        ProgramState prog34= new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex34());
        RepositoryInterface repo34 = new Repository(prog34,"log34.txt");
        Controller controller34 = new Controller(repo34);

        ProgramState prog35= new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),ex35());
        RepositoryInterface repo35 = new Repository(prog35,"log35.txt");
        Controller controller35 = new Controller(repo35);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExampleCommand("1",ex1().toString(),controller1));
        menu.addCommand(new RunExampleCommand("2",ex2().toString(),controller2));
        menu.addCommand(new RunExampleCommand("3",ex3().toString(),controller3));
        menu.addCommand(new RunExampleCommand("4",ex4().toString(),controller4));
        menu.addCommand(new RunExampleCommand("5",ex5().toString(),controller5));
        menu.addCommand(new RunExampleCommand("6",ex6().toString(),controller6));
        menu.addCommand(new RunExampleCommand("7",ex7().toString(),controller7));
        menu.addCommand(new RunExampleCommand("8",ex8().toString(),controller8));
        menu.addCommand(new RunExampleCommand("9",ex9().toString(),controller9));
        menu.addCommand(new RunExampleCommand("10",ex10().toString(),controller10));
        menu.addCommand(new RunExampleCommand("11",ex11().toString(),controller11));
        menu.addCommand(new RunExampleCommand("12",ex12().toString(),controller12));
        menu.addCommand(new RunExampleCommand("13",ex13().toString(),controller13));
        menu.addCommand(new RunExampleCommand("14",ex14().toString(),controller14));
        menu.addCommand(new RunExampleCommand("15",ex15().toString(),controller15));
        menu.addCommand(new RunExampleCommand("16",ex16().toString(),controller16));
        menu.addCommand(new RunExampleCommand("17",ex17().toString(),controller17));
        menu.addCommand(new RunExampleCommand("18",ex18().toString(),controller18));
        menu.addCommand(new RunExampleCommand("19",ex19().toString(),controller19));
        menu.addCommand(new RunExampleCommand("20",ex20().toString(),controller20));
        menu.addCommand(new RunExampleCommand("21",ex21().toString(),controller21));
        menu.addCommand(new RunExampleCommand("22",ex22().toString(),controller22));
        menu.addCommand(new RunExampleCommand("23",ex23().toString(),controller23));
        menu.addCommand(new RunExampleCommand("33",ex33().toString(),controller33));
        menu.addCommand(new RunExampleCommand("34",ex34().toString(),controller34));
        menu.addCommand(new RunExampleCommand("35",ex35().toString(),controller35));
        menu.show();
    }
}
