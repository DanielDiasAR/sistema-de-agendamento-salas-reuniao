package br.com.sasr.dao;

import br.com.sasr.factory.ConnectionFactory;
import br.com.sasr.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDao {
    private Connection conexao;

    public AgendamentoDao() throws SQLException {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void insert(Agendamento agendamento) throws SQLException {
        String sql = "INSERT INTO T_SASR_AGENDAMENTO (ID_AGENDAMENTO, ID_SALA, ID_COLABORADOR, DS_TITULO, DT_AGENDAMENTO, HR_INICIO, HR_FIM) " +
                "VALUES (SQ_AGENDAMENTO.nextval, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, agendamento.getSala().getId());
            stmt.setLong(2, agendamento.getColaborador().getId());
            stmt.setString(3, agendamento.getTitulo());
            stmt.setDate(4, Date.valueOf(agendamento.getData()));
            stmt.setTimestamp(5, Timestamp.valueOf(agendamento.getData().atTime(agendamento.getHoraInicio())));
            stmt.setTimestamp(6, Timestamp.valueOf(agendamento.getData().atTime(agendamento.getHoraFim())));
            stmt.executeUpdate();
        }
    }

    public void update(Agendamento agendamento) throws SQLException {
        String sql = "UPDATE T_SASR_AGENDAMENTO SET ID_SALA = ?, ID_COLABORADOR = ?, DS_TITULO = ?, " +
                "DT_AGENDAMENTO = ?, HR_INICIO = ?, HR_FIM = ? WHERE ID_AGENDAMENTO = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, agendamento.getSala().getId());
            stmt.setLong(2, agendamento.getColaborador().getId());
            stmt.setString(3, agendamento.getTitulo());
            stmt.setDate(4, Date.valueOf(agendamento.getData()));
            stmt.setTimestamp(5, Timestamp.valueOf(agendamento.getData().atTime(agendamento.getHoraInicio())));
            stmt.setTimestamp(6, Timestamp.valueOf(agendamento.getData().atTime(agendamento.getHoraFim())));
            stmt.setLong(7, agendamento.getId());
            stmt.executeUpdate();
        }
    }

    public List<Agendamento> getAll() throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, s.NR_SALA, c.NM_COLABORADOR FROM T_SASR_AGENDAMENTO a " +
                "JOIN T_SASR_SALA s ON a.ID_SALA = s.ID_SALA " +
                "JOIN T_SASR_COLABORADOR c ON a.ID_COLABORADOR = c.ID_COLABORADOR ORDER BY a.DT_AGENDAMENTO, a.HR_INICIO";
        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(parseAgendamento(rs));
            }
        }
        return lista;
    }

    public Agendamento searchById(long id) throws SQLException {
        String sql = "SELECT a.*, s.NR_SALA, c.NM_COLABORADOR FROM T_SASR_AGENDAMENTO a " +
                "JOIN T_SASR_SALA s ON a.ID_SALA = s.ID_SALA " +
                "JOIN T_SASR_COLABORADOR c ON a.ID_COLABORADOR = c.ID_COLABORADOR WHERE a.ID_AGENDAMENTO = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return parseAgendamento(rs);
            }
        }
        return null;
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM T_SASR_AGENDAMENTO WHERE ID_AGENDAMENTO = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Agendamento buscarConflito(Long idSala, Date data, Timestamp inicio, Timestamp fim, Long idIgnorar) throws SQLException {
        String sql = "SELECT * FROM T_SASR_AGENDAMENTO WHERE ID_SALA = ? AND DT_AGENDAMENTO = ? " +
                "AND (HR_INICIO < ? AND HR_FIM > ?) AND (ID_AGENDAMENTO <> ? OR ? IS NULL)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, idSala);
            stmt.setDate(2, data);
            stmt.setTimestamp(3, fim);
            stmt.setTimestamp(4, inicio);
            if (idIgnorar != null) stmt.setLong(5, idIgnorar); else stmt.setNull(5, Types.BIGINT);
            if (idIgnorar != null) stmt.setLong(6, idIgnorar); else stmt.setNull(6, Types.BIGINT);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setHoraInicio(rs.getTimestamp("HR_INICIO").toLocalDateTime().toLocalTime());
                    a.setHoraFim(rs.getTimestamp("HR_FIM").toLocalDateTime().toLocalTime());
                    return a;
                }
            }
        }
        return null;
    }

    private Agendamento parseAgendamento(ResultSet rs) throws SQLException {
        Agendamento a = new Agendamento();
        a.setId(rs.getLong("ID_AGENDAMENTO"));
        a.setTitulo(rs.getString("DS_TITULO"));
        a.setData(rs.getDate("DT_AGENDAMENTO").toLocalDate());
        a.setHoraInicio(rs.getTimestamp("HR_INICIO").toLocalDateTime().toLocalTime());
        a.setHoraFim(rs.getTimestamp("HR_FIM").toLocalDateTime().toLocalTime());

        Sala s = new Sala();
        s.setId(rs.getLong("ID_SALA"));
        s.setNumeroSala(rs.getString("NR_SALA"));
        a.setSala(s);

        Colaborador c = new Colaborador();
        c.setId(rs.getLong("ID_COLABORADOR"));
        c.setNome(rs.getString("NM_COLABORADOR"));
        a.setColaborador(c);

        return a;
    }
}