package com.bladeDemo.commons.models;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence. *;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(name = "full_name")
//    private String fullName;

    @Column(name = "fname")
    private String firstName;

    @Column(name = "lname")
    private String lastName;


    private String token;
    private String email;
    private String password;
    private String salt;
    private Boolean active;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;
}
