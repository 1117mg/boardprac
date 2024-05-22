package org.example.yl.service;

import org.example.yl.model.BoardDto;
import org.example.yl.model.FileDto;
import org.example.yl.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Primary
@Service
public class BoardService {
    @Autowired
    private BoardMapper mapper;

    public Map<String, Object> getPaginationModel(int page, int postperPage) {
        int totalPost = countPosts();
        int totalPage = (int) Math.ceil((double) totalPost / postperPage);
        List<BoardDto> postList = getPostByPage(page, postperPage);

        Map<String, Object> model = new HashMap<>();
        model.put("postList", postList);
        model.put("currentPage", page);
        model.put("totalPage", totalPage);

        return model;
    }

    public Map<String, Object> getSearchedPostListModel(int page, int postperPage, String query) {
        int totalPost = searchedPostCount(query);
        int totalPage = (int) Math.ceil((double) totalPost / postperPage);
        List<BoardDto> searchResults = getSearchedPostByPage(page, postperPage, query);

        Map<String, Object> model = new HashMap<>();
        model.put("postList", searchResults);
        model.put("currentPage", page);
        model.put("totalPage", totalPage);

        return model;
    }

    public BoardDto getpostDetailbypostId(BoardDto post){
        return mapper.getpostDetailbypostId(post);
    }

    // 파일 다운로드
    public ResponseEntity<Resource> downloadFile(FileDto fileDto) throws IOException {
        // 파일 저장 경로 설정
        String filePath = "D:\\image";
        Path path = Paths.get(filePath + "/" + fileDto.getUuid() + "_" + fileDto.getFileName());
        String contentType = Files.probeContentType(path);
        // header를 통해서 다운로드 되는 파일의 정보를 설정한다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileDto.getFileName(), StandardCharsets.UTF_8)
                .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    // 게시판, 파일 (등록, 수정, 삭제)
    public void insertpost(BoardDto post) throws IOException {
        if (post.getTitle() == null || post.getTitle().trim().isEmpty() ||
                post.getContent() == null || post.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("제목 및 내용을 입력하세요.");
        }

        if (post.getBno() != null) {
            mapper.deleteFile(post);
        }
        if (post.getBno() != null) {
            mapper.updatePost(post);
        } else {
            mapper.insertPost(post);
        }
        // 파일 이름 유니크하게 생성
        List<FileDto> list = new ArrayList<>();
        String[] uuids = post.getUuids();
        String[] fileNames = post.getFileNames();
        String[] contentTypes = post.getContentTypes();
        int fileType=1;

        if(uuids!=null){
            for(int i=0;i<uuids.length;i++){
                FileDto fileDto = new FileDto();
                fileDto.setFileName(fileNames[i]);
                fileDto.setUuid(uuids[i]);
                fileDto.setContentType(contentTypes[i]);
                fileDto.setFileType(fileType);
                list.add(fileDto);
            }
        }

        // 첨부파일 등록 (게시글이 있을경우)
        if (!list.isEmpty()) {
            post.setList(list);
            mapper.insertFile(post);
        }
    }


    public boolean deletePostbypostId(int postId) {
        int result  = mapper.deletePostbypostId(postId);
        if(result==1){
            return true;
        }
        return false;
    }

    public int countPosts() {
        return mapper.countPosts();
    }

    public int searchedPostCount(String query) {
        return mapper.searchedPostCount(query);
    }

    public List<BoardDto> searchedPostList(String query) {
        return mapper.searchedPostList(query);
    }

    public List<BoardDto> getPostByPage(int page, int postperPage) {
        return getPagePosts(page, postperPage, null);
    }

    public List<BoardDto> getSearchedPostByPage(int page, int postperPage, String query) {
        return getPagePosts(page, postperPage, query);
    }

    private List<BoardDto> getPagePosts(int page, int postperPage, String query)
    {
        int offset = (page - 1) * postperPage;
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("postperPage", postperPage);
        if (query == null || query == "")
        {
            return mapper.getPostbyPage(params);
        } else {
            params.put("query", query);
            return mapper.getSearchedPostPage(params);
        }
    }

    public void hit(BoardDto board) {
        mapper.hit(board);
    }

    // 첨부파일 리스트
    public List<FileDto> getFile (BoardDto post) {
        post.setFileType(1);
        return mapper.getFile(post);}
}
