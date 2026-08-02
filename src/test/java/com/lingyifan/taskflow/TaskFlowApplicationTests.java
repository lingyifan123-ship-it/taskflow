package com.lingyifan.taskflow;
import tools.jackson.databind.*; import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskFlowApplicationTests {
 @Autowired MockMvc mvc; @Autowired ObjectMapper json; static String token; static long projectId; static long taskId;
 @Test @Order(1) void fullFlow() throws Exception {
   String register=mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"student\",\"password\":\"Password1!\"}" )).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(); token=json.readTree(register).get("token").asText();
   String project=mvc.perform(post("/api/projects").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"毕业设计\",\"description\":\"接口重建\"}")).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(); projectId=json.readTree(project).get("id").asLong();
   String task=mvc.perform(post("/api/tasks").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{\"projectId\":"+projectId+",\"title\":\"完成登录接口\",\"priority\":\"HIGH\"}")).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(); taskId=json.readTree(task).get("id").asLong();
   mvc.perform(patch("/api/tasks/"+taskId+"/status").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{\"status\":\"DOING\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("DOING"));
   mvc.perform(get("/api/tasks/"+taskId).header("Authorization","Bearer "+token)).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("完成登录接口"));
 }
 @Test @Order(2) void validationAndPermission() throws Exception {
   mvc.perform(post("/api/tasks").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON).content("{\"projectId\":1,\"title\":\"\",\"priority\":\"URGENT\"}")).andExpect(status().isBadRequest());
   mvc.perform(get("/api/admin/logs").header("Authorization","Bearer "+token)).andExpect(status().isForbidden());
 }
}
