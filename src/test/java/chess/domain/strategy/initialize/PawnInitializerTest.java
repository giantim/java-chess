package chess.domain.strategy.initialize;

import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PawnInitializerTest {
    @DisplayName("db에서 받아온 정보로 킹 초기화")
    @Test
    void webInitializeTest() {
        Map<String, String> pieceOnBoard = new HashMap<>();
        pieceOnBoard.put("a2", "nwhite");
        pieceOnBoard.put("b2", "pwhite");
        pieceOnBoard.put("f6", "Pblack");
        pieceOnBoard.put("c8", "Nblack");
        pieceOnBoard.put("d4", "nwhite");
        InitializeStrategy pawnInitializer = new PawnInitializer();
        Map<Position, Piece> board = pawnInitializer.initialize(pieceOnBoard);

        Assertions.assertThat(board.keySet()).contains(
                Position.of("b2"), Position.of("f6")
        );
        for (Piece piece : board.values()) {
            Assertions.assertThat(piece).isInstanceOf(Pawn.class);
        }
    }
}
