package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Contato;

public class ContatoDAO {
    private StringBuilder sql;

    public List<Contato> findAll(Long idUsuario) throws SQLException{
        List<Contato> contatos = new ArrayList<>();
        sql = new StringBuilder();
        sql.append("SELECT c.*, u.id as id_usuario FROM contato c, usuario u WHERE c.id_usuario = u.id AND u.id = ?;");

        PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
        preparedStatement.setLong(1, idUsuario);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Contato c = new Contato();
            c.setId(resultSet.getLong("id"));
            c.setNome(resultSet.getString("nome"));
            c.setTelefone(resultSet.getString("telefone"));
            c.setFoto(resultSet.getString("foto"));
            c.setIdUsuario(resultSet.getLong("id_usuario"));
            contatos.add(c);
        }
        preparedStatement.close();
        return contatos;
    }

    public Optional<Contato> findById(Long id, Long idUsuario){
        Contato contato = null;

        try{
            sql = new StringBuilder();
            sql.append("SELECT * FROM contato WHERE id = ? AND id_usuario = ?;");

            PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                contato = new Contato();
                contato.setId(resultSet.getLong("id"));
                contato.setNome(resultSet.getString("nome"));
                contato.setTelefone(resultSet.getString("telefone"));
                contato.setFoto(resultSet.getString("foto"));
            }

            preparedStatement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(contato);
    }

    public boolean salvar(Contato contato){
        try(Connection connection = new ConectaDB().getConnection2()){
            sql = new StringBuilder();
            sql.append("INSERT INTO contato(nome,telefone,foto,id_usuario) VALUES(?,?,?,?);");

            PreparedStatement ps = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, contato.getNome());
            ps.setString(2, contato.getTelefone());
            ps.setString(3, contato.getFoto());
            ps.setLong(4, contato.getIdUsuario());
            int result = ps.executeUpdate();
            ps.close();

            if (result == 0)
                return Boolean.FALSE;

            return Boolean.TRUE;
        }catch(SQLException ex) {
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public boolean deletar(Long idContato){
        try{
            sql = new StringBuilder();
            sql.append("DELETE FROM contato WHERE id = ?;");

            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setLong(1, idContato);
            int result = ps.executeUpdate();
            ps.close();

            if (result == 0)
                return Boolean.FALSE;

            return Boolean.TRUE;
        }catch(SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public boolean atualizar(Contato contato){
        try{
            sql = new StringBuilder();
            sql.append("UPDATE contato SET nome = ?, telefone = ? WHERE id = ?;");

            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setString(1, contato.getNome());
            ps.setString(2, contato.getTelefone());
            ps.setLong(3, contato.getId());
            int result = ps.executeUpdate();
            ps.close();

            if (result == 0)
                return Boolean.FALSE;

            return Boolean.TRUE;
        }catch(SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
