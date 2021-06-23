package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="qb_refundreceipt")
public class RefundReceipt extends BaseEntity {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "depositToAccountRef")
    private Object DepositToAccountRef;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "line")
    private Object Line;

    @Column(name = "syncToken")
    private String SyncToken;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "paymentRefNum")
    private Object PaymentRefNum;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billEmail")
    private Object BillEmail;

    @Column(name = "txnDate")
    private Date TxnDate;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customField")
    private Object CustomField;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "classRef")
    private Object ClassRef;

    @Column(name = "printStatus")
    private String PrintStatus;

    @Column(name = "checkPayment")
    private String CheckPayment;

    @Column(name = "txnSource")
    private String TxnSource;

    @Column(name = "globalTaxCalculation")
    private String GlobalTaxCalculation;

    @Column(name = "transactionLocationType")
    private String TransactionLocationType;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Column(name = "docNumber")
    private String DocNumber;

    @Column(name = "privateNote")
    private String PrivateNote;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerMemo")
    private Object CustomerMemo;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "creditCardPayment")
    private Object CreditCardPayment;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerRef")
    private Object CustomerRef;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "txnTaxDetail")
    private Object TxnTaxDetail;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "paymentMethodRef")
    private Object PaymentMethodRef;

    @Column(name = "exchangeRate")
    private Double ExchangeRate;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "shipAddr")
    private Object ShipAddr;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "departmentRef")
    private Object DepartmentRef;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "paymentType")
    private Object PaymentType;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billAddr")
    private Object BillAddr;

    @Column(name = "applyTaxAfterDiscount")
    private Boolean ApplyTaxAfterDiscount;

    @Column(name = "homeBalance")
    private Double HomeBalance;

    @Column(name = "recurDataRef")
    private Double RecurDataRef;

    @Column(name = "totalAmt")
    private Double TotalAmt;

    @Column(name = "taxExemptionRef")
    private String TaxExemptionRef;

    @Column(name = "balance")
    private Double Balance;

    @Column(name = "homeTotalAmt")
    private Double HomeTotalAmt;
}
