import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayResults extends Canvas {
    JFrame j;
    DisplayResults(ArrayList data) {
        int width = 500;
        int height = 500;
        j = new JFrame("SIR Model Simulation");

        j.setSize(width, height);
        j.setVisible(true);
    }
}
