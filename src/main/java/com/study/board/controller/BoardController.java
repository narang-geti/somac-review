package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
//    @GetMapping("/")
//    @ResponseBody
//    public String main(){
//        return "Hello world";
//
//    }
    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write")//어떤 url로 접근할 것인지 지정 localhost:8080/board/write
    public String boardWriteForm(){
        //어떤 viewfile인지 입력
        return "boardwrite";
    }
//    @RequestParam("title") String title, @RequestParam("content") String content
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){

//        System.out.println("제목 : "+title);
//        System.out.println("내용 : "+content);
//        System.out.println(board.getTitle());
        boardService.write(board);

        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list",boardService.boardList());

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(){

        return "boardView";
    }

}