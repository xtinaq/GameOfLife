package net.skaskiw.gameoflife;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GenerationView extends JPanel {
	private static final long serialVersionUID = 1L;
	private Generation renderedGeneration;

	public GenerationView(Generation generation) {
		renderedGeneration = generation;
		initCanvas();
	}

	private void initCanvas() {
		this.setLayout(null);
		this.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
	}

	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		g.setColor(getBackground());
		g.fillRect(0, 0, width, height);
		g.setColor(getForeground());
		
		int xOffset = width / 2;
		int yOffset = height / 2;
		int xOnCanvas;
		int yOnCanvas;
		Position position;
		for (Cell cell : renderedGeneration.getCells()) {
			position = cell.getPosition();
			xOnCanvas = position.xCoord() * 2 + xOffset;
			yOnCanvas = position.yCoord() * 2 + yOffset;
			if (xOnCanvas >= 0 && yOnCanvas < width && yOnCanvas >= 0
					&& yOnCanvas < height)
				g.fillRect(xOnCanvas, yOnCanvas, 2, 2);
		}
	}

	public void render(Generation generation) {
		renderedGeneration = generation;
		this.repaint();
	}
}
