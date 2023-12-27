package sparta.a08.trello.board.service;

import sparta.a08.trello.board.dto.BoardColorResponse;
import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.dto.BoardResponse;
import sparta.a08.trello.board.entity.Board;
import sparta.a08.trello.user.entity.User;

public interface BoardService {

    /**
     * Board 생성
     * @param user Board 생성 요청자
     * @param boardRequest Board 생성 요청 정보
     * @return Board 생성 결과
     * */
    BoardResponse createBoard(User user, BoardRequest boardRequest);

    /**
     * Board 정보 수정
     * @param user Board 정보 수정 요청자
     * @param boardRequest Board 정보 수정 요청 정보
     * @param boardId Board 정보 수정 대상 Board
     * @return Board 정보 수정 결과
     * */
    BoardResponse updateBoard(User user, BoardRequest boardRequest, Long boardId);

    /**
     * Board 배경색 수정
     * @param user Board 배경색 수정 요청자
     * @param boardId Board 배경색 수정 대상 Board
     * @param filename Board 배경색 수정 요청 배경색 파일명
     * @return Board 배경색 수정 결과
     * */
    BoardColorResponse updateBoardColor(User user, Long boardId, String filename);

    /**
     * Board 삭제
     * @param user Board 삭제 요청자
     * @param boardId Board 삭제 대상 Board
     * @return Board 삭제 결과
     * */
    BoardResponse deleteBoard(User user, Long boardId);
}