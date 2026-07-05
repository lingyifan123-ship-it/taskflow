package com.lingyifan.taskflow.api;
import jakarta.validation.constraints.*; import java.time.LocalDateTime;
public final class Dtos {
    private Dtos(){}
    public record Register(@NotBlank @Size(min=3,max=30) String username,@NotBlank @Size(min=8,max=72) String password){}
    public record Login(@NotBlank String username,@NotBlank String password){}
    public record Token(String token,String username,String role){}
    public record ProjectInput(@NotBlank @Size(max=80) String name,@Size(max=500) String description){}
    public record TaskInput(@NotNull Long projectId,@NotBlank @Size(max=120) String title,@Size(max=1000) String description,@Pattern(regexp="LOW|MEDIUM|HIGH") String priority,Long assigneeId,LocalDateTime dueAt){}
    public record StatusInput(@Pattern(regexp="TODO|DOING|DONE") String status){}
}
