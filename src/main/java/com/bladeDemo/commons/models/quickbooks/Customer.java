package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qb_customer")
public class Customer {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "syncToken")
    private String SyncToken;

    @Column(name = "displayName")
    private String DisplayName;

    @Column(name = "title")
    private String Title;

    @Column(name = "givenName")
    private String GivenName;

    @Column(name = "middleName")
    private String MiddleName;

    @Column(name = "suffix")
    private String Suffix;

    @Column(name = "familyName")
    private String FamilyName;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "primaryEmailAddr")
    private Object PrimaryEmailAddr;

    @Column(name = "resaleNum")
    private String ResaleNum;

    @Column(name = "secondaryTaxIdentifier")
    private String SecondaryTaxIdentifier;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "aRAccountRef")
    private Object ARAccountRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "defaultTaxCodeRef")
    private Object DefaultTaxCodeRef;

    @Column(name = "preferredDeliveryMethod")
    private String PreferredDeliveryMethod;

    @Column(name = "gstin")
    private String GSTIN;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "salesTermRef")
    private Object SalesTermRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerTypeRef")
    private Object CustomerTypeRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "fax")
    private Object Fax;

    @Column(name = "businessNumber")
    private String BusinessNumber;

    @Column(name = "billWithParent")
    private Boolean BillWithParent;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "mobile")
    private Object Mobile;

    @Column(name = "job")
    private Boolean Job;

    @Column(name = "balanceWithJobs")
    private Double BalanceWithJobs;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "primaryPhone")
    private Object PrimaryPhone;

    @Column(name = "openBalanceDate")
    private Date OpenBalanceDate;
    @Column(name = "taxable")
    private Boolean Taxable;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "alternatePhone")
    private Object AlternatePhone;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "parentRef")
    private Object ParentRef;

    @Column(columnDefinition="TEXT", name = "motes" )
    private String Motes;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "webAddr")
    private Object WebAddr;

    @Column(name = "active")
    private Boolean Active;
    @Column(name = "balance")
    private Double Balance;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "shipAddr")
    private Object ShipAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "paymentMethodRef")
    private Object PaymentMethodRef;

    @Column(name = "isProject")
    private Boolean IsProject;
    @Column(name = "companyName")
    private  String CompanyName;
    @Column(name = "primaryTaxIdentifier")
    private String PrimaryTaxIdentifier;
    @Column(name = "gstRegistrationType")
    private String GSTRegistrationType;

    @Column(name = "printOnCheckName")
    private String PrintOnCheckName;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billAddr")
    private Object BillAddr;

    @Column(name = "fullyQualifiedName")
    private String FullyQualifiedName;
    @Column(name = "level")
    private Integer Level;
    @Column(name = "taxExemptionReasonId")
    private Integer TaxExemptionReasonId;
}
