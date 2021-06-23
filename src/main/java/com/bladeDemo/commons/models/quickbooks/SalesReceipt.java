package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qb_salesreceipt")
public class SalesReceipt extends BaseEntity {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "line")
    private Object Line;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerRef")
    private Object CustomerRef;

    @Column(name = "syncToken")
    private String SyncToken;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billEmail")
    private Object BillEmail;

    @Column(name = "txnDate")
    private Date TxnDate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "shipFromAddr")
    private Object ShipFromAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customField")
    private Object CustomField;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "shipDate")
    private Object ShipDate;

    @Column(name = "trackingNum")
    private String TrackingNum;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "classRef")
    private Object ClassRef;

    @Column(name = "printStatus")
    private String PrintStatus;

    @Column(name = "paymentRefNum")
    private String PaymentRefNum;

    @Column(name = "txnSource")
    private String TxnSource;

    @Column(name = "globalTaxCalculation")
    private String GlobalTaxCalculation;

    @Column(name = "transactionLocationType")
    private String TransactionLocationType;

    @Column(name = "applyTaxAfterDiscount")
    private Boolean ApplyTaxAfterDiscount;

    @Column(name = "docNumber")
    private String DocNumber;

    @Column(name = "privateNote")
    private String PrivateNote;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "depositToAccountRef")
    private Object DepositToAccountRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerMemo")
    private Object CustomerMemo;

    @Column(name = "emailStatus")
    private String EmailStatus;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "creditCardPayment")
    private Object CreditCardPayment;

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
    @Column(columnDefinition = "json", name = "shipMethodRef")
    private Object ShipMethodRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billAddr")
    private Object BillAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Column(name = "homeBalance")
    private Double HomeBalance;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "deliveryInfo")
    private Object DeliveryInfo;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "recurDataRef")
    private Object RecurDataRef;

    @Column(name = "totalAmt")
    private Double TotalAmt;

    @Column(name = "balance")
    private Double Balance;

    @Column(name = "homeTotalAmt")
    private Double HomeTotalAmt;
}
