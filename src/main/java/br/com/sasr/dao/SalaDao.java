package br.com.sasr.dao;

import br.com.sasr.factory.ConnectionFactory;
import br.com.sasr.model.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDao {

    private Connection conexao;

    public SalaDao()  throws SQLException {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void insert(Sala sala) throws SQLException {
        String sql = "INSERT INTO T_SASR_SALA (ID_SALA, NR_SALA, CAP_SALA, REC_SALA)" +
                "VALUES (SQ_SALA.nextval, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, sala.getNumeroSala());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setString(3, sala.getRecursos());
            stmt.executeUpdate();
        }

    }

    public void update(Sala sala) throws SQLException {
        String sql = "UPDATE T_SASR_SALA SET NR_SALA = ?, CAP_SALA = ?, REC_SALA = ?" +
                " WHERE ID_SALA = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, sala.getNumeroSala());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setString(3, sala.getRecursos());
            stmt.setLong(4, sala.getId());
            stmt.executeUpdate();
        }
    }

    public List<Sala> getAll() throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM T_SASR_SALA ORDER BY NR_SALA";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sala sala = new Sala();
                sala.setId(rs.getLong("ID_SALA"));
                sala.setNumeroSala(rs.getString("NR_SALA"));
                sala.setCapacidade(rs.getInt("CAP_SALA"));
                sala.setRecursos(rs.getString("REC_SALA"));
                salas.add(sala);
            }
        }
        return salas;
    }

    public Sala searchById(Long id) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM T_SASR_SALA WHERE ID_SALA = ?");
        stmt.setLong(1, id);

        ResultSet rs = stmt.executeQuery();
        Sala sala = null;

        if (rs.next()) {
            sala = new Sala();
            sala.setId(rs.getLong("ID_SALA"));
            sala.setNumeroSala(rs.getString("NR_SALA"));
            sala.setCapacidade(rs.getInt("CAP_SALA"));
            sala.setRecursos(rs.getString("REC_SALA"));

        }

        return sala;
    }

    public void delete(long idSala) throws SQLException {
        String sql = "DELETE FROM T_SASR_SALA WHERE ID_SALA = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idSala);
            stmt.executeUpdate();
        }
    }

}
