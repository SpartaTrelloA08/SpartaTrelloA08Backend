package sparta.a08.trello.card.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import sparta.a08.trello.card.dto.CardColorRequest;
import sparta.a08.trello.card.dto.CardColorResponse;
import sparta.a08.trello.card.dto.CardRequestDto;
import sparta.a08.trello.card.dto.CardResponseDto;
import sparta.a08.trello.card.service.CardService;
import sparta.a08.trello.columns.dto.CommonResponseDto;
import sparta.a08.trello.columns.dto.PositionRequestDto;
import sparta.a08.trello.common.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/boards/{boardId}/columns/{columnId}/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("")
    public ResponseEntity<CardResponseDto> createCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CardRequestDto cardRequestDto,
            @PathVariable("boardId") Long boardId,
            @PathVariable("columnId") Long columnId
    ) {
        CardResponseDto createdCard = cardService.createCard(userDetails.getUser(), cardRequestDto, boardId, columnId);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<CardResponseDto>> getAllCards(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "boardId") Long boardId,
            @PathVariable(name = "columnId") Long columnId
    ) {
        List<CardResponseDto> Cards = cardService.getAllCards(userDetails.getUser(), boardId, columnId);
        return new ResponseEntity<>(Cards, HttpStatus.OK);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> getCardById(
            @PathVariable(name = "cardId") Long cardId
    ) {
        CardResponseDto cardById = cardService.getCardById(cardId);
        return new ResponseEntity<>(cardById, HttpStatus.OK);
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(
            @PathVariable(name = "cardId") Long cardId,
            @RequestBody CardRequestDto cardRequestDto
    ) {
        CardResponseDto updatedCard = cardService.updateCard(cardId, cardRequestDto);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }

    @PutMapping("/{cardId}/color")
    public ResponseEntity<CardColorResponse> updateCardColor(
            @ModelAttribute CardColorRequest colorRequest,
            @PathVariable(name ="cardId") Long cardId,
            @RequestParam(name="type") String type

    ){
        CardColorResponse updateCardColor = cardService.updateCardColor(colorRequest,cardId,type);
        return new ResponseEntity<>(updateCardColor, HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> deleteCard(
            @PathVariable(name = "cardId") Long cardId
    ) {
        CommonResponseDto deletionResult = cardService.deleteCard(cardId);
        return new ResponseEntity<>(deletionResult, HttpStatus.OK);
    }

    @PutMapping("/{cardId}/move")
    public ResponseEntity<CommonResponseDto> moveCardPosition(
            @PathVariable(name = "cardId") Long cardId,
            @RequestBody PositionRequestDto positionRequestDto) {
        CommonResponseDto moveResponse = cardService.movePosition(cardId, positionRequestDto);
        return new ResponseEntity<>(moveResponse, HttpStatus.OK);
    }

}
