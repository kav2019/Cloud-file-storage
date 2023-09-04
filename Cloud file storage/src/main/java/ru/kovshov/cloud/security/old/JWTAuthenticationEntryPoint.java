package ru.kovshov.cloud.security.old;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.kovshov.cloud.payload.responce.error.InvalidLoginResponce;
import ru.kovshov.cloud.security.SecurityConstants;
import org.springframework.security.web.AuthenticationEntryPoint;


import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
Возвращает обьект который содержит, то что параметры которые мы прислали не правельные,
и возвращать ошибку 401 не авторизован
 */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        InvalidLoginResponce loginResponce = new InvalidLoginResponce();
        String jsonLoginResponce = new Gson().toJson(loginResponce);
        response.setContentType(SecurityConstants.CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(jsonLoginResponce);
    }


    //this main laptop commit
}
