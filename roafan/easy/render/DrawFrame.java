package roafan.easy.render;
import roafan.api.Display2D;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.Color;
import java.awt.Insets;
import java.io.PrintStream;
import java.awt.geom.AffineTransform;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

//DrawFrame: a sub class of java's swing JFrame designed for ease of use and quick implementation of 2D graphics
public class DrawFrame extends JFrame implements Display2D
{
	private PrintStream stdOut = System.out;
	private PrintStream stdErr = System.err;

	private BufferedImage canvas = null;
	private Color eraseColor = Color.WHITE;

	private boolean drawSuccessful = false;

	//overridden method from parent class
	//sets the frame to delete itself when the red X is clicked (exiting the program only if there are no other running threads).
	protected void frameInit()
	{
		super.frameInit();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addComponentListener(new ComponentAdapter()
		{
			public void componentMoved(ComponentEvent ce)
			{
				drawSuccessful = false;
			}
		});
	}

	//checks if the frame is ready to be draw on, and if not prepares it for being drawn on
	private void checkInitialized()
	{	
		if(canvas == null)
		{
			canvas = new BufferedImage(getBorderedWidth(), getBorderedHeight(), BufferedImage.TYPE_INT_RGB);
			clear();
		}
	}

	private void ensureDisplayable()
	{
		if(isVisible() == false)
		{
			setVisible(true);
		}

		if(getBufferStrategy() == null)
		{
			createBufferStrategy(3);
		}
	}
	
	//returns the x value for the pixels on the left most line of the canvas
	public int getMinDrawX()
	{
		return 0;
	}

	//returns the x value for the pixels on the right most line of the canvas
	public int getMaxDrawX()
	{
		return getDrawWidth() - 1;
	}

	//returns the width (in pixels) of the canvas. if the canvas cannot be drawn to the value is 0
	public int getDrawWidth()
	{
		if(canvas == null)
		{
			return 0;
		}
		else
		{
			return canvas.getWidth();
		}
	}

	//returns the y value for the pixels on the top most line of the canvas
	public int getMinDrawY()
	{
		return 0;
	}

	//returns the y value for the pixels on the bottom most line of the canvas
	public int getMaxDrawY()
	{
		return getDrawHeight() - 1;
	}

	//returns the height (in pixels) of the canvas. if the canvas cannot be drawn to the value is 0
	public int getDrawHeight()
	{
		if(canvas == null)
		{
			return 0;
		}
		else
		{
			return canvas.getHeight();
		}
	}

	//gets the leftmost x value for the space inside the area surrounded by borders(ie 0 if there are no borders)
	private int getBorderedX()
	{
		return getInsets().left;
	}
	
	//gets the topmost y value for the space inside the area surrounded by borders(ie 0 if there are no borders)
	private int getBorderedY()
	{
		return getInsets().top;
	}

	//gets the width of the area inside the borders
	private int getBorderedWidth()
	{
		Insets insets = getInsets();
		return getWidth() - (insets.left + insets.right);
	}
	
	//gets the height of the area inside the borders
	private int getBorderedHeight()
	{
		Insets insets = getInsets();
		return getHeight() - (insets.top + insets.bottom);
	}

	//returns a graphics object that represents the space inside the borders of the window and which can be drawn to
	public Graphics2D getDrawGraphics()
	{		
		checkInitialized();
		if(canvas != null)
		{
			return canvas.createGraphics();
		}
		else
		{
			throw new RuntimeException("cannot initialize frame");
		}
	}

	//clears the drawable section of the frame(ie paints over the current canvas with the erase color)
	public void clear()
	{
		Graphics2D g = getDrawGraphics();
		g.setColor(eraseColor);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	//visually updates the frame to show the current state of the canvas
	public void display()
	{
		if(drawSuccess())
		{
			ensureDisplayable();
			getBufferStrategy().getDrawGraphics().drawImage(canvas, getBorderedX(), getBorderedY(), getBorderedWidth(), getBorderedHeight(), this);
			getBufferStrategy().show();
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

	//sets the frame as the given size then initializes the canvas (ie the space inside the borders) to be drawn to.
	//the frames resolution is set to the size in pixels of the frame (ie space within borders is as seen on screen until the frame is resized).
	public void init(int width, int height)
	{
		this.setSize(width, height);
		clear();
		drawSuccessful = true;
	}

	//sets the frame as the given size at the given pixel offset from the top left of the screen 
	//then initializes the canvas (ie the space inside the borders) to be drawn to.
	//the frames resolution is set to the size in pixels of the frame (ie space within borders is as seen on screen until the frame is resized).
	public void init(int x, int y, int width, int height)
	{
		this.setLocation(x, y);
		this.setSize(width, height);
		clear();
		drawSuccessful = true;
	}

	public boolean drawSuccess()
	{
		return drawSuccessful;
	}
}