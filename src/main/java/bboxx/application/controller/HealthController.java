package bboxx.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Health check api")
@RestController
@RequestMapping("/health")
public class HealthController {

    @ApiOperation(value = "생존 확인")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck() {
    }
}
