package com.lingyifan.taskflow.domain;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

@TableName("task_item")
public class Task {
    @TableId(type=IdType.AUTO) private Long id;
    private Long projectId; private String title; private String description; private String status; private String priority; private Long assigneeId; private LocalDateTime dueAt; private LocalDateTime updatedAt;
    public Task(){}
    public Long getId(){return id;} public void setId(Long v){id=v;} public Long getProjectId(){return projectId;} public void setProjectId(Long v){projectId=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;} public String getPriority(){return priority;} public void setPriority(String v){priority=v;}
    public Long getAssigneeId(){return assigneeId;} public void setAssigneeId(Long v){assigneeId=v;} public LocalDateTime getDueAt(){return dueAt;} public void setDueAt(LocalDateTime v){dueAt=v;}
    public LocalDateTime getUpdatedAt(){return updatedAt;} public void setUpdatedAt(LocalDateTime v){updatedAt=v;}
}
