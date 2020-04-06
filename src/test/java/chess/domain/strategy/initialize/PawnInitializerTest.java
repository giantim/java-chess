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
        pieceOnBoard.put("a2", "n_white");
        pieceOnBoard.put("b2", "p_white");
        pieceOnBoard.put("f6", "P_black");
        pieceOnBoard.put("c8", "N_black");
        pieceOnBoard.put("d4", "n_white");
        InitializeStrategy pawnInitializer = new PawnInitializer();
        Map<Position, Piece> board = pawnInitializer.webInitialize(pieceOnBoard);

        Assertions.assertThat(board.keySet()).contains(
                Position.of("b2"), Position.of("f6")
        );
        for (Piece piece : board.values()) {
            Assertions.assertThat(piece).isInstanceOf(Pawn.class);
        }
    }
}
