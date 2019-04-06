package roafan.api;
import java.awt.image.RenderedImage;
import java.awt.geom.AffineTransform;

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



	//DRAWING METHODS

	//draws a RenderedImage transformed by the given AffineTransform onto the display
	public void drawRenderedImage(RenderedImage image, AffineTransform transform);

	//draws a RenderedImage at the given location on the display
	public void drawRenderedImage(RenderedImage image, int x, int y);



	//STATE CONTROL METHODS

	//initialises the display to a "blank" state with the given width and height (in pixels) on the parent display
	public void init(int width, int height);

	//initialises the display to a "blank" state with the given width and height (in pixels) at the given coordinates on the parent display
	public void init(int x, int y, int width, int height);

	//sets the display to a "blank" state where nothing is considered to have been drawn in the current frame
	public void clear();

	//ensures the display is visible on the parent display and updates what is graphically shown on the part of the display intended to be drawn on to the current state of the canvas.
	//if drawSuccess() is false (ie. the current frame is invalid), the display does nothing until a new frame is initiated(ie a varient of init() is called).
	public void display();

	//returns whether the current frame is valid. This returns false if the state of the display was changed such that subsequent draws or displays may be incompatible
	//with the display's previous state (such as when the display resolution was changed mid frame).
	public boolean drawSuccess();
}