package co.com.bancolombia.model.user.value;

import java.util.Objects;

public class Password {
    private final String value;

    public Password(String value){
        if (value == null || value.length() <8 ){
             throw new IllegalArgumentException("WEAK_PASSWORD");
        }
        this.value = value;
    }

    public String value(){return value;}

    public boolean equals(Object o){
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass()) return false;
        Password other = (Password) o;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode(){return Objects.hash(value); }
    @Override
    public String toString(){return "Password{***}"; }



}
