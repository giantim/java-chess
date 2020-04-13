package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.strategy.initialize.*;

import java.util.*;

public class BoardInitializer {
    private static final List<InitializeStrategy> INITIALIZER;

    static {
        INITIALIZER = new ArrayList<>(Arrays.asList(
                new KingInitializer(),
                new QueenInitializer(),
                new RookInitializer(),
                new KnightInitializer(),
                new BishopInitializer(),
                new PawnInitializer()
        ));
    }

    public static Map<Position, Piece> initializeAll() {
        Map<Position, Piece> board = new HashMap<>();

        for (InitializeStrategy strategy : INITIALIZER) {
            board.putAll(strategy.initialize());
        }

        return board;
    }

    public static Map<Position, Piece> webInitialize(Map<String, String> pieceOnBoards) {
        Map<Position, Piece> board = new HashMap<>();

        for (InitializeStrategy strategy : INITIALIZER) {
            board.putAll(strategy.initialize(pieceOnBoards));
        }

        return board;
    }
}
