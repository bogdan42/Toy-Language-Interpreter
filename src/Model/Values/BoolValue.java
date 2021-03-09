package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;


public class BoolValue implements Value {
    private Boolean val;
    public BoolValue(){this.val=false;}
    @Override
    public Type getType() {
        return new BoolType();
    }

    public BoolValue(Boolean val){
        this.val=val;
    }

    public Boolean getValue(){
        return val;
    }

    public String toString(){
        return val.toString();
    }

    public boolean equals(Object o){
        if(o instanceof BoolValue){
            BoolValue v = (BoolValue)o;
            return this.val==v.getValue();
        }
        return false;
    }

}
