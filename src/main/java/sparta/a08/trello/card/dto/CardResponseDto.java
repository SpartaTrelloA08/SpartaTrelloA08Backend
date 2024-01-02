package sparta.a08.trello.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sparta.a08.trello.card.entity.Card;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long position;
    private String createAt;
    private String lastModifiedAt;
    private String imageURL;
    public static CardResponseDto fromEntity(Card card ,String imageURL) {

        return new CardResponseDto(
                card.getId(),
                card.getTitle(),
                card.getContent(),
                card.getPosition(),
                card.getCreatedAt().toString(),
                card.getLastModifiedAt().toString(),
                imageURL
        );
    }
}
