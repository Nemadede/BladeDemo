package com.bladeDemo.commons.models.quickbooks;


import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="qb_bill")
public class Bill extends BaseEntity {

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

    @Column(name = "txnDate")
    private Date TxnDate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "apAccountRef")
    private Object APAccountRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "salesTermRef")
    private Object SalesTermRef;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "linkedTxn")
    private Object LinkedTxn;


    @Column(name = "globalTaxCalculation")
    private String GlobalTaxCalculation;

    @Column(name = "totalAmt")
    private Double TotalAmt;

    @Column(name = "transactionLocationType")
    private String TransactionLocationType;

    @Column(name = "dueDate")
    private Date DueDate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Column(name = "docNumber")
    private String DocNumber;
    @Column(name = "privateNote")
    private String PrivateNote;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "txnTaxDetail")
    private Object TxnTaxDetail;

    @Column(name = "exchangeRate")
    private Double ExchangeRate;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "departmentRef")
    private Object DepartmentRef;

    @Column(name = "includeInAnnualTPAR")
    private Boolean IncludeInAnnualTPAR;
    @Column(name = "homeBalance")
    private Double HomeBalance;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "recurDataRef")
    private Object RecurDataRef;

    @Column(name = "balance")
    private Double Balance;

    public CompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(CompositeId compositeId) {
        this.compositeId = compositeId;
    }
}
