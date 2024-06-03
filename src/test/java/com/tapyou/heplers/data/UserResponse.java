package com.tapyou.heplers.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse{
    private String errorMessage;
    private Integer errorCode;
    private User user;
    private Boolean isSuccess;
}