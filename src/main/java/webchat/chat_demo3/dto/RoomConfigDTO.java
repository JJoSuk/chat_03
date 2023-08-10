package webchat.chat_demo3.dto;

import lombok.Data;

@Data
public class RoomConfigDTO {

    private String roomId; // 채팅방 아이디
    private String chPwd; // 채팅방 작업시 필요한 pwd
    private String chRoomName; // 채팅방 이름
    private int chRoomUserCnt; // 채팅방 인원수
    private boolean chSecret; // 채팅방 잠금 여부
}
