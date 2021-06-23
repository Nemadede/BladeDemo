package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qb_vendorcredit")
public class VendorCredit {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "vendorRef")
    private Object VendorRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "line")
    private Object Line;

    @Column(name = "syncToken")
    private String SyncToken;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;

    @Column(name = "docNumber")
    private String DocNumber;
    @Column(name = "privateNote")
    private String PrivateNote;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "linkedTxn")
    private Object LinkedTxn;

    @Column(name = "globalTaxCalculation")
    private String GlobalTaxCalculation;
    @Column(name = "exchangeRate")
    private String ExchangeRate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "aPAccountRef")
    private Object APAccountRef;

    @Column(name = "txnDate")
    private Date TxnDate;

    @Column(name = "includeInAnnualTPAR")
    private Boolean IncludeInAnnualTPAR;

    @Column(name = "transactionLocationType")
    private String TransactionLocationType;

    @Column(name = "balance")
    private Double Balance;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "recurDataRef")
    private Object RecurDataRef;

    @Column(name = "totalAmt")
    private Double TotalAmt;

}
