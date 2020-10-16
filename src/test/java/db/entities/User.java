package db.entities;

import lombok.Data;

import javax.persistence.*;
//hbnt
@Data
@Entity
@Table(name = "akravchenko")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name ;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birthdate")
    private String birthdate;


}
