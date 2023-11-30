package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    //글 작성 완료 띄우기
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, @RequestParam("file") MultipartFile file) throws Exception{

//        System.out.println("제목 : "+title);
//        System.out.println("내용 : "+content);
//        System.out.println(board.getTitle());
        boardService.write(board, file);

        model.addAttribute("message","리뷰 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");


        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page=0, size=10, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("list",boardService.boardList(pageable));

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
    public String boardUpdate(@PathVariable("id") Integer id, Board board, @RequestParam("file") MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";

    }
}