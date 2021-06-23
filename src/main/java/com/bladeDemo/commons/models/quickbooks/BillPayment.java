package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="qb_billpayment")
public class BillPayment extends BaseEntity {

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

    @Column(name = "totalAmt")
    private Double TotalAmt;
    @Column(name = "payType")
    private String PayType;
    @Column(name = "syncToken")
    private String SyncToken;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "currencyRef")
    private Object CurrencyRef;

    @Column(name = "docNumber")
    private String DocNumber;
    @Column(name = "privateNote")
    private String PrivateNote;
    @Column(name = "txnDate")
    private Date TxnDate;
    @Column(name = "exchangeRate")
    private Double ExchangeRate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "apAccountRef")
    private Object APAccountRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "departmentRef")
    private Object DepartmentRef;

    @Column(name = "transactionLocationType")
    private String TransactionLocationType;
    @Column(name = "processBillPayment")
    private Boolean ProcessBillPayment;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "checkPayment")
    private Object CheckPayment;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "creditCardPayment")
    private Object CreditCardPayment;


}
