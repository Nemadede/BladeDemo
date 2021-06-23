package com.bladeDemo.commons.models;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_forms")
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String country;
    private String uuid;


    @Column(name="country_iso")
    private String countryISO;

    @Column(name="fname")
    private String firstName;

    @Column(name="lname")
    private String lastName;

    @Column(name="company_name")
    private String companyName;

    @Column(name = "phone")
    private String phoneNumber;

    private Boolean status;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;

    @org.hibernate.annotations.ForeignKey(name = "admin_id")
    private Integer admin_id;




}
