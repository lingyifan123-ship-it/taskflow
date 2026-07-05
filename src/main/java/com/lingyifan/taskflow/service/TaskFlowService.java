package com.lingyifan.taskflow.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.lingyifan.taskflow.api.Dtos.*; import com.lingyifan.taskflow.domain.*; import com.lingyifan.taskflow.mapper.*; import org.springframework.cache.annotation.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime; import java.util.List;
@Service
public class TaskFlowService {
    private final ProjectMapper projects; private final TaskMapper tasks; private final AuditLogMapper logs; private final UserMapper users;
    public TaskFlowService(ProjectMapper projects,TaskMapper tasks,AuditLogMapper logs,UserMapper users){this.projects=projects;this.tasks=tasks;this.logs=logs;this.users=users;}
    public User user(String name){User u=users.selectByUsername(name);if(u==null)throw new IllegalArgumentException("用户不存在");return u;}
    @Transactional public Project createProject(ProjectInput in,String username){User u=user(username);Project p=new Project(null,in.name(),in.description(),u.getId(),LocalDateTime.now());projects.insert(p);log(u.getId(),"CREATE_PROJECT","project="+p.getId());return p;}
    @Transactional @CacheEvict(cacheNames="tasks",allEntries=true) public Task createTask(TaskInput in,String username){Project p=projects.selectById(in.projectId());if(p==null)throw new IllegalArgumentException("项目不存在");Task t=new Task();t.setProjectId(in.projectId());t.setTitle(in.title());t.setDescription(in.description());t.setPriority(in.priority()==null?"MEDIUM":in.priority());t.setStatus("TODO");t.setAssigneeId(in.assigneeId());t.setDueAt(in.dueAt());t.setUpdatedAt(LocalDateTime.now());tasks.insert(t);log(user(username).getId(),"CREATE_TASK","task="+t.getId());return t;}
    @Cacheable(cacheNames="tasks",key="#id") public Task getTask(Long id){Task t=tasks.selectById(id);if(t==null)throw new IllegalArgumentException("任务不存在");return t;}
    public List<Task> list(Long projectId){return tasks.selectList(new LambdaQueryWrapper<Task>().eq(projectId!=null,Task::getProjectId,projectId).orderByDesc(Task::getUpdatedAt));}
    @Transactional @CacheEvict(cacheNames="tasks",key="#id") public Task changeStatus(Long id,String status,String username){Task t=getTask(id);if(!validTransition(t.getStatus(),status))throw new IllegalStateException("不允许的状态流转: "+t.getStatus()+" -> "+status);t.setStatus(status);t.setUpdatedAt(LocalDateTime.now());tasks.updateById(t);log(user(username).getId(),"CHANGE_STATUS","task="+id+",status="+status);return t;}
    @Transactional @CacheEvict(cacheNames="tasks",key="#id") public void delete(Long id,String username){Task t=getTask(id);User u=user(username);Project p=projects.selectById(t.getProjectId());if(!"ADMIN".equals(u.getRole())&&!p.getOwnerId().equals(u.getId()))throw new SecurityException("只能删除自己的项目任务");tasks.deleteById(id);log(u.getId(),"DELETE_TASK","task="+id);}
    public List<AuditLog> logs(){return logs.selectList(new LambdaQueryWrapper<AuditLog>().orderByDesc(AuditLog::getCreatedAt));}
    private boolean validTransition(String from,String to){return from.equals(to)||(from.equals("TODO")&&to.equals("DOING"))||(from.equals("DOING")&&(to.equals("TODO")||to.equals("DONE")))||(from.equals("DONE")&&to.equals("DOING"));}
    private void log(Long userId,String action,String detail){logs.insert(new AuditLog(null,userId,action,detail,LocalDateTime.now()));}
}
