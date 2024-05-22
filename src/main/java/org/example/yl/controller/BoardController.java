package org.example.yl.controller;

import org.example.yl.model.BoardDto;
import org.example.yl.model.FileDto;
import org.example.yl.service.BoardService;
import org.example.yl.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService service;

    @GetMapping("/main")
    public String pagination(@RequestParam(value="page", defaultValue = "1") int page, Model model) {
        model.addAllAttributes(service.getPaginationModel(page, 10));
        return "board/boardList";
    }

    @GetMapping("/postList/search")
    public String searchedPostList(@RequestParam String query, @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model)
    {
        model.addAllAttributes(service.getSearchedPostListModel(page, 10, query));
        return "board/boardList";
    }

    @GetMapping("/postDetail/{bno}")
    public String getpostDetailbyboardId(BoardDto board, Model model)
    {
        BoardDto post = service.getpostDetailbypostId(board);
        service.hit(board);
        model.addAttribute("post", post);
        model.addAttribute("file", service.getFile(post));
        return "board/boardDetail";
    }

    @RequestMapping("/write")
    public String write(BoardDto post, HttpSession session, Model model) throws IOException {
        String userId = (String) session.getAttribute("userId");
        post.setUserId(userId);
        if (post.getBno()!=null){
            model.addAttribute("getBoard", service.getpostDetailbypostId(post));
            model.addAttribute("getFile", service.getFile(post));
        }
        return "board/boardWrite";
    }

    @RequestMapping("/insertpost")
    public String insertpost(BoardDto post, HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("userId");
        post.setUserId(userId);
        service.insertpost(post);
        return "redirect:/main";
    }

    @DeleteMapping("/deletepost/{postId}")
    public ResponseEntity<String> deletepost(@PathVariable Integer postId)
    {
        boolean result = service.deletePostbypostId(postId);
        if (result) {
            return ResponseEntity.ok("게시글 삭제 성공");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제 실패");
        }
    }

    /*ajax로 첨부파일 처리*/
    @RequestMapping("/ajaxFile")
    @ResponseBody
    public List<FileDto> ajaxFile(@RequestParam("files") MultipartFile[] imageFiles) throws IOException {
        // 파일 등록
        List<FileDto> fileList = FileUtil.uploadFile(imageFiles);
        return fileList;
    }

    /*파일 다운로드*/
    @RequestMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@ModelAttribute FileDto fileDto) throws IOException {
        return service.downloadFile(fileDto);
    }
}
