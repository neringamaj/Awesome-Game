import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVlab {
    BufferedImage[][] tileMap = new BufferedImage[8][42];
    JFrame main_frame;
    int[][] mapn = new int[8][42];

    public void loadGame(String map, JFrame frame) throws IOException{
        Path map_path = Paths.get(map);
        main_frame = frame;

        BufferedImage tile1 = ImageIO.read (new File("images\\Tile1.png"));
        BufferedImage tile2 = ImageIO.read (new File("images\\Tile2.png"));
        BufferedImage tile3 = ImageIO.read (new File("images\\Tile3.png"));
        BufferedImage tile4 = ImageIO.read (new File("images\\Tile4.png"));
        BufferedImage tile5 = ImageIO.read (new File("images\\dead.png"));


        try (BufferedReader br = Files.newBufferedReader(map_path, StandardCharsets.US_ASCII)){
            for (int i = 0; i < 8; i++){
                String line = br.readLine();
                String[] att = line.split(", ");
                for(int j = 0; j < 42; j++){
                    mapn[i][j] = att[j].charAt(0) - 48;
                    if (mapn[i][j] == 1){
                        tileMap[i][j] = tile1;
                    }
                    else if (mapn[i][j] == 2){
                        tileMap[i][j] = tile2;
                    }
                    else if (mapn[i][j] == 3){
                        tileMap[i][j] = tile3;
                    }
                    else if (mapn[i][j] == 4){
                        tileMap[i][j] = tile4;
                    }
                    else if (mapn[i][j] == 5){
                        tileMap[i][j] = tile5;
                    }
                    else tileMap[i][j] = null;
                }
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        new Drawing(tileMap,main_frame,mapn);
    }
}
