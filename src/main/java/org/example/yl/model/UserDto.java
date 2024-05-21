package org.example.yl.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Integer idx;
    private String userId;
    private String pw;
    private String username;
    private LocalDateTime createdTime;
}
