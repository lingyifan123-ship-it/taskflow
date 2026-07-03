package com.lingyifan.taskflow.domain;
import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
@TableName("audit_log")
public class AuditLog {
    @TableId(type=IdType.AUTO) private Long id; private Long userId; private String action; private String detail; private LocalDateTime createdAt;
    public AuditLog(){} public AuditLog(Long id,Long userId,String action,String detail,LocalDateTime createdAt){this.id=id;this.userId=userId;this.action=action;this.detail=detail;this.createdAt=createdAt;}
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;}
    public String getAction(){return action;} public void setAction(String v){action=v;} public String getDetail(){return detail;} public void setDetail(String v){detail=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
