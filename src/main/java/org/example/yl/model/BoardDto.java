package org.example.yl.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BoardDto {
    private Integer bno;
    private String title;
    private String content;
    private Date regdate;
    private int hit;
    private String userId;
    private Integer fileType;

    /*첨부파일*/
    private List<FileDto> list;
    private String fileName;        // 파일 이름
    private String[] uuids;
    private String[] fileNames;
    private String[] contentTypes;

}
