package roafan.easy.render;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
public class EasjCanvas extends JPanel
{
	//the width and height of the canvas of this canvas component such that they may be held when the canvas is null
	private int drawWidth = 0;
	private int drawHeight = 0;
	
	//image representing the canvas or virtual drawable screen space for this component
	private BufferedImage canvas = null;
	private Color eraseColor = Color.WHITE;
	
	public Graphics2D getDrawGraphics()
	{
		if(canvas == null)
		{
			return null;
		}
		else
		{
			return canvas.createGraphics();
		}
	}

	private void initResolution(int width, int height)
	{
		if(width>0 && height>0)
		{
			canvas = new BufferedImage(drawWidth, drawHeight, BufferedImage.TYPE_INT_RGB);
		}
		else
		{
			canvas = null;
		}
	}
	
	public int getDrawWidth()
	{
		return drawWidth;
	}
	
	public int getDrawHeight()
	{
		return drawHeight;
	}
	
	public void init(int width, int height)
	{
		//update the draw dimensions for later calls to init().
		drawWidth = width;
		drawHeight = height;
		
		//sets the virtual resolution to the given values
		initResolution(drawWidth, drawHeight);
	}
	
	public void init()
	{
		if(drawWidth != canvas.getWidth() || drawHeight != canvas.getHeight())
		{
			initResolution(drawWidth, drawHeight);
		}
		Graphics2D g = getDrawGraphics();
		g.setColor(eraseColor);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public void paint(Graphics g)
	{
		System.out.println("child painted");
		g.drawImage(canvas, 0, 0, getWidth() - 1, getHeight() - 1, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
	}
}