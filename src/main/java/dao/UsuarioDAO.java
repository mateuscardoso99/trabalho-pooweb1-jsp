package dao;

import model.Usuario;
import utils.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO {
    private StringBuilder sql;

    public Optional<Usuario> findById(Long id) throws SQLException{
        Usuario usuario = null;

        sql = new StringBuilder();
        sql.append("SELECT * FROM usuario WHERE id = ?;");

        PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            usuario = new Usuario();
            usuario.setId(resultSet.getLong("id"));
            usuario.setNome(resultSet.getString("nome"));
            usuario.setEmail(resultSet.getString("email"));
            usuario.setSenha(resultSet.getString("senha"));
        }

        preparedStatement.close();

        return Optional.of(usuario);
    }

    public Optional<Usuario> findByEmail(String email){
        try(Connection conn = ConectaDB.getConnection()){
            sql = new StringBuilder();
            sql.append("SELECT * FROM usuario WHERE email = ?;");

            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            Usuario usuario = null;

            if(resultSet.next()){
                usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
            }

            preparedStatement.close();
            return Optional.ofNullable(usuario);

        }catch (SQLException ex){
            ex.printStackTrace();
            return Optional.ofNullable(null);
        }

    }

    public boolean salvar(Usuario usuario){
        try(Connection connection = new ConectaDB().getConnection2()){
            sql = new StringBuilder();
            sql.append("INSERT INTO usuario(nome,email,senha) VALUES(?,?,?);");

            PreparedStatement ps = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
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

    public boolean deletar(Usuario usuario) throws SQLException{
        sql = new StringBuilder();
        sql.append("DELETE FROM usuario WHERE id = ?;");

        PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
        ps.setLong(1, usuario.getId());
        int result = ps.executeUpdate();
        ps.close();

        if (result == 0)
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public boolean atualizar(Usuario usuario){
        sql = new StringBuilder();
        sql.append("UPDATE usuario SET nome = ?, email = ?");

        if(Validator.isEmptyOrNull(usuario.getSenha()))
            sql.append(" WHERE id = ?;");
        else
            sql.append(", senha = ? WHERE id = ?;");

        try{
            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());

            if(Validator.isEmptyOrNull(usuario.getSenha())){
                ps.setLong(3, usuario.getId());
            }
            else{
                ps.setString(3, usuario.getSenha());
                ps.setLong(4, usuario.getId());
            }

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
