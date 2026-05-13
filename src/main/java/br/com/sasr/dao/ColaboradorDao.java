package br.com.sasr.dao;

import br.com.sasr.factory.ConnectionFactory;
import br.com.sasr.model.Colaborador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDao {

    private Connection conexao;

    public ColaboradorDao() throws SQLException {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void insert(Colaborador colaborador) throws SQLException {
        String sql = "INSERT INTO T_SASR_COLABORADOR (ID_COLABORADOR, NM_COLABORADOR, DS_DEPARTAMENTO, DS_EMAIL) " +
                "VALUES (SQ_COLABORADOR.nextval, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, colaborador.getNome());
            stmt.setString(2, colaborador.getDepartamento());
            stmt.setString(3, colaborador.getEmail());
            stmt.executeUpdate();
        }
    }

    public void update(Colaborador colaborador) throws SQLException {
        String sql = "UPDATE T_SASR_COLABORADOR SET NM_COLABORADOR = ?, DS_DEPARTAMENTO = ?, DS_EMAIL = ? " +
                "WHERE ID_COLABORADOR = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, colaborador.getNome());
            stmt.setString(2, colaborador.getDepartamento());
            stmt.setString(3, colaborador.getEmail());
            stmt.setLong(4, colaborador.getId());
            stmt.executeUpdate();
        }
    }

    public List<Colaborador> getAll() throws SQLException {
        List<Colaborador> colaboradores = new ArrayList<>();
        String sql = "SELECT * FROM T_SASR_COLABORADOR ORDER BY NM_COLABORADOR";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Colaborador colab = new Colaborador();
                colab.setId(rs.getLong("ID_COLABORADOR"));
                colab.setNome(rs.getString("NM_COLABORADOR"));
                colab.setDepartamento(rs.getString("DS_DEPARTAMENTO"));
                colab.setEmail(rs.getString("DS_EMAIL"));
                colaboradores.add(colab);
            }
        }
        return colaboradores;
    }

    public Colaborador searchById(Long id) throws SQLException {
        String sql = "SELECT * FROM T_SASR_COLABORADOR WHERE ID_COLABORADOR = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Colaborador colab = new Colaborador();
                    colab.setId(rs.getLong("ID_COLABORADOR"));
                    colab.setNome(rs.getString("NM_COLABORADOR"));
                    colab.setDepartamento(rs.getString("DS_DEPARTAMENTO"));
                    colab.setEmail(rs.getString("DS_EMAIL"));
                    return colab;
                }
            }
        }
        return null;
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM T_SASR_COLABORADOR WHERE ID_COLABORADOR = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}