package org.free.proj.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false)
    @Email(message = "*Introduce un email correcto")
    @NotEmpty(message = "*Introduce un email")
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 5, message = "*La contraseña tiene que tener 5 caracteres como minimo")
    @NotEmpty(message = "*Introduce un contraseña")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Introduzca su nombre")
    private String name;

    @Column(name = "lastname")
    @NotEmpty(message = "*Introduzca su apellido")
    private String lastname;

    @Column(name="account_active")
    private int active;

    @OneToOne(cascade = CascadeType.REFRESH)
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
