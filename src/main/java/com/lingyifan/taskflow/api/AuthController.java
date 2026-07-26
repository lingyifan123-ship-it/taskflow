package com.lingyifan.taskflow.api;
import com.lingyifan.taskflow.api.Dtos.*; import com.lingyifan.taskflow.domain.User; import com.lingyifan.taskflow.mapper.UserMapper; import com.lingyifan.taskflow.security.JwtService; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final UserMapper users; private final PasswordEncoder encoder; private final JwtService jwt;
    public AuthController(UserMapper users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public Token register(@Valid @RequestBody Register in){if(users.selectByUsername(in.username())!=null)throw new IllegalArgumentException("用户名已存在");User u=new User(null,in.username(),encoder.encode(in.password()),"USER");users.insert(u);return new Token(jwt.create(u.getUsername(),u.getRole()),u.getUsername(),u.getRole());}
    @PostMapping("/login") public Token login(@Valid @RequestBody Login in){User u=users.selectByUsername(in.username());if(u==null||!encoder.matches(in.password(),u.getPassword()))throw new IllegalArgumentException("用户名或密码错误");return new Token(jwt.create(u.getUsername(),u.getRole()),u.getUsername(),u.getRole());}
}
