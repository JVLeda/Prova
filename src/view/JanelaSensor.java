package view;

import controller.SensorController;

import javax.swing.*;

public class JanelaSensor extends JFrame {
    private SensorController controller = new SensorController();

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

    private JTextArea textoResultado;

    public JanelaSensor (){
        initComponents();

    }

}
