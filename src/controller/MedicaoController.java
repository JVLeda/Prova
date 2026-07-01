package controller;

import dao.MedicaoDao;
import dao.MedicaoDao;
import model.Medicao;
import model.Sensor;

public class MedicaoController {
    private MedicaoDao dao = new MedicaoDao();

    public void cadastrarMedicao(Sensor sensor, String valor, String unidade, String dataHora){
        Medicao medicao = new Medicao();
        sensor.setSensor(sensor);
        sensor.setValor(valor);
        sensor.setUnidade(unidade);
        Sensor.setDataHora(dataHora)
        dao.inserirMedicao(medicao);
    }

    public void consultarMedicao(Sensor sensor){
        dao.consultarMedicao(sensor);
    }
}
