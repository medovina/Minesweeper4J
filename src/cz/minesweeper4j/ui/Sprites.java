package cz.minesweeper4j.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprites {
    BufferedImage hidden, empty, flag, mine;
    BufferedImage[] nums = new BufferedImage[9];

    public Sprites() {
        BufferedImage sprites;
        
        try {
            sprites = ImageIO.read(getClass().getResourceAsStream("/images/sprites.png"));
        } catch (IOException e) { throw new Error(e); }

        hidden = get(sprites, 0, 0);
        mine = get(sprites, 2, 1);
        flag = get(sprites, 2, 2);
        empty = get(sprites, 2, 3);

        int[] num_x = { 0, 2, 1, 3, 1, 1, 0, 0, 0 };
        int[] num_y = { 0, 0, 3, 0, 1, 0, 3, 2, 1 };

        for (int i = 1 ; i <= 8 ; ++i)
            nums[i] = get(sprites, num_x[i], num_y[i]);
    }

    BufferedImage get(BufferedImage sprites, int x, int y) {
        return sprites.getSubimage(x * 26, y * 26, 24, 24);
    }
}
