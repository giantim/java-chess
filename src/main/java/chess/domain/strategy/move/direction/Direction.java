package chess.domain.strategy.move.direction;

import chess.domain.position.Position;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum Direction {
    UP(new UpStrategy(), (fileGap, rankGap) -> fileGap == 0 && rankGap > 0),
    DOWN(new DownStrategy(), (fileGap, rankGap) -> fileGap == 0 && rankGap < 0),
    LEFT(new LeftStrategy(), (fileGap, rankGap) -> fileGap < 0 && rankGap == 0),
    RIGHT(new RightStrategy(), (fileGap, rankGap) -> fileGap > 0 && rankGap == 0),
    LEFT_UP(new LeftUpStrategy(), (fileGap, rankGap) -> fileGap < 0 && rankGap > 0),
    LEFT_DOWN(new LeftDownStrategy(), (fileGap, rankGap) -> fileGap < 0 && rankGap < 0),
    RIGHT_UP(new RightUpStrategy(), (fileGap, rankGap) -> fileGap > 0 && rankGap > 0),
    RIGHT_DOWN(new RightDownStrategy(), (fileGap, rankGap) -> fileGap > 0 && rankGap < 0);

    private DirectionStrategy directionStrategy;
    private BiPredicate<Integer, Integer> condition;

    Direction(DirectionStrategy directionStrategy, BiPredicate<Integer, Integer> condition) {
        this.directionStrategy = directionStrategy;
        this.condition = condition;
    }

    public static DirectionStrategy find(Position source, Position target) {
        int fileGap = target.calculateFileGap(source);
        int rankGap = target.calculateRankGap(source);

        return Arrays.stream(values())
                .filter(direction -> direction.condition.test(fileGap, rankGap))
                .findFirst()
                .map(direction -> direction.directionStrategy)
                .orElseThrow(AssertionError::new);
    }
}