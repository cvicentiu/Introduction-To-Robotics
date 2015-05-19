import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Terrain {
	BufferedImage background;

	public Terrain(String filename) throws IOException {
		background = ImageIO.read(new File(filename));
	}

	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
}
