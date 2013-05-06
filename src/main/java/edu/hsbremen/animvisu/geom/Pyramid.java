package edu.hsbremen.animvisu.geom;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

import edu.hsbremen.animvisu.util.VectorUtil;

public class Pyramid extends AbstractGeometry {

	public Pyramid() {
		super();
	}
	
	@Override
	protected void setGeometry() {
		
		//0,1,0
		//1,-1,1
		//1,-1,-1
		//-1,-1,-1
		//-1,-1,1
		Vector3f p1 = new Vector3f(0,1,0);
		Vector3f p2 = new Vector3f(1,-1,1);
		Vector3f p3 = new Vector3f(1,-1,-1);
		Vector3f p4 = new Vector3f(-1,-1,-1);
		Vector3f p5 = new Vector3f(-1,-1,1);
		Vector3f normal; 

		glBegin(GL_TRIANGLES);
		
			//4 pyramiden seiten
			normal = VectorUtil.createNormal(p1,p2,p3);
			glNormal3f(normal.x,normal.y,normal.z);
			glVertex3f(0,1,0);
			glVertex3f(1,-1,1);
			glVertex3f(1,-1,-1);
	
			normal = VectorUtil.createNormal(p1,p3,p4);
			glNormal3f(normal.x,normal.y,normal.z);
			glVertex3f(0,1,0);
			glVertex3f(1,-1,-1);
			glVertex3f(-1,-1,-1);
	
			normal = VectorUtil.createNormal(p1,p4,p5);
			glNormal3f(normal.x,normal.y,normal.z);
			glVertex3f(0,1,0);
			glVertex3f(-1,-1,-1);
			glVertex3f(-1,-1,1);
	
			normal = VectorUtil.createNormal(p1,p5,p2);
			glNormal3f(normal.x,normal.y,normal.z);
			glVertex3f(0,1,0);
			glVertex3f(-1,-1,1);
			glVertex3f(1,-1,1);
	
			//grundfläche
			normal = VectorUtil.createNormal(p2,p3,p4);
			glNormal3f(normal.x,normal.y,normal.z);
			glVertex3f(1,-1,1);
			glVertex3f(-1,-1,-1);
			glVertex3f(1,-1,-1);
			
	
			normal = VectorUtil.createNormal(p4,p5,p2);
			glNormal3f(normal.x,normal.y,normal.z);
			glVertex3f(-1,-1,-1);
			glVertex3f(1,-1,1);
			glVertex3f(-1,-1,1);
		glEnd();
	}

}
