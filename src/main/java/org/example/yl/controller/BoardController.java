package org.example.yl.controller;

import org.example.yl.model.BoardDto;
import org.example.yl.service.BoardService;
import org.example.yl.util.PhotoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class BoardController {

    @Autowired
    private BoardService service;
    @Autowired
    private PhotoUtil photoUtil;

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
        return "board/boardDetail";
    }

    /*@RequestMapping("/write")
    public String write(BoardDto post, HttpSession session, MultipartFile imageFile, Model model) throws IOException {
        String userId = (String) session.getAttribute("userId");
        post.setUserId(userId);
        BoardDto updatePost = service.updatePost(post);
        model.addAttribute("post", updatePost);
        return "board/boardWrite";
    }

    @RequestMapping("/insertpost")
    public String insertpost(BoardDto post, MultipartFile imageFile, HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("userId");
        post.setUserId(userId);
        service.insertpost(post, imageFile);
        return "redirect:/main";
    }*/

    @RequestMapping("/write")
    public String write(BoardDto post, HttpSession session, Model model) throws IOException {
        String userId = (String) session.getAttribute("userId");
        post.setUserId(userId);
        BoardDto updatePost = service.updatePost(post);
        model.addAttribute("post", updatePost);
        return "board/boardWrite";
    }

    @RequestMapping("/insertpost")
    public String insertpost(BoardDto post, MultipartFile[] imageFiles, HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("userId");
        post.setUserId(userId);
        service.insertpost(post, imageFiles);
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

    /*@PostMapping("/upload")
    public ModelAndView uplpoad(MultipartHttpServletRequest request){
        ModelAndView mav = new ModelAndView("jsonView");
        String uploadPath= photoUtil.ckUpload(request);
        mav.addObject("uploaded", true);
        mav.addObject("url", uploadPath);
        return mav;
    }*/
}
