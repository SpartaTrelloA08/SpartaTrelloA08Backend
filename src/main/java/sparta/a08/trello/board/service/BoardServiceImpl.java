package sparta.a08.trello.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.a08.trello.board.dto.BoardColorResponse;
import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.dto.BoardResponse;
import sparta.a08.trello.board.entity.Board;
import sparta.a08.trello.board.entity.UserBoard;
import sparta.a08.trello.board.entity.UserBoardPK;
import sparta.a08.trello.board.entity.UserBoardRole;
import sparta.a08.trello.board.repository.BoardRepository;
import sparta.a08.trello.board.repository.UserBoardRepository;
import sparta.a08.trello.common.exception.CustomErrorCode;
import sparta.a08.trello.common.exception.CustomException;
import sparta.a08.trello.user.entity.User;

import java.util.Optional;

@Service
@Slf4j(topic = "BoardService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;

    @Override
    @Transactional
    public BoardResponse createBoard(User user, BoardRequest boardRequest) {
        Board board = Board.builder()
                .title(boardRequest.getTitle())
                .build();

        //Board 생성
        boardRepository.save(board);

        //UserBoard 생성
        createUserBoard(user, board, UserBoardRole.ADMIN);

        return new BoardResponse(board);
    }

    @Override
    @Transactional
    public BoardResponse updateBoard(User user, BoardRequest boardRequest, Long boardId) {
        Board findBoard = getBoard(user, boardId);

        findBoard.update(boardRequest.getTitle(), boardRequest.getContent());

        return new BoardResponse(findBoard);
    }

    @Override
    @Transactional
    public BoardColorResponse updateBoardColor(User user, Long boardId, String filename) {
        Board findBoard = getBoard(user, boardId);

        // board color update
        // s3 로직 추가 필요

        return new BoardColorResponse(findBoard.getImageURL());
    }

    @Override
    @Transactional
    public BoardResponse deleteBoard(User user, Long boardId) {
        Board findBoard = getBoard(user, boardId);

        boardRepository.delete(findBoard);

        return new BoardResponse(findBoard);
    }

    private void createUserBoard(User user, Board board, UserBoardRole role) {
        UserBoard userBoard = UserBoard.builder()
                .user(user)
                .board(board)
                .role(role)
                .build();

        userBoardRepository.save(userBoard);
    }

    //ADMIN 권한이 필요한 서비스 기능에 적용
    private Board getBoard(User user, Long boardId) {
        //Board 조회
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.BOARD_NOT_FOUND_EXCEPTION, 404));

        //UserBoard 조회
        Optional<UserBoard> findUserBoard = userBoardRepository.findById(UserBoardPK.builder()
                .userId(user.getId())
                .boardId(findBoard.getId())
                .build()
        );

        //ADMIN 권한 확인 및 Board 구성원 확인
        if(findUserBoard.isPresent()) {
            if(!UserBoardRole.ADMIN.equals(findUserBoard.get().getRole()))
                throw new CustomException(CustomErrorCode.NOT_ALLOWED_TO_UPDATE_BOARD_EXCEPTION, 403);
        } else {
            throw new CustomException(CustomErrorCode.NOT_ALLOWED_TO_UPDATE_BOARD_EXCEPTION, 403);
        }

        return findBoard;
    }
}
