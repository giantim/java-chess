package chess.domain.strategy.initialize;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Rook;
import chess.domain.piece.Team;
import chess.domain.position.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class RookInitializer implements InitializeStrategy {
    @Override
    public Map<Position, Piece> initialize() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of("a8"), new Rook(PieceType.ROOK, Team.BLACK));
        pieces.put(Position.of("a1"), new Rook(PieceType.ROOK, Team.WHITE));
        pieces.put(Position.of("h8"), new Rook(PieceType.ROOK, Team.BLACK));
        pieces.put(Position.of("h1"), new Rook(PieceType.ROOK, Team.WHITE));

        return Collections.unmodifiableMap(pieces);
    }
}