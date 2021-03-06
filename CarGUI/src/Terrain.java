import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Terrain {
	public final String backgroundFilename = "grass.jpg";
	public final String roadFilename = "road.jpg";
	public static final int roadWidth = 70;

	final static float dash1[] = { 10.0f };
	final static BasicStroke dashed = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	int n = 4;
	TexturePaint backgroundTexture, roadTexture;
	Intersection[][] map;
	public static LinkedList<CarSimulator> inIntersection[][];

	public Terrain() throws IOException {
		backgroundTexture = new TexturePaint(ImageIO.read(new File(
				backgroundFilename)), new Rectangle(0, 0, 32, 32));
		roadTexture = new TexturePaint(ImageIO.read(new File(roadFilename)),
				new Rectangle(0, 0, 32, 32));
		inIntersection = new LinkedList[n][n];
		
		map = new Intersection[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				map[i][j] = new Intersection(new Vector2D(j * 500 / 3 + 50,
						i * 500 / 3 + 50));
				inIntersection[i][j] = new LinkedList<>();
			}
			
		}
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(backgroundTexture);
		g2d.fillRect(0, 0, 600, 600);

		g2d.setPaint(roadTexture);

		for (int i = 1; i < n; i++) {
			g2d.setPaint(roadTexture);
			g2d.fillRect((int) map[0][i - 1].point.x - roadWidth / 2,
					(int) map[0][i - 1].point.y - roadWidth / 2,
					(int) (map[0][i].point.x - map[0][i - 1].point.x),
					roadWidth);

			g2d.setColor(Color.WHITE);
			g2d.setStroke(dashed);
			g2d.drawLine((int) map[0][i - 1].point.x + roadWidth / 2,
					(int) map[0][i - 1].point.y, (int) map[0][i].point.x
							- roadWidth / 2, (int) map[0][i].point.y);

			g2d.setPaint(roadTexture);
			g2d.fillRect((int) map[i - 1][0].point.x - roadWidth / 2,
					(int) map[i - 1][0].point.y - roadWidth / 2, roadWidth,
					(int) (map[i][0].point.y - map[i - 1][0].point.y));

			g2d.setColor(Color.WHITE);
			g2d.setStroke(dashed);
			g2d.drawLine((int) map[i - 1][0].point.x,
					(int) map[i - 1][0].point.y + roadWidth / 2,
					(int) map[i][0].point.x, (int) map[i][0].point.y
							- roadWidth / 2);
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				g2d.setPaint(roadTexture);
				g2d.fillRect(
						(int) map[i][j - 1].point.x - roadWidth / 2,
						(int) map[i][j - 1].point.y - roadWidth / 2,
						(int) (map[i][j].point.x - map[i][j - 1].point.x + roadWidth),
						roadWidth);
				g2d.setColor(Color.WHITE);
				g2d.setStroke(dashed);
				g2d.drawLine((int) map[i][j - 1].point.x + roadWidth / 2,
						(int) map[i][j - 1].point.y, (int) map[i][j].point.x
								- roadWidth / 2, (int) map[i][j].point.y);

				g2d.setPaint(roadTexture);
				g2d.fillRect((int) map[i - 1][j].point.x - roadWidth / 2,
						(int) map[i - 1][j].point.y - roadWidth / 2, roadWidth,
						(int) (map[i][j].point.y - map[i - 1][j].point.y)
								+ roadWidth);

				g2d.setColor(Color.WHITE);
				g2d.setStroke(dashed);
				g2d.drawLine((int) map[i - 1][j].point.x,
						(int) map[i - 1][j].point.y + roadWidth / 2,
						(int) map[i][j].point.x, (int) map[i][j].point.y
								- roadWidth / 2);
			}
		}

		//g2d.setColor(Color.WHITE);
		//g2d.setStroke(new BasicStroke());
		
		//for (int i = 0; i < n; i++) {
		//	for (int j = 0; j < n; j++) {
		//		g2d.drawRect((int) map[i][j].point.x - roadWidth / 2,
		//				(int) map[i][j].point.y - roadWidth / 2, roadWidth,
		//				roadWidth);
		//	}
		//}
	}
}
