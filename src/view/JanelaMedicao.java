package view;

import controller.MedicaoController;
import controller.SensorController;
import model.Medicao;
import model.Sensor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JanelaMedicao extends JFrame {
    private MedicaoController controller = new MedicaoController();
    private SensorController sensorController = new SensorController();

    private JPanel painelDados;
    private JPanel painelResultado;

    private JLabel labelId;
    private JTextField textoId;
    private JLabel labelValor;
    private JTextField textoValor;
    private JLabel labelUnidade;
    private JTextField textoUnidade;
    private JLabel labelDataHora;
    private JTextField textoDataHora;
    private JLabel labelSensor;
    private JComboBox<String> comboSensor;

    private JButton botaoSalvar;
    private JButton botaoConsultar;
    private JButton botaoLimpar;
    private JButton botaoAtualizarSensores;

    private JTextArea textoResultado;
    private JScrollPane scrollResultado;

    public JanelaMedicao(){
        initComponents();
    }

    private void initComponents(){
        painelDados = new JPanel();
        painelResultado = new JPanel();

        labelId = new JLabel();
        textoId = new JTextField();
        labelValor = new JLabel();
        textoValor = new JTextField();
        labelUnidade = new JLabel();
        textoUnidade = new JTextField();
        labelDataHora = new JLabel();
        textoDataHora = new JTextField();
        labelSensor = new JLabel();
        comboSensor = new JComboBox<>();

        botaoSalvar = new JButton();
        botaoConsultar = new JButton();
        botaoLimpar = new JButton();
        botaoAtualizarSensores = new JButton();

        textoResultado = new JTextArea();

        setTitle("Cadastro de Medição");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(235, 239, 245));

        painelDados.setLayout(null);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados da Medição"));
        painelDados.setBackground(Color.WHITE);
        painelDados.setBounds(20, 20, 560, 240);
        add(painelDados);

        painelResultado.setLayout(null);
        painelResultado.setBorder(BorderFactory.createTitledBorder("Medições Cadastradas"));
        painelResultado.setBackground(Color.WHITE);
        painelResultado.setBounds(20, 280, 560, 190);
        add(painelResultado);

        labelId.setText("ID:");
        labelId.setBounds(30, 35, 90, 25);
        painelDados.add(labelId);

        textoId.setColumns(10);
        textoId.setEditable(false);
        textoId.setBounds(130, 35, 150, 25);
        painelDados.add(textoId);

        labelSensor.setText("Sensor:");
        labelSensor.setBounds(310, 35, 90, 25);
        painelDados.add(labelSensor);

        comboSensor.setBounds(410, 35, 120, 25);
        painelDados.add(comboSensor);

        labelValor.setText("Valor:");
        labelValor.setBounds(30, 85, 90, 25);
        painelDados.add(labelValor);

        textoValor.setColumns(20);
        textoValor.setBounds(130, 85, 150, 25);
        painelDados.add(textoValor);

        labelUnidade.setText("Unidade:");
        labelUnidade.setBounds(310, 85, 90, 25);
        painelDados.add(labelUnidade);

        textoUnidade.setColumns(20);
        textoUnidade.setBounds(410, 85, 120, 25);
        painelDados.add(textoUnidade);

        labelDataHora.setText("Data/Hora:");
        labelDataHora.setBounds(30, 135, 90, 25);
        painelDados.add(labelDataHora);

        textoDataHora.setColumns(20);
        textoDataHora.setBounds(130, 135, 150, 25);
        painelDados.add(textoDataHora);

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(evt -> {
            try{
                if(comboSensor.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(this, "Selecione um sensor válido para a medição");
                    return;
                }

                String codigoSensor = comboSensor.getSelectedItem().toString();
                double valor = Double.parseDouble(textoValor.getText().replace(",", "."));
                String unidade = textoUnidade.getText();
                String dataHora = textoDataHora.getText();

                if(unidade.isEmpty() || dataHora.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                }else{
                    String mensagem = controller.cadastrarMedicao(codigoSensor, valor, unidade, dataHora);
                    JOptionPane.showMessageDialog(this, mensagem);
                    mostrarMedicoes(codigoSensor);
                }
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Digite um valor numérico.");
            }
        });
        botaoSalvar.setBounds(30, 175, 110, 30);
        painelDados.add(botaoSalvar);

        botaoConsultar.setText("Consultar");
        botaoConsultar.addActionListener(evt -> {
            if(comboSensor.getSelectedItem() == null){
                textoResultado.setText("Selecione um sensor válido para a medição");
            }else{
                String codigoSensor = comboSensor.getSelectedItem().toString();
                mostrarMedicoes(codigoSensor);
            }
        });
        botaoConsultar.setBounds(155, 175, 110, 30);
        painelDados.add(botaoConsultar);

        botaoLimpar.setText("Limpar");
        botaoLimpar.addActionListener(evt -> limparCampos());
        botaoLimpar.setBounds(280, 175, 110, 30);
        painelDados.add(botaoLimpar);

        botaoAtualizarSensores.setText("Atualizar");
        botaoAtualizarSensores.addActionListener(evt -> carregarSensores());
        botaoAtualizarSensores.setBounds(405, 175, 125, 30);
        painelDados.add(botaoAtualizarSensores);

        textoResultado.setColumns(20);
        textoResultado.setRows(5);
        textoResultado.setEditable(false);
        textoResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));

        scrollResultado = new JScrollPane(textoResultado);
        scrollResultado.setBounds(20, 30, 520, 140);
        painelResultado.add(scrollResultado);

        setSize(620, 540);
        setLocationRelativeTo(null);
        carregarSensores();
    }

    private void carregarSensores(){
        ArrayList<Sensor> sensores = sensorController.listarSensores();
        comboSensor.removeAllItems();

        for(Sensor sensor : sensores){
            comboSensor.addItem(sensor.getCodigo());
        }
    }

    private void mostrarMedicoes(String codigoSensor){
        ArrayList<Medicao> medicoes = controller.consultarMedicao(codigoSensor);
        String texto = "";

        if(medicoes.isEmpty()){
            texto = "Nenhuma medição cadastrada.";
        }else{
            for(Medicao medicao : medicoes){
                texto += "ID: " + medicao.getId() +
                        " | Valor: " + medicao.getValor() +
                        " | Unidade: " + medicao.getUnidade() +
                        " | Data/Hora: " + medicao.getDataHora() +
                        " | Sensor: " + medicao.getSensor().getCodigo() + "\n";
            }
        }

        textoResultado.setText(texto);
    }

    private void limparCampos(){
        textoId.setText("");
        textoValor.setText("");
        textoUnidade.setText("");
        textoDataHora.setText("");
        textoResultado.setText("");
    }
}
