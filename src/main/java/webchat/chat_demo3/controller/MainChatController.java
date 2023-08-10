package webchat.chat_demo3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import webchat.chat_demo3.service.ChatRepository;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainChatController {

    private final ChatRepository chatRepository;

    // 채팅 리스트 화면
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
    @GetMapping("/")
    public String goChatRoom(Model model){

        model.addAttribute("list", chatRepository.findAllRoom());
        // model.addAttribute("user", "hey");
        log.info("SHOW ALL ChatList {}", chatRepository.findAllRoom());
        return "roomlist";
    }
}
