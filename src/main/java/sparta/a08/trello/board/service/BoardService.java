package sparta.a08.trello.board.service;

import sparta.a08.trello.board.dto.*;
import sparta.a08.trello.user.entity.User;

import java.util.List;


public interface BoardService {

    /**
     * Board 생성
     * @param user Board 생성 요청자
     * @param request Board 생성 요청 정보
     * @return Board 생성 결과
     * */
    BoardResponse createBoard(User user, BoardRequest request);

    /**
     * Board 정보 수정
     * @param user Board 정보 수정 요청자
     * @param request Board 정보 수정 요청 정보
     * @param boardId Board 정보 수정 대상 Board
     * @return Board 정보 수정 결과
     * */
    BoardResponse updateBoard(User user, BoardRequest request, Long boardId);

    /**
     * Board 배경색 수정
     * @param user Board 배경색 수정 요청자
     * @param boardId Board 배경색 수정 대상 Board
     * @param request Board 배경색 수정 요청 배경색 파일명
     * @param type Board 배경색 수정 요청 배경색 파일 타입
     * @return Board 배경색 수정 결과
     * */
    BoardColorResponse updateBoardColor(User user, BoardColorRequest request, Long boardId, String type);

    /**
     * Board 삭제
     * @param user Board 삭제 요청자
     * @param boardId Board 삭제 대상 Board
     * @return Board 삭제 결과
     * */
    BoardResponse deleteBoard(User user, Long boardId);

    /**
     * Board 사용자 초대 요청
     * @param user Board 사용자 초대 요청자
     * @param request Board 사용자 초대 대상자 이메일 리스트
     * @param boardId Board 사용자 초대 대상 Board
     * */
    void inviteUserBoard(User user, List<UserBoardInviteRequest> request, Long boardId);

    /**
     * Board 사용자 추가
     * @param boardId Board 사용자 추가 대상 Board
     * @param email Board 사용자 추가 대상자 이메일
     * */
    void createUserBoard(Long boardId, String email);
}