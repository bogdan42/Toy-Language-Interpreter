package Model.Statement;


import Collection.Dictionary.MyDictionaryInterface;
import Model.Exception.MyException.MyException;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;

public class SwitchStatement  implements StatementInterface {
    Expression exp1, exp2, exp3;
    StatementInterface stmt1, stmt2, stmt3;

    public SwitchStatement(Expression exp1, Expression exp2, Expression exp3, StatementInterface stmt1, StatementInterface stmt2, StatementInterface stmt3) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }



    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StatementInterface Stm = new IfStatement(new RelationalExpression(exp1, exp2, "=="), stmt1, new IfStatement(new RelationalExpression(exp1, exp3, "=="), stmt2, stmt3));
        state.getExeStack().push(Stm);
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws MyException {
        Type texp1 = exp1.typecheck(typeEnv);
        Type texp2 = exp2.typecheck(typeEnv);
        Type texp3 = exp3.typecheck(typeEnv);
        if (texp1.equals(texp2) && texp2.equals(texp3)) {
            stmt1.typeCheck(typeEnv.cloneDict());
            stmt2.typeCheck(typeEnv.cloneDict());
            stmt3.typeCheck(typeEnv.cloneDict());
            return typeEnv;
        }
        throw new MyException("One of the expression has not the same type as the others!\nError in: " + this.toString());


    }
    public String toString() {
        return "switch(" + exp1.toString() + ")(case " + exp2.toString() + ": " + stmt1.toString() + "(case " + exp3.toString() + ": " + stmt2 + "default: " + stmt3 + ")";
    }
}

