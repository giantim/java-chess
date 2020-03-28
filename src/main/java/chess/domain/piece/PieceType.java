package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.position.Position;
import chess.domain.strategy.move.*;

public enum PieceType {
    KING("k", 0, new KingMoveStrategy()),
    QUEEN("q", 9, new QueenMoveStrategy()),
    ROOK("r", 5, new RookMoveStrategy()),
    BISHOP("b", 3, new BishopMoveStrategy()),
    KNIGHT("n", 2.5, new KnightMoveStrategy()),
    PAWN("p", 1, new PawnMoveStrategy());

    private final String symbol;
    private final double score;
    private final MoveStrategy moveStrategy;

    PieceType(String symbol, double score, MoveStrategy moveStrategy) {
        this.symbol = symbol;
        this.score = score;
        this.moveStrategy = moveStrategy;
    }

    public boolean movable(final Position source, final Position target, final Board board) {
        return this.moveStrategy.movable(source, target, board);
    }

    public boolean isKing() {
        return this == KING;
    }

    public boolean isPawn() {
        return this == PAWN;
    }

    public double getScore() {
        return score;
    }

    public String getSymbol() {
        return symbol;
    }
}