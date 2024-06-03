package com.tapyou.heplers.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserIdListResponse {
    private String errorMessage;
    private Integer errorCode;
    private List<Integer> idList;
    private Boolean isSuccess;
}