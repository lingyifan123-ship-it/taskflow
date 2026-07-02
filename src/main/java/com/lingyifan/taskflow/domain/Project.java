package com.lingyifan.taskflow.domain;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

@TableName("project")
public class Project {
    @TableId(type=IdType.AUTO) private Long id;
    private String name; private String description; private Long ownerId; private LocalDateTime createdAt;
    public Project(){} public Project(Long id,String name,String description,Long ownerId,LocalDateTime createdAt){this.id=id;this.name=name;this.description=description;this.ownerId=ownerId;this.createdAt=createdAt;}
    public Long getId(){return id;} public void setId(Long v){id=v;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;} public Long getOwnerId(){return ownerId;} public void setOwnerId(Long v){ownerId=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
