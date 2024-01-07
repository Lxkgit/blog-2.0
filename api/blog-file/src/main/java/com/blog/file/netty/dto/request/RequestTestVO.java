package com.blog.file.netty.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTestVO implements Serializable {

    private String key;
    private String value;
    private Date date;
}