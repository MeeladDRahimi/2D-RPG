package tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol]; // Corrected indexing

        getTileImage();
        loadMap("/maps/PracticeMap.txt"); // Pass the file path as an argument
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneFin.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/GrassFin.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/HellGrassFin.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/LavaFin.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/StonePathFin.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/HellStonePathFin.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            if (is == null) {
                throw new IOException("Map file not found: " + filepath);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxScreenRow; row++) {
                String line = br.readLine();
                if (line == null) break; // Prevent errors if the file has fewer rows

                String[] numbers = line.split(" ");
                for (int col = 0; col < gp.maxScreenCol; col++) {
                    if (col < numbers.length) {
                        mapTileNum[row][col] = Integer.parseInt(numbers[col]);
                    } else {
                        mapTileNum[row][col] = 0; // Default to a safe tile if missing
                    }
                }
            }

            br.close();
        } catch (Exception e) {
            System.err.println("Error loading map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < gp.maxScreenRow; row++) {
            for (int col = 0; col < gp.maxScreenCol; col++) {
                int tileNum = mapTileNum[row][col];

                if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null) {
                    int x = col * gp.tileSize;
                    int y = row * gp.tileSize;
                    g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}
