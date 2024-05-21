package org.example.yl.service;

import org.example.yl.model.BoardDto;
import org.example.yl.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    // 게시판, 파일 (등록, 수정, 삭제)
    public void insertpost(BoardDto post, MultipartFile[] imageFiles) throws IOException {
        if (post.getTitle() == null || post.getTitle().trim().isEmpty() ||
                post.getContent() == null || post.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("제목 및 내용을 입력하세요.");
        }
        if (post.getBno() != null) {
            // 이미지 파일이 있는 경우에만 파일 처리를 수행합니다.
            if (imageFiles != null && imageFiles.length > 0) {
                List<String> fileList = new ArrayList<>();
                for (MultipartFile imageFile : imageFiles) {
                    String projectPath = "D:\\image"; // D 드라이브의 "image" 폴더 경로
                    UUID uuid = UUID.randomUUID();
                    String filename = uuid + "_" + imageFile.getOriginalFilename();
                    File saveFile = new File(projectPath, filename);
                    imageFile.transferTo(saveFile);

                    post.setFilename(filename);
                    String filepath = "/image/" + filename;
                    fileList.add(filepath); // 파일 경로를 리스트에 추가
                }
                post.setFileList(fileList); // 게시물 객체에 파일 경로 리스트 설정
                mapper.updatePost(post); // 글 수정
            }
        } else {
            // 글 등록 시에는 파일 처리를 수행합니다.
            if (imageFiles != null && imageFiles.length > 0) {
                List<String> fileList = new ArrayList<>();
                for (MultipartFile imageFile : imageFiles) {
                    String projectPath = "D:\\image"; // D 드라이브의 "image" 폴더 경로
                    UUID uuid = UUID.randomUUID();
                    String filename = uuid + "_" + imageFile.getOriginalFilename();
                    File saveFile = new File(projectPath, filename);
                    imageFile.transferTo(saveFile);

                    post.setFilename(filename);
                    String filepath = "/image/" + filename;
                    fileList.add(filepath); // 파일 경로를 리스트에 추가
                }
                post.setFileList(fileList); // 게시물 객체에 파일 경로 리스트 설정
                mapper.insertPost(post); // 글 등록
            }
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


    public BoardDto updatePost(BoardDto post) {
        if(post.getBno()!=null){
            return getpostDetailbypostId(post);
        }else{
            return new BoardDto();
        }
    }
}
