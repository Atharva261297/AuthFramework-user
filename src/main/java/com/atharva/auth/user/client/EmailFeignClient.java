package com.atharva.auth.user.client;

import com.atharva.auth.user.constants.ErrorCodes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "EmailClient", url = "${url.email}")
public interface EmailFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/verify-email/{email}/{userId}/{projectName}")
    ErrorCodes senVerificationEmail(@PathVariable(name = "email") String email, @PathVariable(name = "userId") String userId,
                                    @PathVariable(name = "projectName") String projectName);

    @RequestMapping(method = RequestMethod.GET, value = "/admin/reset-password/{email}/{userId}/{projectName}")
    public ErrorCodes sendResetPassword(@PathVariable(name = "email") String email, @PathVariable(name = "userId") String userId,
                                        @PathVariable(name = "projectName") String projectName);

}
