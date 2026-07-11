package com.lingyifan.taskflow.api;
import com.lingyifan.taskflow.api.Dtos.*; import com.lingyifan.taskflow.domain.*; import com.lingyifan.taskflow.service.TaskFlowService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api")
public class TaskController {
    private final TaskFlowService service; public TaskController(TaskFlowService service){this.service=service;}
    @PostMapping("/projects") @ResponseStatus(HttpStatus.CREATED) public Project project(@Valid @RequestBody ProjectInput in,Authentication a){return service.createProject(in,a.getName());}
    @PostMapping("/tasks") @ResponseStatus(HttpStatus.CREATED) public Task task(@Valid @RequestBody TaskInput in,Authentication a){return service.createTask(in,a.getName());}
    @GetMapping("/tasks/{id}") public Task one(@PathVariable Long id){return service.getTask(id);}
    @GetMapping("/tasks") public List<Task> list(@RequestParam(required=false) Long projectId){return service.list(projectId);}
    @PatchMapping("/tasks/{id}/status") public Task status(@PathVariable Long id,@Valid @RequestBody StatusInput in,Authentication a){return service.changeStatus(id,in.status(),a.getName());}
    @DeleteMapping("/tasks/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id,Authentication a){service.delete(id,a.getName());}
    @GetMapping("/admin/logs") @PreAuthorize("hasRole('ADMIN')") public List<AuditLog> logs(){return service.logs();}
}
