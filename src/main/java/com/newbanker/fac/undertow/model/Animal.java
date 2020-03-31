package com.newbanker.fac.undertow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author roger
 * @Date $ $
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    private String name;

    private Integer age;

    private Boolean happy;

}
