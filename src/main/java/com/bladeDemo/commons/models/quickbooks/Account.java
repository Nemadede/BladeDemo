package com.bladeDemo.commons.models.quickbooks;


import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.Business;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Data
@Entity
@Table(name = "qb_account")
public class Account extends BaseEntity {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "syncToken")
    public String SyncToken;

    @Column(name = "active")
    public Boolean Active;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    public Object MetaData;

    @Column(name = "name")
    private String Name;

    @Column(name = "acctNum")
    private String AcctNum;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "parentRef")
    private Object ParentRef;


    @Column(name = "description")
    private String Description;

    @Column(name = "subAccount")
    private Boolean SubAccount;

    @Column(name = "classification")
    private String Classification;

    @Column(name = "fullyQualifiedName")
    private String FullyQualifiedName;

    @Column(name = "txnLocationType")
    private String TxnLocationType;

    @Column(name = "accountType")
    private String AccountType;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "taxCodeRef")
    private Object TaxCodeRef;

    @Column(name = "accountSubType")
    private String AccountSubType;

    @Column(name = "currentBalance")
    private Float CurrentBalance;

    @Column(name = "currentBalanceWithSubAccounts")
    private Float CurrentBalanceWithSubAccounts;

    @Column(name = "accountAlias")
    private String AccountAlias;

}
