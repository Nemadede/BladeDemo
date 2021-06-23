package com.bladeDemo.commons.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_email")
    private String businessEmail;
    private Boolean active;
    private String country;

    @Column(name = "connector_id")
    private Integer connectorId;

    @Column(name = "pull_status")
    private Boolean pullStatus;

    @Column(name = "default_currency")
    private String defaultCurrency;

    @Column(name = "default_currency_symbol")
    private String defaultCurrencySymbol;

    @Column(name = "is_multiCurrency")
    private String isMultiCurrency;

    @Column(name = "connection_date")
    private Timestamp connectionDate;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;

}
