import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Car {
	BufferedImage car;
	int x, y;
	double direction;

	public Car(int x, int y, double direction, String filename)
			throws IOException {
		car = ImageIO.read(new File(filename));
		this.x = x;
		this.y = y;
		this.direction = Math.toRadians(direction);
	}

	public void draw(Graphics g) {
		AffineTransform tr = new AffineTransform();
		Graphics2D g2d = (Graphics2D) g;

		tr.translate(x, y);
		tr.rotate(direction);
		g2d.setTransform(tr);

		g.drawImage(car, 0, 0, null);

		try {
			g2d.setTransform(tr.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void move(double speed) {
		x += speed;
	}
}
