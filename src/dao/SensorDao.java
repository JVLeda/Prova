package dao;

import model.Sensor;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SensorDao {
    public void inserirSensor(Sensor sensor){
        String sql = "insert into sensores(codigo, tipo, localizacao) values(?, ?, ?)";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, sensor.getCodigo());
            statement.setString(2, sensor.getTipo());
            statement.setString(3, sensor.getLocalizacao());
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
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, codigo);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Sensor sensor = new Sensor();
                sensor.setId(resultSet.getInt("id"));
                sensor.setCodigo(resultSet.getString("codigo"));
                sensor.setTipo(resultSet.getString("tipo"));
                sensor.setLocalizacao(resultSet.getString("localizacao"));
                return sensor;
            }
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Sensor> listarSensores(){
        ArrayList<Sensor> sensores = new ArrayList<>();
        String sql = "select * from sensores order by id";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()){
                Sensor sensor = new Sensor();
                sensor.setId(resultSet.getInt("id"));
                sensor.setCodigo(resultSet.getString("codigo"));
                sensor.setTipo(resultSet.getString("tipo"));
                sensor.setLocalizacao(resultSet.getString("localizacao"));
                sensores.add(sensor);
            }
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return sensores;
    }
}
