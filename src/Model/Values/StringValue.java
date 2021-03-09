package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value{
    private String str;

    public StringValue(){this.str="";}
    public StringValue(String val){this.str=val;}
    public String getValue() {
        return str;
    }

    public void setValue(String str) {
        this.str = str;
    }

    public String toString(){
        return "'"+str.toString()+"'";
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    public boolean equals(Object o){
        if(!(o instanceof StringValue))
            return false;
        StringValue v = (StringValue)o;
        return this.str.equals(v.getValue());
    }
}
