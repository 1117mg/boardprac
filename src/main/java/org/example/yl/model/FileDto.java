package org.example.yl.model;

import lombok.Data;

@Data
public class FileDto {
    private String uuid;
    private String fileName;
    private String contentType;
    private Integer fileType;
}
