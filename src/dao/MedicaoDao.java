package dao;

import model.Medicao;
import model.Sensor;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class MedicaoDao {
    public void inserirMedicao(Medicao medicao){
        String sql = "insert into medicoes(sensor,valor,dataHora) values(?)";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);

        ){
            statement.setObject(1, medicao.getSensor());
            statement.setDouble(1, medicao.getValor());
            statement.setString(1, medicao.getDataHora());
            statement.executeUpdate();
            System.out.println("Medição registrada com sucesso!");
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
    public Sensor consultarMedicao(Sensor sensor){
        String sql = "select * from medicoes where sensor = ?";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);

        ){
            statement.setString(1,sensor.getCodigo());
            PreparedStatement statement1 = conn statement1.executeQuery();
            if(result.next()){
                Sensor sensor1 = new Sensor();
                sensor1.setCodigo(resultSet.getString("codigo"));
                return sensor1;
            }
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }
}
