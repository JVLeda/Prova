package view;

import controller.SensorController;
import model.Sensor;

import javax.swing.*;
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

    private JTextArea textoResultado;
    private JScrollPane scrollResultado;

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

        textoResultado = new JTextArea();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cadastro de Sensor");
        setLayout(null);
        getContentPane().setBackground(new Color(235, 239, 245));

        painelDados.setLayout(null);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Sensor"));
        painelDados.setBackground(Color.WHITE);
        painelDados.setBounds(20, 20, 560, 220);
        add(painelDados);

        painelResultado.setLayout(null);
        painelResultado.setBorder(BorderFactory.createTitledBorder("Sensores Cadastrados"));
        painelResultado.setBackground(Color.WHITE);
        painelResultado.setBounds(20, 260, 560, 190);
        add(painelResultado);

        labelId.setText("ID:");
        labelId.setBounds(30, 35, 90, 25);
        painelDados.add(labelId);

        textoId.setColumns(10);
        textoId.setEditable(false);
        textoId.setBounds(130, 35, 150, 25);
        painelDados.add(textoId);

        labelLocalizacao.setText("Localização:");
        labelLocalizacao.setBounds(310, 35, 90, 25);
        painelDados.add(labelLocalizacao);

        textoLocalizacao.setColumns(20);
        textoLocalizacao.setBounds(410, 35, 120, 25);
        painelDados.add(textoLocalizacao);

        labelCodigo.setText("Código:");
        labelCodigo.setBounds(30, 85, 90, 25);
        painelDados.add(labelCodigo);

        textoCodigo.setColumns(20);
        textoCodigo.setBounds(130, 85, 150, 25);
        painelDados.add(textoCodigo);

        labelTipo.setText("Tipo:");
        labelTipo.setBounds(310, 85, 90, 25);
        painelDados.add(labelTipo);

        textoTipo.setColumns(20);
        textoTipo.setBounds(410, 85, 120, 25);
        painelDados.add(textoTipo);

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(evt -> {
            String codigo = textoCodigo.getText();
            String tipo = textoTipo.getText();
            String localizacao = textoLocalizacao.getText();

            if(codigo.isEmpty() || tipo.isEmpty() || localizacao.isEmpty()){
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            }else{
                String mensagem = controller.cadastrarSensor(codigo, tipo, localizacao);
                JOptionPane.showMessageDialog(this, mensagem);
                mostrarSensores();
            }
        });
        botaoSalvar.setBounds(30, 160, 110, 30);
        painelDados.add(botaoSalvar);

        botaoConsultar.setText("Consultar");
        botaoConsultar.addActionListener(evt -> {
            String codigo = textoCodigo.getText();

            if(codigo.isEmpty()){
                mostrarSensores();
            }else{
                Sensor sensor = controller.consultarSensor(codigo);

                if(sensor != null){
                    textoId.setText(String.valueOf(sensor.getId()));
                    textoCodigo.setText(sensor.getCodigo());
                    textoTipo.setText(sensor.getTipo());
                    textoLocalizacao.setText(sensor.getLocalizacao());

                    textoResultado.setText(
                            "ID: " + sensor.getId() +
                                    "\nCódigo: " + sensor.getCodigo() +
                                    "\nTipo: " + sensor.getTipo() +
                                    "\nLocalização: " + sensor.getLocalizacao()
                    );
                }else{
                    textoResultado.setText("Sensor não encontrado.");
                }
            }
        });
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

        textoResultado.setColumns(20);
        textoResultado.setRows(5);
        textoResultado.setEditable(false);
        textoResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));

        scrollResultado = new JScrollPane(textoResultado);
        scrollResultado.setBounds(20, 30, 520, 140);
        painelResultado.add(scrollResultado);

        setSize(620, 520);
        setLocationRelativeTo(null);
        mostrarSensores();
    }

    private void mostrarSensores(){
        ArrayList<Sensor> sensores = controller.listarSensores();
        String texto = "";

        if(sensores.isEmpty()){
            texto = "Nenhum sensor cadastrado.";
        }else{
            for(Sensor sensor : sensores){
                texto += "ID: " + sensor.getId() +
                        " | Código: " + sensor.getCodigo() +
                        " | Tipo: " + sensor.getTipo() +
                        " | Local: " + sensor.getLocalizacao() + "\n";
            }
        }
        textoResultado.setText(texto);
    }

    private void limparCampos(){
        textoId.setText("");
        textoCodigo.setText("");
        textoTipo.setText("");
        textoLocalizacao.setText("");
    }
}
