package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Documento;

public class DocumentoDAO {
    private StringBuilder sql;

    public List<Documento> findAll(Long idUsuario) throws SQLException{
        List<Documento> documentos = new ArrayList<>();
        sql = new StringBuilder();
        sql.append("SELECT doc.*, u.id as id_usuario FROM documento doc, usuario u WHERE doc.id_usuario = u.id AND u.id = ?;");

        PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
        preparedStatement.setLong(1, idUsuario);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Documento doc = new Documento();
            doc.setId(resultSet.getLong("id"));
            doc.setArquivo(resultSet.getString("arquivo"));
            doc.setIdUsuario(resultSet.getLong("id_usuario"));
            documentos.add(doc);
        }
        preparedStatement.close();
        return documentos;
    }

    public Optional<Documento> findById(Long id, Long idUsuario){
        Documento documento = null;

        try{
            sql = new StringBuilder();
            sql.append("SELECT * FROM documento WHERE id = ? AND id_usuario = ?;");

            PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                documento = new Documento();
                documento.setId(resultSet.getLong("id"));
                documento.setArquivo(resultSet.getString("arquivo"));
            }

            preparedStatement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(documento);
    }

    public boolean salvar(Documento documento){
        try(Connection connection = new ConectaDB().getConnection2()){
            sql = new StringBuilder();
            sql.append("INSERT INTO documento(arquivo,id_usuario) VALUES(?,?);");

            PreparedStatement ps = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, documento.getArquivo());
            ps.setLong(2, documento.getIdUsuario());
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

    public boolean deletar(Long idDoc){
        try{
            sql = new StringBuilder();
            sql.append("DELETE FROM documento WHERE id = ?;");

            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setLong(1, idDoc);
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

    public boolean atualizar(Documento documento){
        try{
            sql = new StringBuilder();
            sql.append("UPDATE documento SET arquivo = ? WHERE id = ?;");

            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setString(1, documento.getArquivo());
            ps.setLong(2, documento.getId());
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
