package com.tapyou.heplers.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User{
    private String gender;
    private String city;
    private String name;
    private String registrationDate;
    private Integer id;
    private Integer age;
}