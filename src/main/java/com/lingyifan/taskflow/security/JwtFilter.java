package com.lingyifan.taskflow.security;
import com.lingyifan.taskflow.domain.User; import com.lingyifan.taskflow.mapper.UserMapper; import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.*; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.List;
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwt; private final UserMapper users;
    public JwtFilter(JwtService jwt,UserMapper users){this.jwt=jwt;this.users=users;}
    @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
        String h=req.getHeader("Authorization");
        if(h!=null&&h.startsWith("Bearer ")) try {String username=jwt.parse(h.substring(7)).getSubject();User u=users.selectByUsername(username);if(u!=null) SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,null,List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole()))));} catch(Exception ignored){}
        chain.doFilter(req,res);
    }
}
