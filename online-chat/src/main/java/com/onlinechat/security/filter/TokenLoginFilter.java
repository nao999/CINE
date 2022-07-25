package com.onlinechat.security.filter;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinechat.base.RestResponse;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.SecurityUser;
import com.onlinechat.entity.TUser;
import com.onlinechat.security.security.TokenManager;
import com.onlinechat.service.RedisService;
import com.onlinechat.service.impl.RedisServiceImpl;
import com.onlinechat.utils.ResponseUtil;
import com.onlinechat.utils.Utils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    private RedisService redisService;


    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate,RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.redisService = redisService;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            TUser user = new ObjectMapper().readValue(req.getInputStream(), TUser.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        SecurityUser user = (SecurityUser) auth.getPrincipal();

        String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
//        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());

        // 新token创建
        redisService.setRedisKeyValueWithTimeout(token,user.getUsername(),  12, TimeUnit.HOURS);

        // 查有没有该用户的旧token
        String preToken = redisService.getRedisValueByKey(user.getUsername());
        if(preToken != null){
            redisService.removeRedisKeyRemote(preToken);
        }
        redisService.setRedisKeyValueWithTimeout(user.getUsername(),token,  12, TimeUnit.HOURS);

        ResponseUtil.out(res, RestResponse.ok(token));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {

        ResponseUtil.out(response, RestResponse.fail(SystemCode.LOGINERROR.getCode(), SystemCode.InnerError.getMessage()));
    }
}
