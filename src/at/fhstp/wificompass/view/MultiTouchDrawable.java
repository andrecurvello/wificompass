/*
 * Created on Feb 10, 2012
 * Author: Paul Woelfel
 * Email: frig@frig.at
 */
package at.fhstp.wificompass.view;

import org.metalev.multitouch.controller.MultiTouchController.PointInfo;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Abstract Class for drawable objects for the MultiTouchView
 * 
 * @author Paul Woelfel (paul@woelfel.at)
 */
public abstract class MultiTouchDrawable {

	/**
	 * static counter for a unique drawable Id
	 */
	protected static int counter = 0;

	/**
	 * unique numeric id
	 */
	protected int id;

	/**
	 * current angle of drawable
	 */
	protected float angle = 0;

	/**
	 * x and y scale factors
	 */
	protected float scaleX = 1.0f, scaleY = 1.0f;

	/**
	 * relative x and y position
	 */
	protected float relX = 0, relY = 0;

	/**
	 * Reflects whether a custom pivot point is used or the center of the image
	 */
	protected boolean isCustomPivotUsed = false;

	/**
	 * The pivot point needed for rotation
	 */
	protected float pivotX = 0, pivotY = 0;

	/**
	 * Super drawable
	 */
	protected MultiTouchDrawable superDrawable = null;

	/**
	 * Context
	 */
	protected Context ctx;

	/**
	 * default constructor
	 * 
	 * @param context
	 */
	public MultiTouchDrawable(Context context) {
		id = counter++;
		this.ctx = context;
	}

	/**
	 * constructor with superDrawable
	 * 
	 * @param context
	 * @param superDrawable
	 *            super Drawable Object
	 */
	public MultiTouchDrawable(Context context, MultiTouchDrawable superDrawable) {
		id = counter++;

		this.ctx = context;
		this.superDrawable = superDrawable;
	}

	/**
	 * <p>
	 * returns a Drawable object, to be painted on the MultiTochView.
	 * </p>
	 * <p>
	 * This function is called by the MultiTouchView onDraw method. This should
	 * return a BitmapDrawable or any other Drawable which should represent the
	 * content
	 * </p>
	 * 
	 * @return Drawable to be painted
	 * @see android.graphics.drawable.Drawable
	 */
	public abstract Drawable getDrawable();

	/**
	 * get the width in pixel of the Drawable
	 * 
	 * @return width in pixel
	 */
	public abstract int getWidth();

	/**
	 * get the height in pixel of the Drawable
	 * 
	 * @return height in pixel
	 */
	public abstract int getHeight();

	/**
	 * get an unique ID of one instance
	 * 
	 * @return id
	 */
	public String getId() {
		return this.getClass().getName() + ":" + id;
	}

	/**
	 * callback method for onTouch events
	 * 
	 * @param pointinfo
	 *            info about the touch event
	 * @return true if the touch event is handled, false if it should be handled
	 *         by the MultiTouchController
	 */
	public abstract boolean onTouch(PointInfo pointinfo);

	/**
	 * set Angle of the Drawable
	 * 
	 * @param angle
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/**
	 * set scaleing
	 * 
	 * @param scaleX
	 * @param scaleY
	 */
	public void setScale(float scaleX, float scaleY) {
		if (isScalable()) {
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		}
	}

	/**
	 * set relative position to super object if set
	 * 
	 * @param xPos
	 *            relative x position
	 * @param yPos
	 *            relative y position
	 */
	public void setRelativePosition(float relX, float relY) {
		this.relX = relX;
		this.relY = relY;
	}

	/**
	 * Get the x position of the drawable relative to it's super-drawable (if
	 * set)
	 * 
	 * @return The x position relative to it's super drawable
	 */
	public float getRelativeX() {
		return this.relX;
	}

	/**
	 * Get the y position of the drawable relative to it's super-drawable (if
	 * set)
	 * 
	 * @return The y position relative to it's super drawable
	 */
	public float getRelativeY() {
		return this.relY;
	}

	/**
	 * Returns whether a custom pivot point is used for this drawable. If not,
	 * the image center will be used.
	 * 
	 * @return Whether a custom pivot is used or not
	 */
	public boolean isCustomPivotUsed() {
		return this.isCustomPivotUsed;
	}

	/**
	 * Sets the pivot point or, in other words, the semantic center of the
	 * image. This is the point relative to which sub-drawables will be
	 * positioned when the super-drawable is scaled and rotated.
	 * 
	 * @param pivotX
	 *            The x-coordinate of the pivot point
	 * @param pivotY
	 *            The y-coordinate of the pivot point
	 */
	public void setPivot(float pivotX, float pivotY) {
		this.pivotX = pivotX;
		this.pivotY = pivotY;

		// This means a custom pivot point is used
		this.isCustomPivotUsed = true;
	}

	/**
	 * Returns the the x coordinate of the pivot point
	 * 
	 * @return The x coordinate of the pivot point
	 */
	public float getPivotX() {
		return this.pivotX;
	}

	/**
	 * Returns the the y coordinate of the pivot point
	 * 
	 * @return The y coordinate of the pivot point
	 */
	public float getPivotY() {
		return this.pivotY;
	}

	/**
	 * Returns the the x coordinate of the pivot point relative to the image's
	 * center. This is useful when the image is by default positioned relative
	 * to it's center and has to be moved from there.
	 * 
	 * @return The x coordinate of the pivot point relative to the image's
	 *         center
	 */
	public float getPivotXRelativeToCenter() {
		return this.pivotX - this.getWidth() / 2;
	}

	/**
	 * Returns the the y coordinate of the pivot point relative to the image's
	 * center. This is useful when the image is by default positioned relative
	 * to it's center and has to be moved from there.
	 * 
	 * @return The y coordinate of the pivot point relative to the image's
	 *         center
	 */
	public float getPivotYRelativeToCenter() {
		return this.pivotY - this.getHeight() / 2;
	}

	/**
	 * does this object support scaleing, or should it stay the same size all
	 * the time
	 * 
	 * @return true if scalable
	 */
	public abstract boolean isScalable();

	/**
	 * does this object support rotateing, or should it have the same angle all
	 * the time
	 * 
	 * @return true if rotateable
	 */
	public abstract boolean isRotateable();

	/**
	 * does this object support dragging, or should it stay where it is
	 * 
	 * @return true if dragable
	 */
	public abstract boolean isDragable();

	/**
	 * is this object only allowed to be positioned in super object
	 * 
	 * @return true, if this object must be on the super object
	 */
	public abstract boolean isOnlyInSuper();

	/**
	 * does this object have a Drawable, which it corresponds to and should be
	 * modified with its super Drawable, i.e. scaled or moved.
	 * 
	 * @return true, if it suppports and has a super Drawable
	 */
	public boolean hasSuperDrawable() {
		return (superDrawable == null ? false : true);
	}

	/**
	 * get the super Drawable
	 * 
	 * @return MultiTouchDrawable if existent, otherwise null
	 */
	public MultiTouchDrawable getSuperDrawable() {
		return superDrawable;
	}

}
