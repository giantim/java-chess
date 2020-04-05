package chess.controller.dto;

public class BoardScoreDto {
    private final double boardScore;
    private String team;

    public BoardScoreDto(final double boardScore) {
        this.boardScore = boardScore;
    }

    public void setTeam(final String team) {
        this.team = team;
    }

    public double getBoardScore() {
        return boardScore;
    }

    public String getTeam() {
        return this.team;
    }
}
