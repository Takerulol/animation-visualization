package edu.hsbremen.animvisu;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import edu.hsbremen.animvisu.geom.AbstractGeometry;
import edu.hsbremen.animvisu.geom.Cube;
import edu.hsbremen.animvisu.geom.Cylinder;
import edu.hsbremen.animvisu.geom.Pyramid;

public class Main {

	private static Vector<AbstractGeometry> DISPLAY_LIST = new Vector<AbstractGeometry>();
	
	
	private static void initObjects() {
		Pyramid p = new Pyramid();
		p.setPosition(-2, 0, -2);
		addGeom(p);
		
		Cube c = new Cube();
		c.setPosition(3, 0, -5);
		addGeom(c);
		
		Cylinder ci = new Cylinder();
		ci.setPosition(0, 0, -5);
		addGeom(ci);
	}
	
	/**
	 * @param args
	 * @throws LWJGLException 
	 */
	public static void main(String[] args) throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		
		glClearColor(0, 0, 0, 1);
		
		//initializes screen + lighting options
		initGL();
		
		//initialize all given objects
		initObjects();
		
		glMatrixMode(GL_PROJECTION);
		glTranslatef(0, 0, -5);
		glMatrixMode(GL_MODELVIEW);
		
		float r = 0;
		float r2 = 0;
		float r3 = 0;
		
		while(true) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			r += 1f;
			r2 +=  .1;
			r3 -= -.5;
			Vector3f v = new Vector3f(r,r2,r3);
			//draw all objects
			for(AbstractGeometry g : DISPLAY_LIST) {
				g.setRotation(v);
				g.draw();
			}
			
			Display.sync(60);
			Display.update();
			if(Display.isCloseRequested()) break;
		}
		Display.destroy();
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
		//glShadeModel( GL_FLAT);
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
		
		glViewport(0, 0, 800, 600);
		glMatrixMode( GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(60, 1, 2, 20);
		glMatrixMode( GL_MODELVIEW);
		glShadeModel(GL_SMOOTH);
		
		glClearColor(0, 0, 0, 1);
	}
	
	private static void addGeom(AbstractGeometry geom) {
		DISPLAY_LIST.add(geom);
	}

	

}
