package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "qb_vendor")
public class Vendor extends BaseEntity {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "syncToken")
    private String SyncToken;
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
    @Column(name = "displayName")
    private String DisplayName;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "primaryEmailAddr")
    private Object PrimaryEmailAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "otherContactInfo")
    private Object OtherContactInfo;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "apAccountRef")
    private Object APAccountRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "termRef")
    private Object TermRef;

    @Column(name = "gstin")
    private String GSTIN;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "fax")
    private Object Fax;

    @Column(name = "businessNumber")
    private String BusinessNumber;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;

    @Column(name = "hasTPAR")
    private Boolean HasTPAR;

    @Column(name = "taxReportingBasis")
    private String TaxReportingBasis;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "mobile")
    private Object Mobile;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "primaryPhone")
    private Object PrimaryPhone;

    @Column(name = "alternatePhone")
    private Boolean AlternatePhone;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Column(name = "vendor1099")
    private Boolean Vendor1099;

    @Column(name = "billRate")
    private Double BillRate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "webAddr")
    private Object WebAddr;

    @Column(name = "companyName")
    private String CompanyName;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "vendorPaymentBankDetail")
    private Object VendorPaymentBankDetail;

    @Column(name = "taxIdentifier")
    private String TaxIdentifier;
    @Column(name = "acctNum")
    private String AcctNum;
    @Column(name = "gSTRegistrationType")
    private String GSTRegistrationType;
    @Column(name = "printOnCheckName")
    private String PrintOnCheckName;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billAddr")
    private Object BillAddr;

    @Column(name = "balance")
    private Double Balance;
}
