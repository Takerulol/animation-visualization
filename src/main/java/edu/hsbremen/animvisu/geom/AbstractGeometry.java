package edu.hsbremen.animvisu.geom;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractGeometry {
	
	private Vector3f position, rotation,colorFront,colorBack;
	private float size;
	public int list;
	
	
	public AbstractGeometry() {
		setPosition(new Vector3f());
		setRotation(new Vector3f());
		colorFront = new Vector3f();
		colorFront.set(1, 0, 0);
		colorBack = new Vector3f();
		colorBack.set(0, 0, 1);
		setSize(1f);
		init();
	}
	
	/**
	 * 
	 */
	protected abstract void setGeometry();
	
	private void init() {
		list = glGenLists(1);
		glNewList(list,GL_COMPILE);
		setGeometry();
		glEndList();
	}

	public final void draw() {
		glPushMatrix();
			glLoadIdentity();
			glScalef(size,size,size);
			glTranslatef(position.x,position.y,position.z);
			glRotatef(rotation.x,1,0,0);
			glRotatef(rotation.y,0,1,0);
			glRotatef(rotation.z,0,0,1);
			setGlMaterialColor(1,colorFront.x,colorFront.y,colorFront.z);
			setGlMaterialColor(2,colorBack.x,colorBack.y,colorBack.z);
			glCallList(list);
		glPopMatrix();
	}

	private void setGlMaterialColor( int side, float r, float g, float b) {
		float[] amb = new float[4];
		float[] dif = new float[4];
		float[] spe = new float[4];

		int	i, mat;

		dif[0] = r;
		dif[1] = g;
		dif[2] = b;
		
		//System.out.println(r+"+"+g+"+"+b);
		
		for( i=0; i<3; i++){
			amb[i] = .1f * dif[i];
			spe[i] = .5f;
		}
		amb[3] = 1.0f;
		dif[3] = 1.0f;
		spe[3] = 1.0f;
		switch( side){
			case 1:	mat = GL_FRONT; break;
			case 2:	mat = GL_BACK; break;
			default: mat = GL_FRONT_AND_BACK; break;
		}
		
		FloatBuffer ambBuf = BufferUtils.createFloatBuffer(4);
		ambBuf.put(amb);
		ambBuf.flip();
		
		FloatBuffer difBuf = BufferUtils.createFloatBuffer(4);
		difBuf.put(dif).flip();
		
		FloatBuffer speBuf = BufferUtils.createFloatBuffer(4);
		speBuf.put(spe).flip();
		
		glMaterial( mat, GL_AMBIENT, ambBuf);
		glMaterial( mat, GL_DIFFUSE, difBuf);
		glMaterial( mat, GL_SPECULAR, speBuf);
		glMaterialf( mat, GL_SHININESS, 20);
	}
	
	
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getColorFront() {
		return colorFront;
	}

	public void setColorFront(Vector3f colorFront) {
		this.colorFront = colorFront;
	}

	public Vector3f getColorBack() {
		return colorBack;
	}

	public void setColorBack(Vector3f colorBack) {
		this.colorBack = colorBack;
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public void setRotation(float rx, float ry, float rz) {
		rotation.x = rx;
		rotation.y = ry;
		rotation.z = rz;
	}
}
