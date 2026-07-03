package view;

import controller.MedicaoController;
import controller.SensorController;
import model.Medicao;
import model.Sensor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JComboBox<Sensor> comboSensor;

    private JButton botaoSalvar;
    private JButton botaoConsultar;
    private JButton botaoLimpar;
    private JButton botaoAtualizarSensores;

    private JTable tabelaMedicoes;
    private DefaultTableModel modeloTabelaMedicoes;
    private JScrollPane scrollTabela;

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

        modeloTabelaMedicoes = new DefaultTableModel(new Object[]{"ID", "Valor", "Unidade", "Data/Hora", "Sensor"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaMedicoes = new JTable(modeloTabelaMedicoes);

        setTitle("Cadastro de Medição");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(235, 239, 245));

        painelDados.setLayout(null);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados da Medição"));
        painelDados.setBackground(Color.WHITE);
        painelDados.setBounds(20, 20, 680, 240);
        add(painelDados);

        painelResultado.setLayout(null);
        painelResultado.setBorder(BorderFactory.createTitledBorder("Medições Cadastradas"));
        painelResultado.setBackground(Color.WHITE);
        painelResultado.setBounds(20, 280, 680, 210);
        add(painelResultado);

        labelId.setText("ID:");
        labelId.setBounds(30, 35, 90, 25);
        painelDados.add(labelId);

        textoId.setColumns(10);
        textoId.setEditable(false);
        textoId.setBounds(130, 35, 160, 25);
        painelDados.add(textoId);

        labelSensor.setText("Sensor:");
        labelSensor.setBounds(330, 35, 90, 25);
        painelDados.add(labelSensor);

        comboSensor.setBounds(430, 35, 200, 25);
        painelDados.add(comboSensor);

        labelValor.setText("Valor:");
        labelValor.setBounds(30, 85, 90, 25);
        painelDados.add(labelValor);

        textoValor.setColumns(20);
        textoValor.setBounds(130, 85, 160, 25);
        painelDados.add(textoValor);

        labelUnidade.setText("Unidade:");
        labelUnidade.setBounds(330, 85, 90, 25);
        painelDados.add(labelUnidade);

        textoUnidade.setColumns(20);
        textoUnidade.setBounds(430, 85, 200, 25);
        painelDados.add(textoUnidade);

        labelDataHora.setText("Data/Hora:");
        labelDataHora.setBounds(30, 135, 90, 25);
        painelDados.add(labelDataHora);

        textoDataHora.setColumns(20);
        textoDataHora.setBounds(130, 135, 160, 25);
        painelDados.add(textoDataHora);

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(evt -> salvarMedicao());
        botaoSalvar.setBounds(30, 175, 110, 30);
        painelDados.add(botaoSalvar);

        botaoConsultar.setText("Consultar");
        botaoConsultar.addActionListener(evt -> consultarMedicao());
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

        tabelaMedicoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMedicoes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                preencherCamposPelaTabela();
            }
        });

        scrollTabela = new JScrollPane(tabelaMedicoes);
        scrollTabela.setBounds(20, 30, 640, 160);
        painelResultado.add(scrollTabela);

        setSize(740, 560);
        setLocationRelativeTo(null);
        carregarSensores();
    }

    private void salvarMedicao(){
        Sensor sensor = (Sensor) comboSensor.getSelectedItem();

        if(sensor == null){
            JOptionPane.showMessageDialog(this, "Selecione um sensor válido para a medição.");
            return;
        }

        try{
            String unidade = textoUnidade.getText().trim();
            String dataHora = textoDataHora.getText().trim();
            double valor = Double.parseDouble(textoValor.getText().replace(",", "."));

            if(unidade.isEmpty() || dataHora.isEmpty()){
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            String mensagem = controller.cadastrarMedicao(sensor, valor, unidade, dataHora);
            JOptionPane.showMessageDialog(this, mensagem);
            mostrarMedicoes(sensor);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Digite um valor numérico.");
        }
    }

    private void consultarMedicao(){
        Sensor sensor = (Sensor) comboSensor.getSelectedItem();

        if(sensor == null){
            JOptionPane.showMessageDialog(this, "Selecione um sensor válido para a medição.");
            limparTabela();
            return;
        }

        mostrarMedicoes(sensor);
    }

    private void carregarSensores(){
        ArrayList<Sensor> sensores = sensorController.listarSensores();
        comboSensor.removeAllItems();

        for(Sensor sensor : sensores){
            comboSensor.addItem(sensor);
        }

        limparTabela();
    }

    private void mostrarMedicoes(Sensor sensor){
        ArrayList<Medicao> medicoes = controller.consultarMedicao(sensor);
        limparTabela();

        for(Medicao medicao : medicoes){
            adicionarMedicaoNaTabela(medicao);
        }
    }

    private void adicionarMedicaoNaTabela(Medicao medicao){
        modeloTabelaMedicoes.addRow(new Object[]{
                medicao.getId(),
                medicao.getValor(),
                medicao.getUnidade(),
                medicao.getDataHora(),
                medicao.getSensor().getCodigo()
        });
    }

    private void limparTabela(){
        modeloTabelaMedicoes.setRowCount(0);
    }

    private void preencherCamposPelaTabela(){
        int linha = tabelaMedicoes.getSelectedRow();
        if(linha >= 0){
            textoId.setText(modeloTabelaMedicoes.getValueAt(linha, 0).toString());
            textoValor.setText(modeloTabelaMedicoes.getValueAt(linha, 1).toString());
            textoUnidade.setText(modeloTabelaMedicoes.getValueAt(linha, 2).toString());
            textoDataHora.setText(modeloTabelaMedicoes.getValueAt(linha, 3).toString());
        }
    }

    private void limparCampos(){
        textoId.setText("");
        textoValor.setText("");
        textoUnidade.setText("");
        textoDataHora.setText("");
        limparTabela();
    }
}
