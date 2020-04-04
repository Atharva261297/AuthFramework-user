package com.atharva.auth.user.model;

import com.atharva.auth.user.constants.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private ErrorCodes code;
    private String data;
}
