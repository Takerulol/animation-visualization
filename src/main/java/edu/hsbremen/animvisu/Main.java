package edu.hsbremen.animvisu;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import edu.hsbremen.animvisu.geom.AbstractGeometry;
import edu.hsbremen.animvisu.geom.Cube;
import edu.hsbremen.animvisu.geom.Cylinder;
import edu.hsbremen.animvisu.geom.Pyramid;
import edu.hsbremen.animvisu.util.SceneFileLoader;

public class Main {

	private static Vector<AbstractGeometry> GEOMETRY_LIST = new Vector<AbstractGeometry>();
	private static int mx,my,mdx,mdy;
	private static float rx,ry,rxt,ryt;
	private static int zoom = -8;
	private static boolean mouseInit = true;
	
	/**
	 * INIT OBJECTS HERE!!!
	 */
	private static void initObjects(String[] args) {
		//load specific file
		if (args.length == 2 && args[0].equals("-f")) {
			GEOMETRY_LIST = SceneFileLoader.loadFile(args[1]);
		}
//		Pyramid p = new Pyramid();
//		p.setPosition(-2, 0, -2);
//		addGeom(p);
//		
//		Cube c = new Cube();
//		c.setPosition(3, 0, -5);
//		addGeom(c);
//		
//		Cylinder ci = new Cylinder();
//		ci.setPosition(0, 0, -5);
//		addGeom(ci);
	}
	
	/**
	 * @param args
	 * @throws LWJGLException 
	 */
	public static void main(String[] args) throws LWJGLException {

		//setup display
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		
		glClearColor(0, 0, 0, 1);
		
		//initializes screen + lighting options
		initGL();
		
		//initialize all given objects
		initObjects(args);
		
		glMatrixMode(GL_PROJECTION);
		glTranslatef(0, 0, zoom);
		glMatrixMode(GL_MODELVIEW);
		
		float r = 0;
		float r2 = 0;
		float r3 = 0;
		
		while(true) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			readInputs();			
			
			//can be used to animate the scene
			r += 1f;
			r2 +=  .1;
			r3 -= -.5;
			Vector3f v = new Vector3f(r,r2,r3);
			
			//draw all objects
			for(AbstractGeometry g : GEOMETRY_LIST) {
				//uncomment to rotate all objects
//				g.setRotation(v);
				g.draw();
			}
			
			Display.sync(60);
			Display.update();
			if(Display.isCloseRequested()) break;
		}
		Display.destroy();
	}

	private static void readInputs() {
		if(!Mouse.isButtonDown(0)) mouseInit = true;
		zoom += Mouse.getDWheel()/100;
		if(mouseInit && Mouse.isButtonDown(0)) {
			mx = Mouse.getX();
			my = Mouse.getY();
			mdx = 0;
			mdy = 0;
			rxt = rx;
			ryt = ry;
			mouseInit = false;
		} else if (Mouse.isButtonDown(0)) {
			mdx = mx - Mouse.getX();
			mdy = my - Mouse.getY();
			
			ry = ryt + mdx;
			rx = rxt + mdy;
			
			//continuous turning
//			rx += mdy/5;
//			ry += mdx/5;
			
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			GLU.gluPerspective(60, 1, 2, 20);
			
			glTranslatef(0, 0, zoom);
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);

//			float eyex = (float) ( Math.sin(rx/180*Math.PI) * Math.sin(ry/180*Math.PI)) * zoom;
//			float eyey = (float) -Math.cos(rx/180*Math.PI) * zoom;
//			float eyez = (float) ( Math.sin(rx/180*Math.PI) * Math.cos(ry/180*Math.PI)) * zoom;
//			GLU.gluLookAt(eyex, eyey, eyez, 0, 0, 0, 0, 1, 0);
			
			glMatrixMode(GL_MODELVIEW);
		}
	}
	
	

	private static void initGL() {
		float[] lp1f = { 100, 50, 100,  0};
		float[] lp2f = { -50, 50, -100,  0};
		float[] fullf = { .5f,  .5f,  .5f,  1};
		float[] redf = { 1.0f,  .8f,  .8f,  1};
		float[] bluef = { .8f,  .8f,  1.0f,  1};
		float[] zerof = {  0,   0,   0,  1};
		
		FloatBuffer lp1 = BufferUtils.createFloatBuffer(4).put(lp1f);
		FloatBuffer lp2 = BufferUtils.createFloatBuffer(4).put(lp2f);
		FloatBuffer full = BufferUtils.createFloatBuffer(4).put(fullf);
		FloatBuffer red = BufferUtils.createFloatBuffer(4).put(redf);
		FloatBuffer blue = BufferUtils.createFloatBuffer(4).put(bluef);
		FloatBuffer zero = BufferUtils.createFloatBuffer(4).put(zerof);
		lp1.flip();
		lp2.flip();
		full.flip();
		red.flip();
		blue.flip();
		zero.flip();
		
		glEnable( GL_DEPTH_TEST);
		glDepthFunc( GL_LESS);
		glClearColor(1,1,1,1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glShadeModel( GL_SMOOTH);
//		glShadeModel( GL_FLAT);
		glLightModeli( GL_LIGHT_MODEL_TWO_SIDE, 1);
		glEnable( GL_LIGHTING);

		glLight( GL_LIGHT1, GL_POSITION, lp1);
		glLight( GL_LIGHT1, GL_DIFFUSE,  red);
		glLight( GL_LIGHT1, GL_SPECULAR, red);
		//glLighti( GL_LIGHT1, GL_CONSTANT_ATTENUATION, 1);
		//glLighti( GL_LIGHT1, GL_LINEAR_ATTENUATION, 0);
		//glLighti( GL_LIGHT1, GL_QUADRATIC_ATTENUATION, 0);
		glEnable( GL_LIGHT1);

		glLight( GL_LIGHT2, GL_POSITION, lp2);
		glLight( GL_LIGHT2, GL_DIFFUSE,  blue);
		glLight( GL_LIGHT2, GL_SPECULAR, blue);
		//glLighti( GL_LIGHT2, GL_CONSTANT_ATTENUATION, 1);
		//glLighti( GL_LIGHT2, GL_LINEAR_ATTENUATION, 0);
		//glLighti( GL_LIGHT2, GL_QUADRATIC_ATTENUATION, 0);
		glEnable( GL_LIGHT2);

		//glEnable( GL_ALPHA_TEST);
		//glEnable( GL_BLEND);
		//glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_NORMALIZE);
		
		glViewport(0, 0, 800, 600);
		glMatrixMode( GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(60, 1, 2, 20);
		glMatrixMode( GL_MODELVIEW);
		glShadeModel(GL_SMOOTH);
		
		glClearColor(0, 0, 0, 1);
		
		mx = 0;
		my = 0;
		mdx = 0;
		mdy = 0;
	}
	
	/**
	 * Add geometry to the scene
	 * @param geom
	 */
	private static void addGeom(AbstractGeometry geom) {
		GEOMETRY_LIST.add(geom);
	}

	

}
