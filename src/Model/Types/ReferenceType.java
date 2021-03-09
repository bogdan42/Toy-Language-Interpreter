package Model.Types;

import Model.Values.ReferenceValue;
import Model.Values.Value;


public class ReferenceType implements Type {
    private Type inner;

    public ReferenceType(){
        this.inner=null;
    }
    public ReferenceType(Type innerType){
        this.inner=innerType;
    }

    public Type getInner(){
        return this.inner;
    }

    public boolean equals(Object o){
        if(!(o instanceof ReferenceType))
            return false;
        ReferenceType obj = (ReferenceType)o;
        return inner.equals(obj.getInner());
    }
    @Override
    public Value defaultValue() {
        return new ReferenceValue(inner,0);
    }

    public String toString(){
        return "Ref("+inner.toString()+")";
    }
}
