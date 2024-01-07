package com.blog.file.netty.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTestVO implements Serializable {

    private String message;
    private Date date;
}