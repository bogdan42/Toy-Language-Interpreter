package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {
    private Integer value;
    public IntValue(){this.value=0;}
    public IntValue(Integer val){
        this.value=val;
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    public int getValue(){return this.value;}

    public String toString(){
        return value.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof IntValue) {
            IntValue v = (IntValue) o;
            return this.value == v.getValue();
        }
        return  false;
    }

}

