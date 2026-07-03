package controller;

import dao.SensorDao;
import model.Sensor;

import java.util.ArrayList;

public class SensorController {
    private SensorDao dao = new SensorDao();

    public String cadastrarSensor(String codigo, String tipo, String localizacao){
        Sensor sensorExistente = dao.consultarSensor(codigo);
        if(sensorExistente != null){
            return "Já existe um sensor cadastrado com este código.";
        }

        Sensor sensor = new Sensor();
        sensor.setCodigo(codigo);
        sensor.setTipo(tipo);
        sensor.setLocalizacao(localizacao);
        dao.inserirSensor(sensor);
        return "Sensor cadastrado com sucesso!";
    }

    public Sensor consultarSensor(String codigo){
        return dao.consultarSensor(codigo);
    }

    public ArrayList<Sensor> listarSensores(){
        return dao.listarSensores();
    }
}
