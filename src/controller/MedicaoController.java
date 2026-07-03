package controller;

import dao.MedicaoDao;
import dao.SensorDao;
import model.Medicao;
import model.Sensor;

import java.util.ArrayList;

public class MedicaoController {
    private MedicaoDao dao = new MedicaoDao();
    private SensorDao sensorDao = new SensorDao();

    public String cadastrarMedicao(String codigoSensor, double valor, String unidade, String dataHora){
        Sensor sensor = sensorDao.consultarSensor(codigoSensor);

        if(sensor == null){
            return "Selecione um sensor válido para a medição";
        }

        Medicao medicao = new Medicao();
        medicao.setSensor(sensor);
        medicao.setValor(valor);
        medicao.setUnidade(unidade);
        medicao.setDataHora(dataHora);
        dao.inserirMedicao(medicao);
        return "Medição registrada com sucesso!";
    }

    public ArrayList<Medicao> consultarMedicao(String codigoSensor){
        Sensor sensor = sensorDao.consultarSensor(codigoSensor);

        if(sensor == null){
            return new ArrayList<>();
        }

        return dao.consultarMedicao(sensor);
    }

}
