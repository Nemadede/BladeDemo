package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "qb_invoice")
public class Invoice extends BaseEntity {

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

    @Column(name = "docNumber")
    private String DocNumber;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billEmail")
    private Object BillEmail;

    @Column(name = "txnDate")
    private Date TxnDate;


    @Type(type = "json")
    @Column(columnDefinition = "json", name = "shipFromAddr")
    private Object ShipFromAddr;

    @Column(name = "shipDate")
    private Date ShipDate;

    @Column(name = "trackingNum")
    private String TrackingNum;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "classRef")
    private Object ClassRef;

    @Column(name = "printStatus")
    private String PrintStatus;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "salesTermRef")
    private Object SalesTermRef;

    @Column(name = "txnSource")
    private String TxnSource;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "linkedTxn")
    private Object LinkedTxn;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "depositToAccountRef")
    private Object DepositToAccountRef;

    @Column(name = "globalTaxCalculation")
    private String GlobalTaxCalculation;
    @Column(name = "allowOnlineACHPayment")
    private Boolean AllowOnlineACHPayment;
    @Column(name = "transactionLocationType")
    private String TransactionLocationType;
    @Column(name = "dueDate")
    private Date DueDate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Column(name = "privateNote")
    private String PrivateNote;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billEmailCc")
    private Object BillEmailCc;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerMemo")
    private Object CustomerMemo;

    @Column(name = "emailStatus")
    private String EmailStatus;
    @Column(name = "exchangeRate")
    private Double ExchangeRate;
    @Column(name = "deposit")
    private Double Deposit;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "txnTaxDetail")
    private Object TxnTaxDetail;

    @Column(name = "allowOnlineCreditCardPayment")
    private Boolean AllowOnlineCreditCardPayment;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customField")
    private Object CustomField;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "shipAddr")
    private Object ShipAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "departmentRef")
    private Object DepartmentRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billEmailBcc")
    private Object BillEmailBcc;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "shipMethodRef")
    private Object ShipMethodRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "billAddr")
    private Object BillAddr;

    @Column(name = "applyTaxAfterDiscount")
    private Boolean ApplyTaxAfterDiscount;

    @Column(name = "homeBalance")
    private Double HomeBalance;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "deliveryInfo")
    private Object DeliveryInfo;

    @Column(name = "totalAmt")
    private Double TotalAmt;
    @Column(name = "invoiceLink")
    private String InvoiceLink;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "recurDataRef")
    private Object RecurDataRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "taxExemptionRef")
    private Object TaxExemptionRef;

    @Column(name = "balance")
    private Double Balance;

    @Column(name = "homeTotalAmt")
    private Double HomeTotalAmt;

    @Column(name = "freeFormAddress")
    private Boolean FreeFormAddress;

    @Column(name = "allowOnlinePayment")
    private Boolean AllowOnlinePayment;

    @Column(name = "allowIPNPayment")
    private Boolean AllowIPNPayment;
}
