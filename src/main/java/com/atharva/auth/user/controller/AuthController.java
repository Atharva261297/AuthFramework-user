package com.atharva.auth.user.controller;

import com.atharva.auth.user.client.EmailFeignClient;
import com.atharva.auth.user.constants.ErrorCodes;
import com.atharva.auth.user.model.Response;
import com.atharva.auth.user.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailFeignClient emailClient;

    Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ErrorCodes registerFull(@RequestHeader String project_auth, @RequestHeader String user_auth, @RequestBody String userData) {
        log.debug("Register : " + userData);
        return authService.register(project_auth, user_auth, userData);
    }

    @GetMapping("/getData")
    public Response login(@RequestHeader String project_auth, @RequestHeader String user_auth) {
        return authService.getData(project_auth, user_auth);
    }

    @PostMapping("/updateData")
    public ErrorCodes updateData(@RequestHeader String project_auth, @RequestHeader String user_auth, @RequestBody String userData) {
        log.debug("Update : " + userData);
        return authService.updateData(project_auth, user_auth, userData);
    }

//    @PostMapping("/resetPassword")
//    public ErrorCodes resetPassword(@RequestHeader String project_auth, @RequestHeader String user_auth) {
////        log.debug("Reset : " + );
//        return authService.resetPassword(project_auth, user_auth);
//    }

//    @PostMapping("/sendResetPasswordEmail")
//    public ErrorCodes sendResetPasswordEmail(@RequestHeader String project_auth, @RequestHeader String user_auth) {
////        log.debug("Reset : " + );
//        return emailClient.sendResetPassword()
//    }

    @PostMapping("/change")
    public ErrorCodes changePassword(@RequestHeader String project_auth, @RequestHeader String user_old_auth, @RequestHeader String user_new_auth) throws JsonProcessingException {
//        log.debug("Reset : " + );
        return authService.changePassword(project_auth, user_old_auth, user_new_auth);
    }
}
