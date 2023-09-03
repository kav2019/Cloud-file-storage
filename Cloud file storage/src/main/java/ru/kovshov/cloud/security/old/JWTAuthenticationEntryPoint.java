package ru.kovshov.cloud.security.old;

import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
Возвращает обьект который содержит, то что параметры которые мы прислали не правельные,
и возвращать ошибку 401 не авторизован
 */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        InvalidLoginResponce loginResponce = new InvalidLoginResponce();
        String jsonLoginResponce = new Gson().toJson(loginResponce);
        response.setContentType(SecurityConstants.CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(jsonLoginResponce);
    }


    //this main laptop commit
}
