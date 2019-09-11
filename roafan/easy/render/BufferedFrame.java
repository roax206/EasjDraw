package roafan.easy.render;
import roafan.api.Display2D;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Insets;
import java.io.PrintStream;
import java.awt.geom.AffineTransform;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

//DrawFrame: a sub class of java's swing JFrame designed for ease of use and quick implementation of 2D graphics
public class BufferedFrame extends JFrame implements Display2D
{
	private PrintStream stdOut = System.out;
	private PrintStream stdErr = System.err;

	private EasjCanvas canvas = null;
	private Color eraseColor = Color.WHITE;

	private int drawWidth;
	private int drawHeight;

	private int bufferCount = 3;
	private boolean bCountChanged = true;

	//overridden method from parent class
	//sets the frame to delete itself when the red X is clicked (exiting the program only if there are no other running threads).
	protected void frameInit()
	{
		super.frameInit();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void ensureDisplayable()
	{
		if(isVisible() == false)
		{
			setVisible(true);
		}

		if(getBufferStrategy() == null || bCountChanged)
		{
			createBufferStrategy(bufferCount);
			bCountChanged = false;
		}
	}

	public setBufferCount(int bCount)
	{
		bufferCount = bCount;
		bCountChanged = true;
	}
	
	//returns the x value for the pixels on the left most line of the canvas
	public int getMinDrawX()
	{
		return 0;
	}

	//returns the x value for the pixels on the right most line of the canvas
	public int getMaxDrawX()
	{
		return canvas.getDrawWidth() - 1;
	}

	//returns the width (in pixels) of the canvas. if the canvas cannot be drawn to the value is 0
	public int getDrawWidth()
	{
		return canvas.getDrawWidth();
	}

	//returns the y value for the pixels on the top most line of the canvas
	public int getMinDrawY()
	{
		return 0;
	}

	//returns the y value for the pixels on the bottom most line of the canvas
	public int getMaxDrawY()
	{
		return canvas.getDrawHeight() - 1;
	}

	//returns the height (in pixels) of the canvas. if the canvas cannot be drawn to the value is 0
	public int getDrawHeight()
	{
		return canvas.getDrawHeight();
	}

	//returns a graphics object that represents the space inside the borders of the window and which can be drawn to
	public Graphics2D getDrawGraphics()
	{
		return canvas.getDrawGraphics();
	}

	//visually updates the frame to show the current state of the canvas
	public void display()
	{
		try{
			BufferStrategy bs = getBufferStrategy();
			do
			{
				ensureDisplayable();
				paint(bs.getDrawGraphics());
			}
			while(bs.contentsLost());
			bs.show();
		}
		catch(Exception e)
		{
			//window closed during display;
		}
	}

	//draws the given image onto the area inside the borders with the image offset by x pixels from the left and y pixels from the top
	//note: pixel offsets are according to the resolution of the frame (set by init() or the size of the frame when first drawn to) and may not match screen
	//if the frame is resized.
	public void drawRenderedImage(RenderedImage image, int x, int y)
	{
		getDrawGraphics().drawRenderedImage(image, AffineTransform.getTranslateInstance(x,y));
	}

	public void drawRenderedImage(RenderedImage image, AffineTransform transform)
	{
		getDrawGraphics().drawRenderedImage(image, transform);
	}
	
	public void repaint(long time, int x, int y, int width, int height)
	{}
	
	public void paint(Graphics g)
	{
		stdOut.println("painted");
		super.paint(g);
	}

	//sets the frame as the given size then initializes the canvas (ie the space inside the borders) to be drawn to.
	//the frames resolution is set to the size in pixels of the frame (ie space within borders is as seen on screen until the frame is resized).
	public void init(int width, int height)
	{
		if(canvas == null)
		{
			canvas = new EasjCanvas();
			add(canvas);
		}
		canvas.init(width, height);
	}

	public void init()
	{
		if(canvas == null)
		{
			canvas = new EasjCanvas();
			add(canvas);
		}
		canvas.init();
	}

	//sets the frame as the given size at the given pixel offset from the top left of the screen 
	//then initializes the canvas (ie the space inside the borders) to be drawn to.
	//the frames resolution is set to the size in pixels of the frame (ie space within borders is as seen on screen until the frame is resized).

	public boolean drawSuccess()
	{
		return true;
	}
}