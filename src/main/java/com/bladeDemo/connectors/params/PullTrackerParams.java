package com.bladeDemo.connectors.params;

import com.bladeDemo.commons.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PullTrackerParams {
     private User user;
     private String tableName;
     private LocalDateTime connectionDate;

     public PullTrackerParams(User user, String tableName, LocalDateTime connectionDate) {
          this.user = user;
          this.tableName = tableName;
          this.connectionDate = connectionDate;
     }
}
