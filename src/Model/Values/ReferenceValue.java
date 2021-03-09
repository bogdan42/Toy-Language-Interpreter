package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.Type;

public class ReferenceValue implements Value {
    private int address;
    private Type locationType;

    public ReferenceValue(Type locationType,int address) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return this.address;
    }

    public Type getLocationType(){
        return locationType;
    }
    public String toString(){
        return "("+Integer.toString(address)+","+locationType.toString()+")";
    }
    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }
}
