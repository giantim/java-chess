package chess.domain;

import chess.controller.dto.BoardScoreDto;
import chess.controller.dto.TileDto;
import chess.domain.board.Board;
import chess.domain.board.BoardScore;
import chess.domain.board.Tile;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.domain.strategy.direction.Direction;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ChessRunner {
    private Board board;
    private Team currentTeam;

    public ChessRunner() {
        this.board = new Board();
        this.currentTeam = Team.WHITE;
    }

    public ChessRunner(Map<String, String> pieceOnBoards) {
        this.board = Board.webBoard(pieceOnBoards);
        this.currentTeam = Team.WHITE;
    }

    public void update(String source, String target) {
        Position sourcePosition = Position.of(source);
        Position targetPosition = Position.of(target);
        Piece sourcePiece = getSourcePiece(sourcePosition);

        checkCorrectTurn(sourcePiece);
        checkUpdateBoard(sourcePosition, targetPosition, sourcePiece);

        updateBoard(sourcePosition, targetPosition);
        changeTeam();
    }

    private Piece getSourcePiece(Position source) {
        Optional<Piece> sourcePiece = this.board.getPiece(source);
        if (!sourcePiece.isPresent()) {
            throw new IllegalArgumentException("비어있는 위치를 선택했습니다.");
        }

        return sourcePiece.get();
    }

    private void checkCorrectTurn(Piece sourcePiece) {
        if (sourcePiece.isEnemy(this.currentTeam)) {
            throw new IllegalArgumentException("현재 차례가 아닙니다.");
        }
    }

    private void checkUpdateBoard(Position sourcePosition, Position targetPosition, Piece sourcePiece) {
        if (isSamePosition(sourcePosition, targetPosition)) {
            throw new IllegalArgumentException("같은 위치로 이동할 수 없습니다.");
        }

        if (!(sourcePiece.movable(sourcePosition, targetPosition))) {
            throw new IllegalArgumentException("선택한 기물이 이동할 수 없는 곳입니다.");
        }

        if (!isEmptyPath(sourcePosition, targetPosition)) {
            throw new IllegalArgumentException("경로 사이에 장애물이 있습니다.");
        }

        if (!isMovableTarget(sourcePiece, targetPosition)) {
            throw new IllegalArgumentException("목적지가 잘못되었습니다.");
        }
    }

    private boolean isSamePosition(final Position sourcePosition, final Position targetPosition) {
        return sourcePosition.equals(targetPosition);
    }

    private boolean isEmptyPath(final Position sourcePosition, final Position targetPosition) {
        Direction direction = Direction.findDirection(sourcePosition, targetPosition);
        List<Position> path = direction.findPath(sourcePosition, targetPosition);

        if (path.isEmpty()) {
            return true;
        }
        return path.stream()
                .allMatch(this.board::isEmpty);
    }

    private boolean isMovableTarget(final Piece sourcePiece, final Position targetPosition) {
        Optional<Piece> targetPiece = this.board.getPiece(targetPosition);
        return targetPiece.map(sourcePiece::isEnemy).orElse(true);
    }

    private void updateBoard(Position sourcePosition, Position targetPosition) {
        this.board.updateBoard(sourcePosition, targetPosition);
    }

    private void changeTeam() {
        this.currentTeam = this.currentTeam.changeTeam();
    }

    public double calculateScore() {
        BoardScore currentTeamScore = this.board.calculateScore(this.currentTeam);
        return currentTeamScore.getBoardScore();
    }

    public List<BoardScoreDto> calculateScores() {
        List<BoardScoreDto> scores = new ArrayList<>();
        BoardScoreDto currentTeam = new BoardScoreDto(calculateScore());
        currentTeam.setTeam(this.currentTeam.name());
        scores.add(currentTeam);

        BoardScore oppositeTeamScore = this.board.calculateScore(this.currentTeam.changeTeam());
        BoardScoreDto oppositeTeam = new BoardScoreDto(oppositeTeamScore.getBoardScore());
        oppositeTeam.setTeam(this.currentTeam.changeTeam().name());
        scores.add(oppositeTeam);

        return Collections.unmodifiableList(scores);
    }

    public boolean isEndChess() {
        return this.board.getWinner().isPresent();
    }

    public Map<String, String> getBoardEntities() {
        return this.board.parse();
    }

    public String getCurrentTeam() {
        return this.currentTeam.name();
    }

    public String getWinner() {
        Optional<Team> winner = this.board.getWinner();
        return winner.map(Enum::name).orElse(StringUtils.EMPTY);
    }

    public List<TileDto> entireTileDtos() {
        List<TileDto> tileDtos = Position.getPositions().stream()
                .map(TileDto::new)
                .collect(Collectors.toList());

        setTileDtoTeam(tileDtos);
        setTileDtoImage(tileDtos);

        return Collections.unmodifiableList(tileDtos);
    }

    private void setTileDtoTeam(List<TileDto> tileDtos) {
        List<Integer> indexes = Position.getPositionsIndex();
        for (int i = 0; i < indexes.size(); i++) {
            TileDto tileDto = tileDtos.get(i);
            tileDto.setStyle(indexes.get(i));
        }
    }

    private void setTileDtoImage(List<TileDto> tileDtos) {
        List<Tile> tiles = this.board.tiles();
        for (Tile tile : tiles) {
            TileDto tileDto = tileDtos.stream()
                    .filter(td -> td.getPosition().equals(tile.position()))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
            tileDto.setPieceImageUrl(tile.pieceImageUrl());
        }
    }

    public List<TileDto> pieceTileDtos() {
        List<TileDto> tileDtos = this.board.tiles().stream()
                .map((tile) -> {
                    TileDto tileDto = new TileDto(tile.position());
                    tileDto.setPieceImageUrl(tile.pieceImageUrl());
                    return tileDto;
                }).collect(Collectors.toList());

        return Collections.unmodifiableList(tileDtos);
    }
}
