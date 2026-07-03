package dao;

import model.Medicao;
import model.Sensor;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicaoDao {
    public void inserirMedicao(Medicao medicao){
        String sql = "insert into medicoes(sensor_id, valor, unidade, dataHora) values(?, ?, ?, ?)";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setInt(1, medicao.getSensor().getId());
            statement.setDouble(2, medicao.getValor());
            statement.setString(3, medicao.getUnidade());
            statement.setString(4, medicao.getDataHora());
            statement.executeUpdate();
            System.out.println("Medição registrada com sucesso!");
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public ArrayList<Medicao> consultarMedicao(Sensor sensor){
        ArrayList<Medicao> medicoes = new ArrayList<>();
        String sql = "select * from medicoes where sensor_id = ? order by id";
        try(
                Connection conn = Conexao.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setInt(1, sensor.getId());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Medicao medicao = new Medicao();
                medicao.setId(resultSet.getInt("id"));
                medicao.setValor(resultSet.getDouble("valor"));
                medicao.setUnidade(resultSet.getString("unidade"));
                medicao.setDataHora(resultSet.getString("dataHora"));
                medicao.setSensor(sensor);
                medicoes.add(medicao);
            }
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return medicoes;
    }
}
