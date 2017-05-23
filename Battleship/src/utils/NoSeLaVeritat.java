package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class NoSeLaVeritat {
	
	private static final int IMG_WIDTH = 50;
	private static final Color SHAPE_COLOR = Color.RED;
	private static final int GAP = 4;
	
	static BufferedImage shape;
	
	public static BufferedImage getHitShape() {
		if (shape != null) {
			shape = new BufferedImage(IMG_WIDTH, IMG_WIDTH, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = shape.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(SHAPE_COLOR);
			int imgX = GAP;
			int imgY = GAP;
			int width = IMG_WIDTH - 2 * imgX;
			int height = IMG_WIDTH - 2 * imgY;
			g2.fillOval(imgX,imgY, width, height);
			g2.dispose();
		}
		
		return shape;
	}
}
