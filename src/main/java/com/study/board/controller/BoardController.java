package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardView(Model model, @RequestParam("id") Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") Integer id){
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model){

        model.addAttribute("board",boardService.boardView(id));

        return "boardmodify";

    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board){

        //기존 내용을 가져옴
        Board boardTemp=boardService.boardView(id);
        //기존 내용에 새로 덮어씀
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);

        return "redirect:/board/list";

    }

}