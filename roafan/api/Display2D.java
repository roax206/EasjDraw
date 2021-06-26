package roafan.api;
import java.awt.image.RenderedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Point;

public interface Display2D
{
	//DIMENSIONS GETTERS

	//gets the width (in pixels) of the area intended to be drawn on
	public int getDrawWidth();

	//gets the height (in pixels) of the area intended to be drawn on
	public int getDrawHeight();


	//gets the x value of the left most column of pixels that are intended to be drawn on
	public int getMinDrawX();

	//gets the x value of the right most column of pixels that are intended to be drawn on
	public int getMaxDrawX();


	//gets the y value of the top most row of pixels that are intended to be drawn on
	public int getMinDrawY();

	//gets the y value of the top most row of pixels that are intended to be drawn on
	public int getMaxDrawY();

	//return the point on screen relative to the displays internal resolution/canvas
	public Point getLocalCoordinate(Point point);



	//DRAWING METHODS

	//draws a RenderedImage transformed by the given AffineTransform onto the display.
	public void drawRenderedImage(RenderedImage image, AffineTransform transform);

	//draws a RenderedImage at the given location on the display.
	public void drawRenderedImage(RenderedImage image, int x, int y);

	//returns the Graphics2D object that will allow RenderedImages to be drawn to this display (where the drawing is controlled by the image being drawn).
	public Graphics2D getDrawGraphics();



	//STATE CONTROL METHODS

	//initialises the display to a "blank" state and sets the resolution to the given width and height (in pixels) on the parent display
	public void init(int width, int height);

	//initialises the display with its current resolution and sets it to a "blank" state where nothing is considered to have been drawn in the current frame
	public void init();

	//ensures the display is visible on the parent display and updates what is graphically shown on the part of the display intended to be drawn on to the current state of the canvas.
	//if drawSuccess() is false (ie. the current frame is invalid), the display does nothing until a new frame is initiated(ie a varient of init() is called).
	public void display();

	//returns whether the current frame is valid. This returns false if the state of the display was changed such that subsequent draws or displays may be incompatible
	//with the display's previous state (such as when the display resolution was changed mid frame).
	public boolean drawSuccess();

	//returns whether or not the user can see the content of the display
	public boolean isVisible();

	//allows objects to be notified when the display is permanently disposed
	public void addDisposeListener(DisposeListener listener);

	//returns true if the display has the active focus of the user or false otherwise (i.e. if the display is minimised or a different window is selected).
	public boolean isFocusOwner();
}