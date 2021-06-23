package com.bladeDemo.commons.models.quickbooks;

import io.github.biezhi.anima.Model;
import io.github.biezhi.anima.annotation.Column;
import lombok.Data;
import org.json.JSONObject;

@Data
public class General extends Model {

    @Column(name = "Id")
    public Integer id;

    @Column(name = "business_id")
    public Integer businessId;

    @Column(name = "SyncToken")
    public String syncToken;

    @Column(name="Active")
    public Boolean active;

    @Column(name= "MetaData")
    public JSONObject metaData;

}
