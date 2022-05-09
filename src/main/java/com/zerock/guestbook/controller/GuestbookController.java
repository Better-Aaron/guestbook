package com.zerock.guestbook.controller;

import com.zerock.guestbook.dto.GuestbookDTO;
import com.zerock.guestbook.dto.PageRequestDTO;
import com.zerock.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@RequiredArgsConstructor
@Log4j2
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list.................." + pageRequestDTO);

        model.addAttribute("result", guestbookService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        Long id = guestbookService.register(dto);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("id: ????????????????? " + id);
        GuestbookDTO dto = guestbookService.read(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify ................");
        log.info("dto: " + dto);

        guestbookService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("id", dto.getId());

        return "redirect:/guestbook/read";
    }

    @PostMapping("remove")
    public String remove(long id, RedirectAttributes redirectAttributes) {
        log.info("id: " + id);
        guestbookService.remove(id);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/guestbook/list";
    }
}
