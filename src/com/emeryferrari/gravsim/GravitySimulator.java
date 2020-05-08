package com.emeryferrari.gravsim;
import javax.swing.*;
import java.awt.*;
import java.math.*;
public class GravitySimulator extends JComponent {
	private static final long serialVersionUID = 1L;
	double camDist = 50.0;
	double xLength = 400.0;
	double yLength = 400.0;
	double zLength = 400.0;
	double k = 0;
	double sx;
	double sy;
	double sz;
	int counter = -1;
	double[] pointArrayX = new double[10000];
	double[] pointArrayY = new double[10000];
	double[] pointArrayZ = new double[10000];
	double[] forceArrayX = new double[10000];
	double[] forceArrayY = new double[10000];
	double[] forceArrayZ = new double[10000];
	double forceArrayXSum;
	double forceArrayYSum;
	double forceArrayZSum;
	double[] totalForceX = new double[10000];
	double[] totalForceY = new double[10000];
	double[] totalForceZ = new double[10000];
	double[] initVelocityX = new double[10000];
	double[] initVelocityY = new double[10000];
	double[] initVelocityZ = new double[10000];
	double initVelocityStrength = 0.1;
	double gForce = 0.1;
	double gDampEffect = 0.0;
	double t = 0;
	double[] massArray = new double[10000];
	double massStrength = 1.0;
	boolean boolTest = false;
	boolean distanceLimitCon = false;
	double distanceLimit = 50.0;
	double rarity = 0.1;
	boolean halt = false;
	double lineCutoff = 0.125;
	double distMitigator = 1.0;
	boolean bigBang = true;
	double emitSpeed = 0.0;
	double initSize = 5.0;
	boolean shellVelocity = false;
	boolean shell = false;
	boolean sphereMode = true;
	double spin = 0.0;
	double spinPow = 1.0;
	boolean cells = true;
	double[] cellArrayX = new double[10000];
	boolean[] cellArrayXTest = new boolean[10000];
	double[] cellArrayY = new double[10000];
	boolean[] cellArrayYTest = new boolean[10000];
	double[] cellArrayZ = new double[10000];
	boolean[] cellArrayZTest = new boolean[10000];
	double[] cellMass = new double[10000];
	double cellSize = 1;
	double minDist = 1;
	double[] sortDistArray = new double[10000];
	boolean densityDisplay = true;
	double whiteStrength = 12.5;
	double unitSize = 10.0;
	boolean useLines = false;
	JFrame frame;
	public static void main(String[] args) {
		new GravitySimulator().start();
	}
	public void start() {
		frame = new JFrame(GravSimConst.FULL_NAME);
		frame.setSize(1250, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.CENTER, this);
		frame.setVisible(true);
		startRender();
	}
	public void startRender() {
		Thread renderer = new Renderer();
		renderer.start();
	}
	@Override
	public void paintComponent(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		if (!boolTest) {
			for (double x = 0; x <= xLength; x += 10) {
				for (double y = 0; y <= yLength; y += 10) {
					for (double z = 0; z <= zLength; z += 10) {
						k = Math.random();
						if (k >= 1-rarity && Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2)) <= 1 && sphereMode) {
							if (!bigBang) {
								counter++;
								if (!shell) {
									pointArrayX[counter] = (x/200-1)*initSize;
									pointArrayY[counter] = (y/200-1)*initSize;
									pointArrayZ[counter] = (z/200-1)*initSize;
								} else {
									pointArrayX[counter] = (x/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayY[counter] = (y/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayY[counter] = (z/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
								}
								totalForceX[counter] = 0;
								totalForceY[counter] = 0;
								totalForceZ[counter] = 0;
								massArray[counter] = (k-1+rarity)/rarity*massStrength+1;
								sx = Math.random();
								initVelocityX[counter] = (sx-0.5)*initVelocityStrength;
								sy = Math.random();
								initVelocityY[counter] = (sy-0.5)*initVelocityStrength;
								sz = Math.random();
								initVelocityZ[counter] = (sz-0.5)*initVelocityStrength;
							} else {
								counter++;
								if (!shell) {
									pointArrayX[counter] = (x/200-1)*initSize;
									pointArrayY[counter] = (y/200-1)*initSize;
									pointArrayZ[counter] = (z/200-1)*initSize;
								} else {
									pointArrayX[counter] = (x/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayY[counter] = (y/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayZ[counter] = (z/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
								}
								totalForceX[counter] = 0;
								totalForceY[counter] = 0;
								totalForceZ[counter] = 0;
								massArray[counter] = (k-1+rarity)/rarity*massStrength+1;
								if (!shellVelocity) {
									initVelocityX[counter] = initVelocityStrength*(x/200-1);
									initVelocityY[counter] = initVelocityStrength*(y/200-1);
									initVelocityZ[counter] = initVelocityStrength*(z/200-1);
								} else {
									initVelocityX[counter] = initVelocityStrength*pointArrayX[counter];
									initVelocityY[counter] = initVelocityStrength*pointArrayY[counter];
									initVelocityZ[counter] = initVelocityStrength*pointArrayZ[counter];
								}
							}
						} else if (!sphereMode && k >= 1-rarity && Math.abs(y-200) <= 200 && Math.abs(z-200) <= 200 && Math.abs(x-200) <= 200) {
							if (!bigBang) {
								counter++;
								if (!shell) {
									pointArrayX[counter] = (x/200-1)*initSize;
									pointArrayY[counter] = (y/200-1)*initSize;
									pointArrayZ[counter] = (z/200-1)*initSize;
								} else {
									pointArrayX[counter] = (x/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayY[counter] = (y/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayZ[counter] = (z/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;									
								}
								totalForceX[counter] = 0;
								totalForceY[counter] = 0;
								totalForceZ[counter] = 0;
								massArray[counter] = (k-1+rarity)/rarity*massStrength+1;
								sx = Math.random();
								initVelocityX[counter] = (sx-0.5)*initVelocityStrength;
								sy = Math.random();
								initVelocityY[counter] = (sy-0.5)*initVelocityStrength;
								sz = Math.random();
								initVelocityZ[counter] = (sz-0.5)*initVelocityStrength;
							} else {
								counter++;
								if (!shell) {
									pointArrayX[counter] = (x/200-1)*initSize;
									pointArrayY[counter] = (y/200-1)*initSize;
									pointArrayZ[counter] = (z/200-1)*initSize;
								} else {
									pointArrayX[counter] = (x/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayY[counter] = (y/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
									pointArrayZ[counter] = (z/200-1)/(Math.sqrt(Math.pow(x/200-1, 2) + Math.pow(y/200-1, 2) + Math.pow(z/200-1, 2))+0.05)*initSize;
								}
								totalForceX[counter] = 0;
								totalForceY[counter] = 0;
								totalForceZ[counter] = 0;
								massArray[counter] = (k-1+rarity)/rarity*massStrength+1;
								if (!shellVelocity) {
									initVelocityX[counter] = initVelocityStrength*(x/200-1);
									initVelocityY[counter] = initVelocityStrength*(y/200-1);
									initVelocityZ[counter] = initVelocityStrength*(z/200-1);
								} else {
									initVelocityX[counter] = initVelocityStrength*pointArrayX[counter];
									initVelocityY[counter] = initVelocityStrength*pointArrayY[counter];
									initVelocityZ[counter] = initVelocityStrength*pointArrayZ[counter];
								}
							}
						}
						if (counter > -1) {
							initVelocityX[counter] += pointArrayZ[counter]/initSize*spin/Math.pow((Math.sqrt(Math.pow(pointArrayX[counter], 2) + Math.pow(pointArrayZ[counter], 2)))+0.05, spinPow);
							initVelocityZ[counter] -= pointArrayX[counter]/initSize*spin/Math.pow((Math.sqrt(Math.pow(pointArrayX[counter], 2) + Math.pow(pointArrayZ[counter], 2)))+0.05, spinPow);
							cellMass[counter] = 0;
						} else {
							counter++;
							initVelocityX[counter] += pointArrayZ[counter]/initSize*spin/Math.pow((Math.sqrt(Math.pow(pointArrayX[counter], 2) + Math.pow(pointArrayZ[counter], 2)))+0.05, spinPow);
							initVelocityZ[counter] -= pointArrayX[counter]/initSize*spin/Math.pow((Math.sqrt(Math.pow(pointArrayX[counter], 2) + Math.pow(pointArrayZ[counter], 2)))+0.05, spinPow);
							cellMass[counter] = 0;
						}
					}
				}
			}
			/*pointArrayX[counter+1] = 1;
			pointArrayY[counter+1] = 0;
			pointArrayZ[counter+1] = 0;
			pointArrayX[counter+2] = 0;
		    pointArrayY[counter+2] = 1;
		    pointArrayZ[counter+2] = 0;
		    pointArrayX[counter+3] = 0;
		    pointArrayY[counter+3] = 0;
		    pointArrayZ[counter+3] = 1;
		    pointArrayX[counter+4] = 0;
		    pointArrayY[counter+4] = 0;
		    pointArrayZ[counter+4] = 0;*/
		    massArray[counter+1] = 1;
		    massArray[counter+2] = 1;
		    massArray[counter+3] = 1;
		    massArray[counter+3] = 1;
		    boolTest = true;
		}
		
		double distance;
		if (!halt) {
			for (int x = 0; x < counter; x++) {
				forceArrayXSum = 0;
				forceArrayYSum = 0;
				forceArrayZSum = 0;
				cellArrayX[x] = round(pointArrayX[x]-cellSize/2, (int)cellSize)+cellSize/2;
				cellArrayXTest[x] = true;
				cellArrayY[x] = round(pointArrayY[x]-cellSize/2, (int)cellSize)+cellSize/2;
				cellArrayYTest[x] = true;
				cellArrayZ[x] = round(pointArrayZ[x]-cellSize/2, (int)cellSize)+cellSize/2;
				cellArrayZTest[x] = true;
				cellMass[x] = massArray[x];
				for (int y = 0; y < counter; y++) {
					if (cellArrayX[x] == cellArrayX[y] && cellArrayY[x] == cellArrayY[y] && cellArrayZ[x] == cellArrayZ[y] && y != x && y < x) {
						cellArrayXTest[x] = false;
						cellArrayYTest[x] = false;
						cellArrayZTest[x] = false;
						cellMass[y] += cellMass[y];
					}
					if (Math.abs(pointArrayX[x]-pointArrayX[y]) <= minDist && Math.abs(pointArrayY[x]-pointArrayY[y]) <= minDist && Math.abs(pointArrayZ[x]-pointArrayZ[y]) <= minDist && cells) {
						distance = Math.sqrt((pointArrayX[x]-pointArrayX[y])*(pointArrayX[x]-pointArrayX[y])+(pointArrayY[x]-pointArrayY[y])*(pointArrayY[x]-pointArrayY[y])+(pointArrayZ[x]-pointArrayZ[y])*(pointArrayZ[x]-pointArrayZ[y]));
						forceArrayXSum = forceArrayXSum + (pointArrayX[x]-pointArrayX[y])*gForce*massArray[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
						forceArrayYSum = forceArrayYSum + (pointArrayY[x]-pointArrayY[y])*gForce*massArray[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
						forceArrayZSum = forceArrayZSum + (pointArrayZ[x]-pointArrayZ[y])*gForce*massArray[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
					} else if (!cells) {
						distance = Math.sqrt((pointArrayX[x]-pointArrayX[y])*(pointArrayX[x]-pointArrayX[y])+(pointArrayY[x]-pointArrayY[y])*(pointArrayY[x]-pointArrayY[y])+(pointArrayZ[x]-pointArrayZ[y])*(pointArrayZ[x]-pointArrayZ[y]));
						forceArrayXSum = forceArrayXSum + (pointArrayX[x]-pointArrayX[y])*gForce*massArray[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
						forceArrayYSum = forceArrayYSum + (pointArrayY[x]-pointArrayY[y])*gForce*massArray[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
						forceArrayZSum = forceArrayZSum + (pointArrayZ[x]-pointArrayZ[y])*gForce*massArray[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
					}
				}
				for (int y = 0; y < counter; y++) {
					if (cellArrayXTest[y] && cellArrayYTest[y] && cellArrayZTest[y] && cells) {
						if (Math.abs(pointArrayX[x]-cellArrayX[y]) > minDist && Math.abs(pointArrayY[x]-cellArrayY[y]) > minDist && Math.abs(pointArrayZ[x]-cellArrayZ[y]) > minDist) {
							distance = Math.sqrt((pointArrayX[x]-cellArrayX[y])*(pointArrayX[x]-cellArrayX[y])+(pointArrayY[x]-cellArrayY[y])*(pointArrayY[x]-cellArrayY[y])+(pointArrayZ[x]-cellArrayZ[y])*(pointArrayZ[x]-cellArrayZ[y]));
							forceArrayXSum = forceArrayXSum + (pointArrayX[x]-cellArrayX[y])*gForce*cellMass[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
					        forceArrayYSum = forceArrayYSum + (pointArrayY[x]-cellArrayY[y])*gForce*cellMass[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
					        forceArrayZSum = forceArrayZSum + (pointArrayZ[x]-cellArrayZ[y])*gForce*cellMass[y]/(Math.pow(distance, 3)+Math.pow(distMitigator, 2));
						}
					}
				}
				forceArrayX[x] = forceArrayXSum;
			    forceArrayY[x] = forceArrayYSum;
			    forceArrayZ[x] = forceArrayZSum;
			    totalForceX[x] = totalForceX[x] + forceArrayX[x]/60.0;
			    totalForceY[x] = totalForceY[x] + forceArrayY[x]/60.0;
			    totalForceZ[x] = totalForceZ[x] + forceArrayZ[x]/60.0;
			}
			for (int i = 0; i < counter; i++) {
				pointArrayX[i] += initVelocityX[i]-totalForceX[i]/200;
				pointArrayY[i] += initVelocityY[i]-totalForceY[i]/200;
				pointArrayZ[i] += initVelocityZ[i]-totalForceZ[i]/200;
			}
		}
		//System.out.println(counter);
		t += 0.001;
		gForce += t*gDampEffect;
		int[] edgeArray1 = {counter+4, counter+4, counter+4};
		int[] edgeArray2 = {counter+1, counter+2, counter+3};
		double[] storageX = new double[10000];
		double[] storageY = new double[10000];
		double[] dist = new double[10000];
		double camPosX;
		double camPosY;
		double camPosZ;
		double perspectiveStrength = 0.0;
		double viewAngle = 0.56;
		double[] camScale = new double[10000];
		double scale = 125.0;
		double sensitivity = 125.0;
		double x;
		double y;
		double xTransform;
		double yTransform;
		double viewAngleX;
		double viewAngleY;
		graphics.fillOval(frame.getWidth()/2, frame.getHeight()/2, 5, 5);
		for (int xx = 0; xx < counter; xx++) {
			
			double zAngle = Math.atan(pointArrayZ[xx]/pointArrayX[xx]);
			if (pointArrayX[xx] == 0 && pointArrayZ[xx] == 0) {
				zAngle = 0;
			}
			double mag = Math.sqrt(Math.pow(pointArrayX[xx], 2) + Math.pow(pointArrayZ[xx], 2));
			Point mouse = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
			viewAngleY = -(mouse.y-frame.getHeight()/2)/sensitivity;
			if (Math.abs(mouse.y-frame.getHeight()/2) > Math.PI/2*sensitivity) {
				if (viewAngleY < 0) {
					viewAngleY = -Math.PI/2*sensitivity;
				} else {
					viewAngleY = Math.PI/2*sensitivity;
				}
			}
			viewAngleX = -(mouse.x-frame.getWidth()/2)/sensitivity;
			if (pointArrayX[xx] < 0) {
				xTransform = -mag*scale*(Math.cos(viewAngleX+zAngle));
				yTransform = -mag*scale*Math.sin(viewAngleX+zAngle)*Math.sin(viewAngleY)+pointArrayY[xx]*scale*(Math.cos(viewAngleY));
			} else {
				xTransform = mag*scale*(Math.cos(viewAngleX+zAngle));
				yTransform = mag*scale*Math.sin(viewAngleX+zAngle)*Math.sin(viewAngleY)+pointArrayY[xx]*scale*(Math.cos(viewAngleY));
			}
			camPosX = camDist*Math.sin(viewAngleX)*Math.cos(viewAngleY);
			camPosY = -camDist*Math.sin(viewAngleY);
			camPosZ = camDist*Math.cos(viewAngleX)*Math.cos(viewAngleY);
			dist[xx] = Math.sqrt((camPosX-pointArrayX[xx])*(camPosX-pointArrayX[xx])+(camPosY-pointArrayY[xx])*(camPosY-pointArrayY[xx])+(camPosZ-pointArrayZ[xx])*(camPosZ-pointArrayZ[xx]));
			double theta = Math.asin((Math.sqrt(xTransform*xTransform+yTransform*yTransform)/scale)/dist[xx]);
		    camScale[xx] = dist[xx]*Math.cos(theta)*Math.sin(viewAngle/2);
		    storageX[xx] = frame.getWidth()/2+xTransform/camScale[xx];
		    storageY[xx] = frame.getHeight()/2-yTransform/camScale[xx];
		}
		sortDistArray = dist;
		for (int xx = 0; xx < counter; xx++) {
			if (!densityDisplay) {
				graphics.fillOval((int)storageX[(int)sortDistArray[xx]], (int)storageY[(int)sortDistArray[xx]], 5, 5);
			} else {
				for (int yy = 1; yy < 6; yy++) {
					graphics.setColor(new Color(255, 255, 255, (int)(massArray[xx]*whiteStrength*Math.pow(yy, 1)/Math.pow(6, 1))));
					graphics.fillOval((int)(storageX[xx]+unitSize/2*(6-yy)/camScale[xx]), (int)(storageY[xx]+unitSize/2*(6-yy)/camScale[xx]), (int)(unitSize*yy/camScale[xx]), (int)(unitSize*yy/camScale[xx]));
				}
			}
		}
		graphics.setColor(Color.WHITE);
		if (useLines) {
			for (int xx = 0; xx < counter; xx++) {
				for (int yy = 0; yy < counter; yy++) {
					if (Math.sqrt((pointArrayX[xx]-pointArrayX[yy])*(pointArrayX[xx]-pointArrayX[yy])+(pointArrayY[xx]-pointArrayY[yy])*(pointArrayY[xx]-pointArrayY[yy])+(pointArrayZ[xx]-pointArrayZ[yy])*(pointArrayZ[xx]-pointArrayZ[yy])) < lineCutoff) {
						graphics.drawLine((int)storageX[xx], (int)storageY[xx], (int)storageX[yy], (int)storageY[yy]);
					}
				}
			}
		}
		graphics.setColor(new Color(220, 220, 220));
		graphics.fillRect(frame.getWidth()-75, frame.getHeight()-25, 65, 15);
		graphics.fillRect(frame.getWidth()-150, frame.getHeight()-25, 65, 15);
		graphics.fillRect(frame.getWidth()-225, frame.getHeight()-25, 65, 15);
		graphics.fillRect(frame.getWidth()-300, frame.getHeight()-25, 65, 15);
	}
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	private class Renderer extends Thread {
		@Override
		public void run() {
			int targetFps = 60;
			int optimalTime = 1000000000/targetFps;
			int fps = 0;
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			while (true) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    lastFpsTime += updateLength;
			    fps++;
			    if (lastFpsTime >= 1000000000) {
			    	System.out.println("FPS: " + fps);
			        lastFpsTime = 0;
			        fps = 0;
			    }
			    renderFrame();
			    try {Thread.sleep((lastLoopTime-System.nanoTime()+optimalTime)/1000000);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
		}
		private void renderFrame() {
			repaint();
		}
	}
}