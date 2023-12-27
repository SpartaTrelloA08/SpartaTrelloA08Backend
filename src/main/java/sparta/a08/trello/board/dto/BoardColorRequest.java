package sparta.a08.trello.board.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BoardColorRequest {
    private String filename;
    private MultipartFile file;
}
