package sparta.a08.trello.card.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Builder
public class CardColorRequest {
    private String filename;
    private MultipartFile file;

}
