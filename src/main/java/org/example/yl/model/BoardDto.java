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

    // 이미지 파일
    private String filename;
    private String filepath;
    private String filepaths;

    // 게시물 객체에 이미지 파일 경로 리스트를 저장하기 위한 List<String> 속성 추가
    private List<String> fileList;

}
