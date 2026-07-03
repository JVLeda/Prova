package model;

public class Medicao {
    private int id;
    private Sensor sensor;
    private double valor;
    private String unidade;
    private String dataHora;

    public Medicao (){}

    public Medicao (int id, double valor, String unidade, String dataHora, Sensor sensor){
        this.id = id;
        this.valor = valor;
        this.unidade = unidade;
        this.dataHora = dataHora;
        this.sensor = sensor;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

}
