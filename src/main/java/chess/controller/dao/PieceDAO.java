package chess.controller.dao;

import chess.controller.dto.TileDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PieceDAO {
    public void addPiece(int chessBoardId, List<TileDto> tileDtos) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        for (TileDto tileDto : tileDtos) {
            String query = "INSERT INTO piece (position, pieceImageUrl, chessBoardId) "
                    + "VALUES(?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, tileDto.getPosition());
            pstmt.setString(2, tileDto.getPieceImageUrl());
            pstmt.setInt(3, chessBoardId);
            pstmt.executeUpdate();
        }
        ConnectionManager.closeConnection(con);
    }

    public void updatePiece(List<PieceOnBoard> pieces) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        for (PieceOnBoard pieceOnBoard : pieces) {
            String query = "UPDATE piece SET position = ? WHERE pieceId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, pieceOnBoard.getPosition());
            pstmt.setInt(2, pieceOnBoard.getPieceId());
            pstmt.executeUpdate();
        }
        ConnectionManager.closeConnection(con);
    }

    public void updatePiece(PieceOnBoard piece) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String query = "UPDATE piece SET position = ? WHERE pieceId = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, piece.getPosition());
        pstmt.setInt(2, piece.getPieceId());
        pstmt.executeUpdate();
        ConnectionManager.closeConnection(con);
    }

    public void deletePiece(PieceOnBoard a2WhitePawn) throws SQLException {
        if (a2WhitePawn == null) {
            return;
        }

        Connection con = ConnectionManager.getConnection();
        String query = "DELETE FROM piece WHERE pieceId = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, a2WhitePawn.getPieceId());
        pstmt.executeUpdate();
        ConnectionManager.closeConnection(con);
    }

    public List<PieceOnBoard> findPiece(int chessBoardId) throws SQLException {
        List<PieceOnBoard> pieceOnBoards = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String query = "SELECT * FROM piece WHERE chessBoardId = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, chessBoardId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            PieceOnBoard pieceOnBoard = new PieceOnBoard(
                    rs.getInt("pieceId"),
                    rs.getString("position"),
                    rs.getString("pieceImageUrl"),
                    rs.getInt("chessBoardId")
            );
            pieceOnBoards.add(pieceOnBoard);
        }

        return Collections.unmodifiableList(pieceOnBoards);
    }

    public void updatePiece(PieceOnBoard pieceOnBoard, String targetPosition) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String query = "UPDATE piece SET position = ? WHERE pieceId = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, targetPosition);
        pstmt.setInt(2, pieceOnBoard.getPieceId());
        pstmt.executeUpdate();
        ConnectionManager.closeConnection(con);
    }
}
