package sparta.a08.trello.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sparta.a08.trello.board.dto.BoardColorRequest;
import sparta.a08.trello.board.dto.BoardColorResponse;
import sparta.a08.trello.board.dto.BoardRequest;
import sparta.a08.trello.board.dto.BoardResponse;
import sparta.a08.trello.board.entity.Board;
import sparta.a08.trello.board.entity.UserBoard;
import sparta.a08.trello.board.entity.UserBoardPK;
import sparta.a08.trello.board.entity.enums.BoardColor;
import sparta.a08.trello.board.entity.enums.UserBoardRole;
import sparta.a08.trello.board.repository.BoardRepository;
import sparta.a08.trello.board.repository.UserBoardRepository;
import sparta.a08.trello.common.cloud.s3.S3Const;
import sparta.a08.trello.common.cloud.s3.S3Util;
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

    private final S3Util s3Util;

    @Override
    @Transactional
    public BoardResponse createBoard(User user, BoardRequest request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .build();

        //Board 생성
        boardRepository.save(board);

        //UserBoard 생성
        createUserBoard(user, board, UserBoardRole.ADMIN);

        return new BoardResponse(board);
    }

    @Override
    @Transactional
    public BoardResponse updateBoard(User user, BoardRequest request, Long boardId) {
        Board findBoard = getBoard(user, boardId);

        findBoard.update(request.getTitle(), request.getContent());

        return new BoardResponse(findBoard);
    }

    @Override
    @Transactional
    public BoardColorResponse updateBoardColor(User user, BoardColorRequest request, Long boardId, String type) {
        Board findBoard = getBoard(user, boardId);

        switch (type) {
            case "service" -> findBoard.setFilename(request.getFilename());
            case "custom" -> {
                MultipartFile file = request.getFile();
                if (!isDefaultColor(file.getOriginalFilename())) {
                    //기존 이미지 삭제
                    s3Util.deleteImage(S3Const.S3_DIR_BOARD, findBoard.getFilename());
                }

                //custom background 업로드
                findBoard.setFilename(s3Util.uploadImage(S3Const.S3_DIR_BOARD, file));
            }
            default -> throw new CustomException(CustomErrorCode.INVALID_COLOR_TYPE_EXCEPTION, 400);
        }

        return new BoardColorResponse(s3Util.getImageURL(S3Const.S3_DIR_BOARD, findBoard.getFilename()));
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

    //Board 배경색 파일명이 기본 배경색 파일명인지 확인
    private boolean isDefaultColor(String filename) {
        for(BoardColor value : BoardColor.values()) {
            if(value.getUrl().equals(filename)) return true;
        }

        return false;
    }
}
