package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qb_payment")
public class Payment extends BaseEntity {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "totalAmt")
    private Double TotalAmt;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerRef")
    private Object CustomerRef;

    @Column(name = "syncToken")
    private String SyncToken;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;

    @Column(name = "privateNote")
    private String PrivateNote;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "paymentMethodRef")
    private Object PaymentMethodRef;

    @Column(name = "unappliedAmt")
    private Double UnappliedAmt;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "depositToAccountRef")
    private Object DepositToAccountRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "exchangeRate")
    private Object ExchangeRate;

    @Column(name = "txnSource")
    private String TxnSource;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "line")
    private Object Line;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "aRAccountRef")
    private Object ARAccountRef;

    @Column(name = "txnDate")
    private Date TxnDate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "creditCardPayment")
    private Object CreditCardPayment;

    @Column(name = "transactionLocationType")
    private String TransactionLocationType;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Column(name = "paymentRefNum")
    private String PaymentRefNum;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "taxExemptionRef")
    private Object TaxExemptionRef;

}
