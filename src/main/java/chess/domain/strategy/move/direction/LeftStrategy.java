package chess.domain.strategy.move.direction;

import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class LeftStrategy implements DirectionStrategy {
    @Override
    public List<Position> findPath(Position source, Position target) {
        List<Position> path = new ArrayList<>();

        for (int i = target.getFile() + 1; i < source.getFile(); i++) {
            path.add(Position.of(i, source.getRank()));
        }
        return path;
    }
}