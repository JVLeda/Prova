package controller;

import dao.MedicaoDao;
import dao.MedicaoDao;
import model.Medicao;
import model.Sensor;

public class MedicaoController {
    private MedicaoDao dao = new MedicaoDao();

    public void cadastrarMedicao(Sensor sensor, double valor, String unidade, String dataHora){
        Medicao medicao = new Medicao();
        medicao.setSensor(sensor);
        medicao.setValor(valor);
        medicao.setUnidade(unidade);
        medicao.setDataHora(dataHora);
        dao.inserirMedicao(medicao);
    }

    public void consultarMedicao(Sensor sensor){
        dao.consultarMedicao(sensor);
    }
}
