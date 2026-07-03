package view;

import controller.SensorController;
import model.Sensor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class JanelaSensor extends JFrame {
    private SensorController controller = new SensorController();

    private JPanel painelDados;
    private JPanel painelResultado;

    private JLabel labelId;
    private JTextField textoId;
    private JLabel labelCodigo;
    private JTextField textoCodigo;
    private JLabel labelTipo;
    private JTextField textoTipo;
    private JLabel labelLocalizacao;
    private JTextField textoLocalizacao;

    private JButton botaoSalvar;
    private JButton botaoConsultar;
    private JButton botaoLimpar;
    private JButton botaoAbrirMedicao;

    private JTable tabelaSensores;
    private DefaultTableModel modeloTabelaSensores;
    private JScrollPane scrollTabela;

    public JanelaSensor (){
        initComponents();
    }

    private void initComponents(){
        painelDados = new JPanel();
        painelResultado = new JPanel();

        labelId = new JLabel();
        textoId = new JTextField();
        labelCodigo = new JLabel();
        textoCodigo = new JTextField();
        labelTipo = new JLabel();
        textoTipo = new JTextField();
        labelLocalizacao = new JLabel();
        textoLocalizacao = new JTextField();

        botaoSalvar = new JButton();
        botaoConsultar = new JButton();
        botaoLimpar = new JButton();
        botaoAbrirMedicao = new JButton();

        modeloTabelaSensores = new DefaultTableModel(new Object[]{"ID", "Código", "Tipo", "Localização"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaSensores = new JTable(modeloTabelaSensores);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cadastro de Sensor");
        setLayout(null);
        getContentPane().setBackground(new Color(235, 239, 245));

        painelDados.setLayout(null);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Sensor"));
        painelDados.setBackground(Color.WHITE);
        painelDados.setBounds(20, 20, 660, 220);
        add(painelDados);

        painelResultado.setLayout(null);
        painelResultado.setBorder(BorderFactory.createTitledBorder("Sensores Cadastrados"));
        painelResultado.setBackground(Color.WHITE);
        painelResultado.setBounds(20, 260, 660, 210);
        add(painelResultado);

        labelId.setText("ID:");
        labelId.setBounds(30, 35, 90, 25);
        painelDados.add(labelId);

        textoId.setColumns(10);
        textoId.setEditable(false);
        textoId.setBounds(130, 35, 160, 25);
        painelDados.add(textoId);

        labelLocalizacao.setText("Localização:");
        labelLocalizacao.setBounds(330, 35, 90, 25);
        painelDados.add(labelLocalizacao);

        textoLocalizacao.setColumns(20);
        textoLocalizacao.setBounds(430, 35, 180, 25);
        painelDados.add(textoLocalizacao);

        labelCodigo.setText("Código:");
        labelCodigo.setBounds(30, 85, 90, 25);
        painelDados.add(labelCodigo);

        textoCodigo.setColumns(20);
        textoCodigo.setBounds(130, 85, 160, 25);
        painelDados.add(textoCodigo);

        labelTipo.setText("Tipo:");
        labelTipo.setBounds(330, 85, 90, 25);
        painelDados.add(labelTipo);

        textoTipo.setColumns(20);
        textoTipo.setBounds(430, 85, 180, 25);
        painelDados.add(textoTipo);

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(evt -> salvarSensor());
        botaoSalvar.setBounds(30, 160, 110, 30);
        painelDados.add(botaoSalvar);

        botaoConsultar.setText("Consultar");
        botaoConsultar.addActionListener(evt -> consultarSensor());
        botaoConsultar.setBounds(155, 160, 110, 30);
        painelDados.add(botaoConsultar);

        botaoLimpar.setText("Limpar");
        botaoLimpar.addActionListener(evt -> {
            limparCampos();
            mostrarSensores();
        });
        botaoLimpar.setBounds(280, 160, 110, 30);
        painelDados.add(botaoLimpar);

        botaoAbrirMedicao.setText("Medições");
        botaoAbrirMedicao.addActionListener(evt -> {
            JanelaMedicao janela = new JanelaMedicao();
            janela.setVisible(true);
        });
        botaoAbrirMedicao.setBounds(405, 160, 125, 30);
        painelDados.add(botaoAbrirMedicao);

        tabelaSensores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaSensores.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                preencherCamposPelaTabela();
            }
        });

        scrollTabela = new JScrollPane(tabelaSensores);
        scrollTabela.setBounds(20, 30, 620, 160);
        painelResultado.add(scrollTabela);

        setSize(720, 540);
        setLocationRelativeTo(null);
        mostrarSensores();
    }

    private void salvarSensor(){
        String codigo = textoCodigo.getText().trim();
        String tipo = textoTipo.getText().trim();
        String localizacao = textoLocalizacao.getText().trim();

        if(codigo.isEmpty() || tipo.isEmpty() || localizacao.isEmpty()){
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        String mensagem = controller.cadastrarSensor(codigo, tipo, localizacao);
        JOptionPane.showMessageDialog(this, mensagem);
        mostrarSensores();
    }

    private void consultarSensor(){
        String codigo = textoCodigo.getText().trim();

        if(codigo.isEmpty()){
            mostrarSensores();
            return;
        }

        Sensor sensor = controller.consultarSensor(codigo);

        limparTabela();
        if(sensor != null){
            preencherCampos(sensor);
            adicionarSensorNaTabela(sensor);
        }else{
            JOptionPane.showMessageDialog(this, "Sensor não encontrado.");
        }
    }

    private void mostrarSensores(){
        ArrayList<Sensor> sensores = controller.listarSensores();
        limparTabela();

        for(Sensor sensor : sensores){
            adicionarSensorNaTabela(sensor);
        }
    }

    private void adicionarSensorNaTabela(Sensor sensor){
        modeloTabelaSensores.addRow(new Object[]{
                sensor.getId(),
                sensor.getCodigo(),
                sensor.getTipo(),
                sensor.getLocalizacao()
        });
    }

    private void limparTabela(){
        modeloTabelaSensores.setRowCount(0);
    }

    private void preencherCampos(Sensor sensor){
        textoId.setText(String.valueOf(sensor.getId()));
        textoCodigo.setText(sensor.getCodigo());
        textoTipo.setText(sensor.getTipo());
        textoLocalizacao.setText(sensor.getLocalizacao());
    }

    private void preencherCamposPelaTabela(){
        int linha = tabelaSensores.getSelectedRow();
        if(linha >= 0){
            textoId.setText(modeloTabelaSensores.getValueAt(linha, 0).toString());
            textoCodigo.setText(modeloTabelaSensores.getValueAt(linha, 1).toString());
            textoTipo.setText(modeloTabelaSensores.getValueAt(linha, 2).toString());
            textoLocalizacao.setText(modeloTabelaSensores.getValueAt(linha, 3).toString());
        }
    }

    private void limparCampos(){
        textoId.setText("");
        textoCodigo.setText("");
        textoTipo.setText("");
        textoLocalizacao.setText("");
    }
}
