import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.chart.ChartPanel;

public class Frame {
    JFrame f;
    Frame() {
        f = new JFrame("Simulation du modèle SIR");

        final int[] varTaille = {1000};
        final int[] varInfectes = {50};
        final int[] varGenerations = {100};
        final double[] varContagion = {0.5};
        final double[] varSeuilProx = {0.5};
        final double[] varSeuilRet = {0.15};

        JPanel panelChart = new JPanel();

        ArrayList data = generate(varGenerations, varTaille, varInfectes, varContagion, varSeuilProx, varSeuilRet);
        ArrayList dataFormatted = formatData(data);
        ChartPanel cp = new ChartPanel(getChart(dataFormatted));
        panelChart.removeAll();
        panelChart.add(cp);
        SwingUtilities.updateComponentTreeUI(f);

        JTextArea taille = new JTextArea(String.valueOf(varTaille[0]));
        JTextArea infectes = new JTextArea(String.valueOf(varInfectes[0]));
        JTextArea generations = new JTextArea(String.valueOf(varGenerations[0]));
        JSlider contagion = new JSlider(0, 10, 5);
        JSlider seuilProx = new JSlider(0, 10, 5);
        JSlider seuilRet = new JSlider(0, 100, 15);

        JLabel labelTaille = new JLabel("Taille de la population");
        JLabel labelInfectes = new JLabel("Nombre d'infectés à la première génération");
        JLabel labelGenerations = new JLabel("Nombre de générations");
        JLabel labelContagion = new JLabel("Contagion: " + varContagion[0]);
        JLabel labelSeuilProx = new JLabel("Seuil de proximité: " + varSeuilProx[0]);
        JLabel labelSeuilRet = new JLabel("Probabilité de rétablissement: " + varSeuilRet[0]);
        JButton start = new JButton("Démarrer la simulation");

        taille.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                varTaille[0] = Integer.parseInt(taille.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                varTaille[0] = Integer.parseInt(taille.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        infectes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                varInfectes[0] = Integer.parseInt(infectes.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                varInfectes[0] = Integer.parseInt(infectes.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        generations.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                varGenerations[0] = Integer.parseInt(generations.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                varGenerations[0] = Integer.parseInt(generations.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        contagion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                varContagion[0] = (double) contagion.getValue()/10;
                labelContagion.setText("Contagion: " + String.valueOf(varContagion[0]));
            }
        });

        seuilProx.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                varSeuilProx[0] = (double) seuilProx.getValue()/10;
                labelSeuilProx.setText("Seuil de proximité: " + String.valueOf(varSeuilProx[0]));
            }
        });

        seuilRet.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                varSeuilRet[0] = (double) seuilRet.getValue()/100;
                labelSeuilRet.setText("Probabilité de rétablissement: " + String.valueOf(varSeuilRet[0]));
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList data = generate(varGenerations, varTaille, varInfectes, varContagion, varSeuilProx, varSeuilRet);
                ArrayList dataFormatted = formatData(data);
                ChartPanel cp = new ChartPanel(getChart(dataFormatted));
                panelChart.removeAll();
                panelChart.add(cp);
                SwingUtilities.updateComponentTreeUI(f);
            }
        });

        JPanel panelText = new JPanel();
        panelText.add(labelTaille);
        panelText.add(taille);
        panelText.add(labelInfectes);
        panelText.add(infectes);
        panelText.add(labelGenerations);
        panelText.add(generations);
        panelText.add(labelContagion);
        panelText.add(contagion);
        panelText.add(labelSeuilProx);
        panelText.add(seuilProx);
        panelText.add(labelSeuilRet);
        panelText.add(seuilRet);

        panelText.setLayout(new BoxLayout(panelText, BoxLayout.Y_AXIS));

        JPanel panelButton = new JPanel();
        panelButton.add(start);

        f.add(panelText);
        f.add(panelButton);
        f.add(panelChart);
        f.getContentPane().add(BorderLayout.NORTH, panelText);
        f.getContentPane().add(BorderLayout.CENTER, panelButton);
        f.getContentPane().add(BorderLayout.SOUTH, panelChart);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setVisible(true);
    }

    ArrayList generate(int[] generations, int[] taille, int[] infectes, double[] contagion, double[] seuilProx, double[] seuilRet) {
        ArrayList<ArrayList> res = new ArrayList<>();
        Terrain t = new Terrain(taille[0],infectes[0]);
        for (int i = 0; i<generations[0]; i++) {
            t.nextGen(t, contagion[0], seuilProx[0], seuilRet[0]);
            ArrayList<String> buffer = new ArrayList<>();
            for (int j = 0; j < t.getPopulation().size(); j++) {
                buffer.add(t.getPopulation().get(j).getStatut());
            }
            res.add(buffer);
        }

        return res;
    }

    JFreeChart getChart(ArrayList dataFormatted) {
        double[] gens;
        gens = new double[dataFormatted.size()];
        for (int i = 1; i<dataFormatted.size(); i++) {
            gens[i] = i;
        }

        double[] data1;
        data1 = new double[dataFormatted.size()];
        for (int j = 0; j < dataFormatted.size(); j++) {
            ArrayList current = (ArrayList)dataFormatted.get(j);
            data1[j] = Double.parseDouble(current.get(0).toString());
        }
        double[] data2;
        data2 = new double[dataFormatted.size()];
        for (int j = 0; j < dataFormatted.size(); j++) {
            ArrayList current = (ArrayList)dataFormatted.get(j);
            data2[j] = Double.parseDouble(current.get(1).toString()) + Double.parseDouble(current.get(2).toString());
        }
        DefaultXYDataset ds = new DefaultXYDataset();
        double[][] dataSet1 = { gens, data1 };
        double[][] dataSet2 = { gens, data2 } ;
        ds.addSeries("Non infectés", dataSet1);
        ds.addSeries("Infectés", dataSet2);

        JFreeChart chart = ChartFactory.createXYLineChart("Évolution d'une épidémie à travers le modèle SIR", "Générations", "Population", ds, PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }

    ArrayList formatData(ArrayList data) {
        ArrayList dataFormatted = new ArrayList();
        int healthy, infected, healed;
        healthy = 0;
        infected = 0;
        healed = 0;

        for (int i = 0; i<data.size(); i++) {
            ArrayList currentGen = (ArrayList) data.get(i);
            healthy = Collections.frequency(currentGen, "S");
            infected = Collections.frequency(currentGen, "I");
            healed = Collections.frequency(currentGen, "R");
            ArrayList<Integer> buffer = new ArrayList<Integer>();
            buffer.add(healthy);
            buffer.add(infected);
            buffer.add(healed);
            dataFormatted.add(buffer);
            healthy = 0;
            infected = 0;
            healed = 0;
        }
        return dataFormatted;
    }

    public static void main(String[] args) {
        new Frame();
    }
}


