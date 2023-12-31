package sparta.a08.trello.common.smtp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MailRequest {
    private String to;
    private String title;
    private String content;
}
