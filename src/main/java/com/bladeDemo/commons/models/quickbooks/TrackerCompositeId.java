package com.bladeDemo.commons.models.quickbooks;

import java.io.Serializable;
import java.util.Objects;

public class TrackerCompositeId implements Serializable {

    private String tableName;
    private Integer user_id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackerCompositeId that = (TrackerCompositeId) o;
        return tableName.equals(that.tableName) &&
                user_id.equals(that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, user_id);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getBusiness_id() {
        return user_id;
    }

    public void setBusiness_id(Integer user_id) {
        this.user_id = user_id;
    }


}
