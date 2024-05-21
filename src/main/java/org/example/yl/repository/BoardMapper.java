package org.example.yl.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.yl.model.BoardDto;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    List<BoardDto> getboardList();

    BoardDto getpostDetailbypostId(BoardDto post);

    void insertPost(BoardDto post);

    void updatePost(BoardDto post);

    int deletePostbypostId(int postId);

    List<BoardDto> getSearchedPostPage(Map<String, Object> params);

    List<BoardDto> getPostbyPage(Map<String, Object> params);

    int countPosts();

    int searchedPostCount(String query);

    List<BoardDto> searchedPostList(String query);

    int hit(BoardDto board);

}
