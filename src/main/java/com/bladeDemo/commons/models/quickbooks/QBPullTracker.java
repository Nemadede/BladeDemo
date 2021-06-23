package com.bladeDemo.commons.models.quickbooks;

import com.bladeDemo.commons.models.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "qb_pull_tracker")
public class QBPullTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @Column(name = "tableName", nullable = false)
    private String tableName;

    @Column(name = "last_item_inserted_on")
    private LocalDateTime LastItemInsertedOn;

    @Column(name = "first_item_inserted_on")
    private LocalDateTime firstItemInsertedOn;

    @Column(name = "last_pull_date_limit")
    private LocalDateTime lastPullDateLimit;

    @Column(name = "created_on")
    @CreationTimestamp
    private Timestamp createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private Timestamp updatedOn;

}
