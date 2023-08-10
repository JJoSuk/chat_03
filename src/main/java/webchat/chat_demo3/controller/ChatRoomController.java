package webchat.chat_demo3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchat.chat_demo3.dto.ChatRoomDto;
import webchat.chat_demo3.dto.RoomConfigDTO;
import webchat.chat_demo3.service.ChatRepository;

@Controller
@Slf4j
public class ChatRoomController {

    // ChatRepository Bean 가져오기
    @Autowired
    private ChatRepository chatRepository;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String roomName,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("secretChk") String secretChk,
                             RedirectAttributes attr) {

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDto room;

        room = chatRepository.createChatRoom(roomName, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt));
        log.info("CREATE Chat Room {}", room);
        attr.addFlashAttribute("roomName", room);

        return "redirect:/";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId){

        log.info("roomId {}", roomId);

        ChatRoomDto room = chatRepository.findRoomById(roomId);
        model.addAttribute("room", room);

        return "chatroom";
    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd){

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatRepository.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 설정 변경
    @PostMapping("/chat/updateRoom")
    public ResponseEntity<?> updateRoom(@RequestBody RoomConfigDTO configData) {
        // 방 설정을 변경하는 로직
        // 예: chatRepository.updateRoomConfig(configData);
        return ResponseEntity.ok().body("방 설정이 변경되었습니다.");
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId){

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatRepository.delChatRoom(roomId);

        return "redirect:/";
    }

    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return chatRepository.chkRoomUserCnt(roomId);
    }
}