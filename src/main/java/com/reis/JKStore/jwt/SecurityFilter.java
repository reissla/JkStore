package com.reis.JKStore.jwt;

import com.reis.JKStore.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository usuarioRepository;

    private static List<String> PUBLIC_URLS = List.of(
            "/auth/**",
            "/summary/getSummariesWithLatestDate",
            "/summary/getLatestSummariesMoreLiked"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        var token = this.recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);

            UserDetails user = usuarioRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private boolean isPublicRequest(HttpServletRequest request){
        if(request == null){
            throw  new IllegalArgumentException();
        }
        String path = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();

        return PUBLIC_URLS.stream().anyMatch(p -> matcher.match(p, path));
    }

}