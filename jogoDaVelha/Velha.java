package jogoDaVelha;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Velha extends JFrame {

    private JButton[] btn = new JButton[9];
    private JLabel placar = new JLabel("PLACAR");
    private JLabel placarX = new JLabel("X: 0");
    private JLabel placarO = new JLabel("O: 0");
    private JButton novo = new JButton("Novo Jogo");
    private JButton zerar = new JButton("Zerar Placar");
    private int pontoX = 0;
    private int pontoO = 0;
    private boolean XO = false;
    private boolean[] click = new boolean[9];

    public Velha() {
        int cont = 0;

        setVisible(true);
        setTitle("Jogo da Velha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(100, 100, 800, 600);

        Color background = new Color(40, 42, 54);
        Color foreground = new Color(248, 248, 242);
        Color buttonBackground = new Color(68, 71, 90);
        Color buttonForeground = new Color(249, 38, 114);

        getContentPane().setBackground(background);
        placar.setForeground(foreground);
        placarX.setForeground(buttonForeground);
        placarO.setForeground(buttonForeground);

        novo.setBackground(buttonBackground);
        novo.setForeground(buttonForeground);
        novo.setFocusPainted(false);

        zerar.setBackground(buttonBackground);
        zerar.setForeground(buttonForeground);
        zerar.setFocusPainted(false);

        add(placar);
        add(placarX);
        add(placarO);
        add(novo);
        add(zerar);

        placar.setBounds(550, 50, 100, 30);
        placarX.setBounds(525, 75, 100, 30);
        placarO.setBounds(575, 75, 100, 30);
        novo.setBounds(525, 120, 150, 30);
        zerar.setBounds(525, 170, 150, 30);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[cont] = new JButton();
                add(btn[cont]);
                btn[cont].setBounds((150 * i) + 50, (150 * j) + 50, 140, 140);
                btn[cont].setFont(new Font("Arial", Font.BOLD, 40));
                btn[cont].setBackground(buttonBackground);
                btn[cont].setForeground(buttonForeground);
                cont++;
            }
        }

        for (int i = 0; i < 9; i++) {
            click[i] = false;
        }

        novo.addActionListener(e -> erase());
        zerar.addActionListener(e -> resetScore());

        for (int i = 0; i < 9; i++) {
            final int index = i;
            btn[i].addActionListener(e -> buttonClick(index));
        }
    }

    private void buttonClick(int index) {
        if (!click[index]) {
            click[index] = true;
            mudar(btn[index]);
        }
    }

    private void mudar(JButton btn) {
        if (XO) {
            btn.setText("O");
            XO = false;
        } else {
            btn.setText("X");
            XO = true;
        }
        win();
    }

    private void updatePlacar() {
        placarX.setText("X: " + pontoX);
        placarO.setText("O: " + pontoO);
    }

    private void win() {
        int cont = 0;
        for (int i = 0; i < 9; i++) {
            if (click[i] == true)
                cont++;
        }

        if ((btn[0].getText().equals("X") && btn[1].getText().equals("X") && btn[2].getText().equals("X"))
                || (btn[3].getText().equals("X") && btn[4].getText().equals("X") && btn[5].getText().equals("X"))
                || (btn[6].getText().equals("X") && btn[7].getText().equals("X") && btn[8].getText().equals("X"))
                || (btn[0].getText().equals("X") && btn[3].getText().equals("X") && btn[6].getText().equals("X"))
                || (btn[1].getText().equals("X") && btn[4].getText().equals("X") && btn[7].getText().equals("X"))
                || (btn[2].getText().equals("X") && btn[5].getText().equals("X") && btn[8].getText().equals("X"))
                || (btn[0].getText().equals("X") && btn[4].getText().equals("X") && btn[8].getText().equals("X"))
                || (btn[6].getText().equals("X") && btn[4].getText().equals("X") && btn[2].getText().equals("X"))) {

            JOptionPane.showMessageDialog(null, "X foi o vencedor, parabéns!!!");
            pontoX++;
            updatePlacar();
            erase();
        } else if ((btn[0].getText().equals("O") && btn[1].getText().equals("O") && btn[2].getText().equals("O"))
                || (btn[3].getText().equals("O") && btn[4].getText().equals("O") && btn[5].getText().equals("O"))
                || (btn[6].getText().equals("O") && btn[7].getText().equals("O") && btn[8].getText().equals("O"))
                || (btn[0].getText().equals("O") && btn[3].getText().equals("O") && btn[6].getText().equals("O"))
                || (btn[1].getText().equals("O") && btn[4].getText().equals("O") && btn[7].getText().equals("O"))
                || (btn[2].getText().equals("O") && btn[5].getText().equals("O") && btn[8].getText().equals("O"))
                || (btn[0].getText().equals("O") && btn[4].getText().equals("O") && btn[8].getText().equals("O"))
                || (btn[6].getText().equals("O") && btn[4].getText().equals("O") && btn[2].getText().equals("O"))) {

            JOptionPane.showMessageDialog(null, "O foi o vencedor, parabéns!!!");
            pontoO++;
            updatePlacar();
            erase();
        } else if (cont == 9) {
            JOptionPane.showMessageDialog(null, "Deu VELHA!!!");
            erase();
        }
    }

    private void erase() {
        for (int i = 0; i < 9; i++) {
            btn[i].setText(" ");
            click[i] = false;
            XO = false;
        }
    }

    private void resetScore() {
        pontoO = 0;
        pontoX = 0;
        updatePlacar();
    }

    public static void main(String[] args) {
        new Velha();
    }
}
