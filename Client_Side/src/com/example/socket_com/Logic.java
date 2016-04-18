package com.example.socket_com;

import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import android.location.Location;

public class Logic {

	public static final int FACE_HIT = 1, UPPER_BODY_HIT = 2, LOWER_BODY_HIT = 3;

	private JavaCameraView mOpenCvCameraView;
	private Mat mGray, mRgba;
	private int rectSize;
	
	
	//private Rect[] facesArrayWhileShoot, upperBodyArrayWhileShoot, lowerBodyArrayWhileShoot;
	
	public Logic(JavaCameraView CameraView){
		
		this.mOpenCvCameraView = CameraView;
	}
	
	public void setMats(Mat mGray, Mat mRgba){
		this.mGray = mGray;
		this.mRgba = mRgba;
	}
	
	public int getSizeOfRect(){
		return rectSize;
	}

	public int isHit(Rect[] facesArrayWhileShoot, Rect[] upperBodyArrayWhileShoot, Rect[] lowerBodyArrayWhileShoot){

			if(checkForUpperBody(upperBodyArrayWhileShoot))
			return UPPER_BODY_HIT;
		
		if(checkForLowerBody(lowerBodyArrayWhileShoot))
			return LOWER_BODY_HIT;
		
		
		/*
		if(checkForLowerBody(lowerBodyArrayWhileShoot)){
			facesArrayWhileShoot = null;
			upperBodyArrayWhileShoot = null;
			lowerBodyArrayWhileShoot = null;

			return LOWER_BODY_HIT;
		}
		 */

		return -1;
	}

	//check if hit at some face in the frame, if yes return true, else return false
	private boolean checkForFace(Rect[] facesArrayWhileShoot){

		if(facesArrayWhileShoot != null){

			Point sightPoint = getSightPoint();

			for (int i = 0; i < facesArrayWhileShoot.length; i++){
				if(sightPoint.inside(facesArrayWhileShoot[i]))
					return true;
			}
		}

		return false;
	}

	//check if hit at some upper body in the frame, if yes return true, else return false
	private boolean checkForUpperBody(Rect[] upperBodyArrayWhileShoot){


		if(upperBodyArrayWhileShoot != null){

			Point sightPoint = getSightPoint();

			for (int i = 0; i < upperBodyArrayWhileShoot.length; i++){
				if(sightPoint.inside(upperBodyArrayWhileShoot[i])){
					rectSize = upperBodyArrayWhileShoot[i].width;
					return true;
				}
			}
		}
		/*if(upperBodyArrayWhileShoot != null){

				Point sightPoint = getSightPoint();

				for (int i = 0; i < upperBodyArrayWhileShoot.length; i++){
					for (int j = 0; j < facesArrayWhileShoot.length; j++){

						if(facesArrayWhileShoot[j].br().inside(upperBodyArrayWhileShoot[i]) && facesArrayWhileShoot[j].tl().inside(upperBodyArrayWhileShoot[i])){
							if(sightPoint.y >= facesArrayWhileShoot[j].br().y && sightPoint.inside(upperBodyArrayWhileShoot[i]))
								return true;
						}
					}
				}
			}*/

		return false;
	}

	//check if hit at some lower body in the frame, if yes return true, else return false
	private boolean checkForLowerBody(Rect[] lowerBodyArrayWhileShoot){

		if(lowerBodyArrayWhileShoot != null){

			Point sightPoint = getSightPoint();

			for (int i = 0; i < lowerBodyArrayWhileShoot.length; i++){
				if(sightPoint.inside(lowerBodyArrayWhileShoot[i])){
					rectSize = lowerBodyArrayWhileShoot[i].width;
					return true;
				}
			}
		}

		return false;
	}

	//return the weapon sight point
	public Point getSightPoint(){

		return new Point(mOpenCvCameraView.getWidth()/2- getOffset("X"), mOpenCvCameraView.getHeight()/2- getOffset("Y"));
	}

	//return the offset of x and y between the openCV rectangle and the real screen
	//parameter: if xy = X return the x offset, if xy = Y return the y offset, else return -1
	private double getOffset(String xy){

		double w = mRgba.cols();
		double h = mRgba.rows();				

		if(xy.equals("X"))
			return (mOpenCvCameraView.getWidth() - w) / 2;

		else if(xy.equals("Y"))
			return (mOpenCvCameraView.getHeight() - h) / 2;

		else 
			return -1;
	}
	
	public boolean isInjured(Location myLocation, Location otherLocation, float azimuth){
		
		float epsilon = 5f;
		float degree = otherLocation.bearingTo(myLocation);
		
		float digression = Math.abs(degree - azimuth);
		
		//return digression <= epsilon;
		return true;
	}
/*	
	//catch the detection rectangles on current time
		public void catchRect(Rect[] facesArray, Rect[] upperBodyArray, Rect[] lowerBodyArray){

			//for all faces, only if there is any faces in the current camera frame
			if(facesArray != null){
				facesArrayWhileShoot = new Rect[facesArray.length];
				
				for(int i = 0; i < facesArray.length; i++)
					facesArrayWhileShoot[i] = new Rect(facesArray[i].tl(), facesArray[i].br());
			}

			//for all upper bodies, only if there is any upper bodies in the current camera frame
			if(upperBodyArray != null){
				upperBodyArrayWhileShoot = new Rect[upperBodyArray.length];

				for (int i = 0; i < upperBodyArray.length; i++)
					upperBodyArrayWhileShoot[i] = new Rect(upperBodyArray[i].tl(), upperBodyArray[i].br());
			}
			
			//for all lower bodies, only if there is any upper bodies in the current camera frame
			if(lowerBodyArray != null){
				lowerBodyArrayWhileShoot = new Rect[lowerBodyArray.length];

				for (int i = 0; i < lowerBodyArray.length; i++)
					lowerBodyArrayWhileShoot[i] = new Rect(lowerBodyArray[i].tl(), lowerBodyArray[i].br());
			}
		}*/
}
