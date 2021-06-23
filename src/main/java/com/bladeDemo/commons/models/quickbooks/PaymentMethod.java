package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.BaseEntity;
import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "qb_paymentmethod")
public class PaymentMethod extends BaseEntity {

    @Column(name = "id", insertable = false, updatable = false)
    private Integer Id;

    @EmbeddedId
    private CompositeId compositeId;

    @MapsId(value = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "name")
    private String Name;

    @Column(name = "syncToken")
    private String SyncToken;

    @Column(name = "active")
    private Boolean Active;

    @Column(name = "type")
    private String Type;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "metaData")
    private Object MetaDate;

    public CompositeId getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(CompositeId compositeId) {
        this.compositeId = compositeId;
    }
}
