package Model.Exception.MyException;

public class MyException extends RuntimeException {
    private String msg;
    public MyException(String msg){
        this.msg=msg;
    }

    public String getMessage(){
        return this.msg;
    }
}

