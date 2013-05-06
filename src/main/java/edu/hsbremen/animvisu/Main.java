package edu.hsbremen.animvisu;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {

	/**
	 * @param args
	 * @throws LWJGLException 
	 */
	public static void main(String[] args) throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		
		glClearColor(0, 0, 0, 1);
		
		glViewport(0, 0, 800, 600);
		glEnable( GL_DEPTH_TEST);
		glDepthFunc( GL_LESS);
		glMatrixMode( GL_PROJECTION);
		glLoadIdentity();
		glOrtho( -15, 15, -10, 10, -20, 20);
		glMatrixMode( GL_MODELVIEW);
		glShadeModel(GL_SMOOTH);
		float r = 0;
		while(true) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			r += 1;
			glRotatef(r, 0, 1, 0);
			
			glBegin(GL_QUADS);
				glColor3f(1, 0, 0);
				glVertex3f(1, -1, 0);
				glVertex3f(1, 1, 0);
				glVertex3f(-1, 1, 0);
				glVertex3f(-1, -1, 0);
			glEnd();
			
			Display.sync(60);
			Display.update();
			if(Display.isCloseRequested()) break;
		}
		Display.destroy();
	}

}
