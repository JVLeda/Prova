package controller;

import dao.SensorDao;
import model.Sensor;

public class SensorController {
    private SensorDao dao = new SensorDao();

    public void cadastrarSensor(String codigo, String  tipo, String localizacao){
        Sensor sensor = new Sensor();
        sensor.setCodigo(codigo);
        sensor.setTipo(tipo);
        sensor.setLocalizacao(localizacao);
        dao.inserirSensor(sensor);
    }

    public void consultarSensor(String codigo){
        dao.consultarSensor(codigo);
    }
}
