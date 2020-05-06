package com.web.controller;

import com.web.domain.WebBoard;
import com.web.repository.WebBoardRepository;
import com.web.vo.PageMaker;
import com.web.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/boards")
public class WebBoardController {

    @Autowired
    private WebBoardRepository boardRepository;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageVO") PageVO pageVO, Model model){
        Pageable pageable= pageVO.makePageable(0,"bno");
        Page<WebBoard> result= boardRepository.findAll(boardRepository.makePredicate(pageVO.getType(), pageVO.getKeyword()),pageable);
        model.addAttribute("result",new PageMaker(result));

    }

    @GetMapping("/register")
    public void register(){
    }
    @PostMapping("/register")
    public String registerPost(@ModelAttribute("board") WebBoard board, RedirectAttributes rttr){
        boardRepository.save(board);
        rttr.addFlashAttribute("msg","success");
        return "redirect:/boards/list";
    }

    @GetMapping("/view")
    public void view(Long bno, @ModelAttribute("pageVO") PageVO pageVO,Model model){
        boardRepository.findById(bno).ifPresent(board -> model.addAttribute("vo",board));
    }
    @GetMapping("/modify")
    public void modify(Long bno, @ModelAttribute("pageVO")PageVO pageVO,Model model){
        boardRepository.findById(bno).ifPresent(board -> model.addAttribute("vo",board));
    }
    @PostMapping("/modify")
    public String modifyPost(WebBoard board, PageVO vo, RedirectAttributes rttr ){
        boardRepository.findById(board.getBno()).ifPresent( origin ->{
            origin.setTitle(board.getTitle());
            origin.setContent(board.getContent());
            boardRepository.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("bno", origin.getBno());
        });

        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/boards/view";
    }

    @PostMapping("/delete")
    public String delete(Long bno, PageVO pageVO, RedirectAttributes rttr){
        boardRepository.deleteById(bno);
        rttr.addFlashAttribute("msg","deletesuccess");

        rttr.addAttribute("page", pageVO.getPage());
        rttr.addAttribute("size", pageVO.getSize());
        rttr.addAttribute("type", pageVO.getType());
        rttr.addAttribute("keyword", pageVO.getKeyword());
        return "redirect:/boards/list";
    }
}
