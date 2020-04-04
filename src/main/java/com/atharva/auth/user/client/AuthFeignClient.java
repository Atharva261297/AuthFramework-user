package com.atharva.auth.user.client;

import com.atharva.auth.user.constants.ErrorCodes;
import com.atharva.auth.user.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "AuthClient", url = "${url.auth}")
public interface AuthFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    Response register(@RequestHeader String project_auth, @RequestHeader String user_auth);

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    Response login(@RequestHeader String project_auth, @RequestHeader String user_auth);

    @RequestMapping(method = RequestMethod.GET, value = "/reset")
    Response change(@RequestHeader String new_auth, @RequestHeader String old_auth, @RequestHeader String project_auth);
}
