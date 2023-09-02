package ru.kovshov.cloud.security.old;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kovshov.cloud.security.SecurityConstants;
import ru.kovshov.cloud.service.UserDetailService;

import java.io.IOException;

public class JWTAuthenticatonFilter extends OncePerRequestFilter {
    public static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticatonFilter.class);

    private final JWTTokenProvaider jwtTokenProvaider;
    private final UserDetailService userDetailService;


    @Autowired
    public JWTAuthenticatonFilter(JWTTokenProvaider jwtTokenProvaider, UserDetailService userDetailService) {
        this.jwtTokenProvaider = jwtTokenProvaider;
        this.userDetailService = userDetailService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(request);
            if(StringUtils.hasText(jwt) && jwtTokenProvaider.validateToken(jwt)){
                Long userId = jwtTokenProvaider.getUserIdFromToken(jwt);
                UserDetails userDetails = userDetailService.loadUserByUserId(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            LOG.error("Cloud not set user authentication");
        }
        filterChain.doFilter(request, response);
    }


    private String getJWTFromRequest(HttpServletRequest request){
        String bearToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if(StringUtils.hasText(bearToken) && bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return bearToken.split(" ")[1];
        }
        return null;
    }
}
