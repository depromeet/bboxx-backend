package bboxx.application.security;

import bboxx.application.controller.dto.response.ApiResponse;
import bboxx.application.controller.dto.response.EmptyJsonResponse;
import bboxx.domain.exception.DomainErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {

        log.error(e.getMessage(), e);

        var unauthorizedResponse = ApiResponse.failure(DomainErrorCode.UNAUTHORIZED_ERROR);
        String jsonLoginResponse = objectMapper.writeValueAsString(unauthorizedResponse);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);
    }
}