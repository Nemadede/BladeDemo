package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.Business;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="qb_business_info")
public class QBBusiness extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tableId;

    @ManyToOne
    private User user;

    @Column(name = "id")
    private String Id;

    @Column(name = "token", columnDefinition = "text")
    private String token;

    @Column(name = "realmId")
    private String realmId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "connection_date")
    private LocalDateTime connectionDate;

    @Column(name = "code")
    private String code;

    @Column(name="companyName")
    private String CompanyName;

    @Column(name = "syncToken")
    private String SyncToken;

    @Column(name = "legalName")
    private String LegalName;

    @Column(name = "companyAddr")
    private String CompanyAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "legalAddr")
    private Object LegalAddr;

    @Column(name="supportedLanguages")
    private String SupportedLanguages;

    @Column(name="country")
    private String Country;

    @Column(name = "fiscalYearStartMonth")
    private String FiscalYearStartMonth;

    @Column(name = "email")
    private String Email;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "webAddr")
    private Object WebAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "nameValue")
    private Object NameValue;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "customerCommunicationAddr")
    private String CustomerCommunicationAddr;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "primaryPhone")
    private Object PrimaryPhone;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaData;

    @Column(name = "companyStartDate")
    private String CompanyStartDate;


}
