package dao;

import model.Sensor;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SensorDao {
    public void inserirSensor(Sensor sensor){
        String sql = "insert into sensores(codigo,tipo,localizacao) values(?)";
        try(
            Connection conn = Conexao.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

        ){
            statement.setString(1, sensor.getCodigo());
            statement.setString(1, sensor.getTipo());
            statement.setString(1, sensor.getLocalizacao());
            statement.executeUpdate();
            System.out.println("Sensor cadastrado com sucesso!");
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
    public Sensor consultarSensor(String codigo){
        String sql = "select * from sensores where codigo = ?";
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
