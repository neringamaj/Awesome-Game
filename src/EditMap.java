import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EditMap {
    public void chooseMap(JPanel panel, JFrame frame) {
        back_panel = panel;
        back_frame = frame;

        ImageIcon[][] map1 = loadMap("maps\\map1.csv");
        int[][] mapn1 = mapn;
        ImageIcon[][] map2 = loadMap("maps\\map2.csv");
        int[][] mapn2 = mapn;
        ImageIcon[][] map3 = loadMap("maps\\map3.csv"); //uzpildyti mapsus pirminius
        int[][] mapn3 = mapn;

        panel.setVisible(false);
        JPanel main_choices = new JPanel();
        main_choices.setLayout(null);
        main_choices.setBackground(new Color(90, 70, 40));

        JPanel choices = new JPanel();
        choices.setSize(800,100);
        choices.setBackground(new Color(90, 70, 40));
        choices.setLocation(0,200);

        JLabel choose = new JLabel("Choose which level to edit:");
        choose.setFont(new Font("Courier", Font.BOLD, 55));
        choose.setForeground(Color.WHITE);

        JPanel label = new JPanel();
        label.setBackground(new Color(90, 70, 40));
        label.setSize(800,100);
        label.setLocation(0,10);

        label.add(choose);

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

        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);

        main_choices.add(label);
        main_choices.add(choices);
        main_choices.add(go_back);

        frame.add(main_choices);

        choice1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map_num = 1;
                main_choices.setVisible(false);
                editMap(map1, frame, mapn1);
            }
        });
        choice2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map_num = 2;
                main_choices.setVisible(false);
                editMap(map2, frame, mapn2);
            }
        });
        choice3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map_num = 3;
                main_choices.setVisible(false);
                editMap(map3, frame, mapn3);
            }
        });
        go_to_Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main_choices.setVisible(false);
                Setup back = new Setup(frame);
                back.startUp();
            }
        });
    }

    public void editMap(ImageIcon[][] map, JFrame frame, int[][] map_n) {
        rowData = map;
        rowDatan = map_n;

        JPanel mainp = new JPanel();
        mainp.setLayout(null);

        JPanel panelMap = new JPanel();
        panelMap.setBackground(new Color(90, 70, 40));
        panelMap.setLocation(0,0);
        panelMap.setSize(800,80);

        JButton back = new JButton("Back");
        JButton save = new JButton("Save");

        JButton pic0 = new JButton(new ImageIcon("images\\dead.png"));
        pic0.setPreferredSize(new Dimension(50,50));
        JButton pic1 = new JButton(new ImageIcon("images\\Tile1.png"));
        pic1.setPreferredSize(new Dimension(50, 50));
        JButton pic2 = new JButton(new ImageIcon("images\\Tile2.png"));
        pic2.setPreferredSize(new Dimension(50, 50));
        JButton pic3 = new JButton(new ImageIcon("images\\Tile3.png"));
        pic3.setPreferredSize(new Dimension(50, 50));
        JButton pic4 = new JButton(new ImageIcon("images\\Tile4.png"));
        pic4.setPreferredSize(new Dimension(50, 50));
        JButton pic5 = new JButton("0");
        pic5.setPreferredSize(new Dimension(50,50));

        panelMap.add(back);
        panelMap.add(pic0);
        panelMap.add(pic1);
        panelMap.add(pic2);
        panelMap.add(pic3);
        panelMap.add(pic4);
        panelMap.add(pic5);
        panelMap.add(save);

        map_table.addMouseListener(new CustomListener());

        map_table.setRowHeight(50);
        TableColumnModel col = map_table.getColumnModel();

        for (int i = 0; i < 42; i++){
            col.getColumn(i).setPreferredWidth(50);
        }

        map_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        JScrollPane ogmap = new JScrollPane(map_table);
        ogmap.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ogmap.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        ogmap.setLocation(40,120);
        ogmap.setSize(700,430);

        mainp.add(panelMap);
        mainp.add(ogmap);

        frame.add(mainp);

        loop(back,pic0,pic1,pic2,pic3,pic4,pic5,save,mainp);
    }
    /////////////////////////////////////////////////////////////
    private final TableModel map_grid = new AbstractTableModel() {
        @Override
        public int getRowCount() {
            return 8;
        }

        @Override
        public int getColumnCount() {
            return 42;
        }
        @Override
        public Class<?> getColumnClass (int columnIndex){
            return ImageIcon.class;
        }
        @Override
        public ImageIcon getValueAt(int rowIndex, int columnIndex) {
            return rowData[rowIndex][columnIndex];
        }
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex){
            return true;
        }
        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex){
            rowData[rowIndex][columnIndex] = (ImageIcon)value;
            fireTableCellUpdated(rowIndex,columnIndex);
            rowDatan[rowIndex][columnIndex] = tilen;
        }
    };

    public int[][] rowDatan = new int[8][42];
    private int map_num = 0;
    private final JTable map_table = new JTable(map_grid);
    public ImageIcon[][] rowData = new ImageIcon[8][42];
    public ImageIcon tile = null;
    public int rows, col, tilen;
    public int[][] mapn;
    private JFrame back_frame = new JFrame();
    private JPanel back_panel = new JPanel();


    ////////////////////////////////////////////////////////////////////////////
    public void loop(JButton back,JButton pic0,JButton pic1,JButton pic2,JButton pic3,JButton pic4,JButton pic5,JButton save, JPanel mainp){
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainp.setVisible(false);
                chooseMap(back_panel,back_frame);
            }
        });
        pic0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tile = new ImageIcon("images\\dead.png");
                tilen = 5;
            }
        });
        pic1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tile = new ImageIcon("images\\Tile1.png");
                tilen = 1;
            }
        });
        pic2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tile = new ImageIcon("images\\Tile2.png");
                tilen = 2;
            }
        });
        pic3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tile = new ImageIcon("images\\Tile3.png");
                tilen = 3;
            }
        });
        pic4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tile = new ImageIcon("images\\Tile4.png");
                tilen = 4;
            }
        });
        pic5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tile = null;
                tilen = 0;
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveMap();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public class CustomListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent arg0) {
            super.mouseClicked(arg0);
            rows = map_table.getSelectedRow();
            col = map_table.getSelectedColumn();

            map_table.setValueAt(tile,rows,col);
        }
        @Override
        public void mousePressed(MouseEvent arg0){
            super.mousePressed(arg0);
            rows = map_table.getSelectedRow();
            col = map_table.getSelectedColumn();

            map_table.setValueAt(tile,rows,col);
        }
        @Override
        public void mouseReleased(MouseEvent arg0){

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            rows = map_table.getSelectedRow();
            col = map_table.getSelectedColumn();

            map_table.setValueAt(tile,rows,col);
        }
    }
    public void saveMap() throws IOException {
        File path;
        if (map_num == 1){
            path = new File("maps\\map1.csv");
        }
        else if (map_num == 2){
            path = new File("maps\\map2.csv");
        }
        else{
            path = new File("maps\\map3.csv");
        }
        PrintWriter pw = new PrintWriter(path);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 42; y++) {
                pw.write(rowDatan[x][y] + 48);
                pw.write(", ");
            }
            pw.write("\n");
        }
        pw.flush();
        pw.close();
    }
    public ImageIcon[][] loadMap(String map_name){
        Path map_path = Paths.get(map_name);
        mapn = new int[8][42];
        ImageIcon[][] map = new ImageIcon[8][42];

        ImageIcon tile0 = new ImageIcon("images\\dead.png");
        ImageIcon tile1 = new ImageIcon("images\\tile1.png");
        ImageIcon tile2 = new ImageIcon("images\\tile2.png");
        ImageIcon tile3 = new ImageIcon("images\\tile3.png");
        ImageIcon tile4 = new ImageIcon("images\\tile4.png");

        try (BufferedReader br = Files.newBufferedReader(map_path, StandardCharsets.US_ASCII)){
            for (int i = 0; i < 8; i++){
                String line = br.readLine();
                String[] att = line.split(", ");
                for(int j = 0; j < 42; j++){
                    mapn[i][j] = att[j].charAt(0) - 48;
                    if (mapn[i][j] == 1){
                        map[i][j] = tile1;
                    }
                    else if (mapn[i][j] == 2){
                        map[i][j] = tile2;
                    }
                    else if (mapn[i][j] == 3){
                        map[i][j] = tile3;
                    }
                    else if (mapn[i][j] == 4){
                        map[i][j] = tile4;
                    }
                    else if (mapn[i][j] == 5){
                        map[i][j] = tile0;
                    }
                    else map[i][j] = null;
//                    print(mapn[i][j]);
                }
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return map;
    }
}


