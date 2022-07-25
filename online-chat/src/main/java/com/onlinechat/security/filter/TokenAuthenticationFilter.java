package com.onlinechat.security.filter;


import com.baomidou.mybatisplus.extension.api.R;
import com.onlinechat.security.security.TokenManager;
import com.onlinechat.service.RedisService;
import com.onlinechat.service.impl.RedisServiceImpl;
import com.onlinechat.utils.ResponseUtil;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 访问过滤器
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    private RedisService redisService;

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager,RedisTemplate redisTemplate,RedisService redisService) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.info("================="+req.getRequestURI());
//        if(req.getRequestURI().indexOf("admin") == -1) {
//            chain.doFilter(req, res);
//            return;
//        }

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (Exception e) {
//            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setCharacterEncoding("utf-8");
            JSONObject o = new JSONObject();
            o.put("code", 401);
            o.put("message", "Invalid token");
            res.getWriter().write(o.toString());
            res.getWriter().flush();
            return;
//            ResponseUtil.out(res, res);
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
//            ResponseUtil.out(res, R.error());
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/json; charset=utf-8");
            JSONObject o = new JSONObject();
            o.put("code", 401);
            o.put("message", "Invalid token");
            res.getWriter().write(o.toString());
            res.getWriter().flush();
            return;
        }
        res.setHeader("Sec-WebSocket-Protocol",req.getHeader("Sec-WebSocket-Protocol"));
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        String wstoken = request.getHeader("Sec-WebSocket-Protocol");
        if(token == null && wstoken !=null){
            token = wstoken;
        }
        if (token != null && !"".equals(token.trim())) {

//            String userName = tokenManager.getUserFromToken(token);

            String userName = redisService.getRedisValueByKey(token);
            String newToken = redisService.getRedisValueByKey(userName);

//            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            for(String permissionValue : permissionValueList) {
//                if(StringUtils.isEmpty(permissionValue)) continue;
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
//                authorities.add(authority);
//            }
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (!StringUtils.isEmpty(userName) &&(token.equals(newToken))) {
                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
            }
            return null;
        }
        return null;
    }
}