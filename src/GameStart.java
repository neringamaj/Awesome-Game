import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameStart {
    private JFrame frame;

    public void chooseLevel(JFrame main_frame){
        frame = main_frame;

        JPanel main_choices = new JPanel();
        main_choices.setLayout(null);
        main_choices.setBackground(new Color(90, 70, 40));

        JPanel choose = new JPanel();
        choose.setBackground(new Color(90, 70, 40));
        choose.setSize(800,200);
        choose.setLocation(0,200);

        JButton choice1 = new JButton("Level 1");
        choice1.setFont(new Font("Courier", Font.BOLD, 50));
        choice1.setBackground(new Color(200,150,90));

        JButton choice2 = new JButton("Level 2");
        choice2.setFont(new Font("Courier", Font.BOLD, 50));
        choice2.setBackground(new Color(200,150,90));

        JButton choice3 = new JButton("Level 3");
        choice3.setFont(new Font("Courier", Font.BOLD, 50));
        choice3.setBackground(new Color(200,150,90));

        JButton go_to_Start = new JButton("Go back");
        go_to_Start.setFont(new Font("Courier", Font.BOLD, 50));
        go_to_Start.setBackground(new Color(200,150,90));

        JPanel go_back = new JPanel();
        go_back.setBackground(new Color(90, 70, 40));
        go_back.setSize(220,100);
        go_back.setLocation(550,450);

        go_back.add(go_to_Start);

        choose.add(choice1);
        choose.add(choice2);
        choose.add(choice3);

        main_choices.add(choose);
        main_choices.add(go_back);

        frame.add(main_choices);

        frame.setVisible(true);

        choice1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main_choices.setVisible(false);
                CSVlab l = new CSVlab();
                try {
                    l.loadGame("maps\\map1.csv",main_frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        choice2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main_choices.setVisible(false);
                CSVlab l = new CSVlab();
                try {
                    l.loadGame("maps\\map2.csv",main_frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        choice3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main_choices.setVisible(false);
                CSVlab l = new CSVlab();
                try {
                    l.loadGame("maps\\map3.csv",main_frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        go_to_Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main_choices.setVisible(false);
                Setup back = new Setup(main_frame);
                back.startUp();
            }
        });
    }

}


