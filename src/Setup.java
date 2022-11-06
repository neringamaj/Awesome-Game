import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Neringa Majauskaitė 4gr

public class Setup {
    public JPanel panel;
    public JFrame frame;

    public static void main (String[] args){
        new Setup().startUp();
    }

    public Setup(){
        frame = new JFrame("My Game");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(90, 70, 40));
        frame.setVisible(true);
    }

    public Setup(JFrame prevFrame) {
        frame = prevFrame;
    }

    //declare variables

    public void startUp(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(69, 43, 14));

        JPanel p_game_name = new JPanel();
        p_game_name.setLocation(10,10);
        p_game_name.setSize(780,100);
        p_game_name.setBackground(new Color(69, 43, 14));

        JLabel game_name = new JLabel("The best GAME ever");
        game_name.setFont(new Font("Courier", Font.BOLD,75));
        game_name.setForeground(new Color(255,255,255));

        p_game_name.add(game_name);

        JButton start_button = new JButton("Start");
        start_button.setFont(new Font("Courier", Font.BOLD,50));
        start_button.setBackground(new Color(200,150,90));

        JPanel p_button = new JPanel();
        p_button.setLocation(300,200);
        p_button.setSize(200,80);
        p_button.setBackground(new Color(69, 43, 14));
        p_button.add(start_button);

        JButton map_button = new JButton("Edit Map");
        map_button.setFont(new Font("Courier", Font.BOLD,50));
        map_button.setBackground(new Color(200,150,90));

        JPanel p_map = new JPanel();
        p_map.setSize(300,80);
        p_map.setBackground(new Color(69, 43, 14));
        p_map.setLocation(250,300);
        p_map.add(map_button);

        panel.add(p_game_name);
        panel.add(p_button);
        panel.add(p_map);
        //panel.setOpaque(true);

        frame.add(panel);
        frame.setVisible(true);

        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                GameStart gg = new GameStart();
                gg.chooseLevel(frame);
            }
        });

        map_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditMap m = new EditMap();
                m.chooseMap(panel,frame);
            }
        });
    }

}