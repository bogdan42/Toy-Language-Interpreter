package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type {
    @Override
    public Value defaultValue() {
        return new StringValue();
    }

    public String toString(){
        return "string";
    }

    public boolean equals(Object o){
        return o instanceof StringType;
    }
}
