package co.com.bancolombia.model.user.model;

import co.com.bancolombia.model.user.value.Password;
import co.com.bancolombia.model.user.value.Email;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private  final Email email;
    private final Password password;

    public User(Email email, Password password){
        //Invariantes del agregado (además de las de los VOs)
        this.id = UUID.randomUUID();
        this.email = Objects.requireNonNull(email, "INVALID_EMAIL_FORMAT");
        this.password = Objects.requireNonNull(password, "WEAK_PASSWORD");
    }

    //Getters solo de LEctura (agregado inmutable)
    public UUID id() {return id;}
    public Email email() {return email;}
    public Password password() {return password;}

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }
    @Override
    public int hashCode(){return Objects.hash(id);}

}
