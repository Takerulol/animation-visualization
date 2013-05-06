/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsbremen.animvisu.geom;

import edu.hsbremen.animvisu.util.VectorUtil;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Svenja
 */
public class Cylinder extends AbstractGeometry {
    
    private int triangleCount;

   
    
    public Cylinder() {
        super();
        this.triangleCount = 20;
    }
    
    public Cylinder(int triangleCount) {
        super();
        this.triangleCount = triangleCount;
    }
    
    @Override
    protected void setGeometry() {

        Vector3f m1 = new Vector3f(0,1,0);        
        Vector3f m2 = new Vector3f(0,-1,0);
        float radius = 1;
        
        Vector3f p11;
        Vector3f p12;
        Vector3f p21;
        Vector3f p22;

        Vector3f normal;

        //Zeichnen
        glBegin(GL_TRIANGLES);
            
            for(int i=0; i<this.triangleCount; i++){
                //triagle in upper circle
                p11 = new Vector3f((float)Math.cos(360/this.triangleCount*i),m1.y,(float)Math.sin(360/this.triangleCount*i));   
                p12 = new Vector3f((float)Math.cos(360/this.triangleCount*(i+1)),m1.y,(float)Math.sin(360/this.triangleCount*(i+1)));
                normal = VectorUtil.createNormal(p12, m1, p11);
                glNormal3f(normal.x, normal.y, normal.z);
                glVertex3f(m1.x,m1.y,m1.z);
                glVertex3f(p11.x,p11.y,p11.z);
                glVertex3f(p12.x,p12.y,p12.z);
                
                //triagle in lower circle
                p21 = new Vector3f((float)Math.cos(360/this.triangleCount*i),m2.y,(float)Math.sin(360/this.triangleCount*i));   
                p22 = new Vector3f((float)Math.cos(360/this.triangleCount*(i+1)),m2.y,(float)Math.sin(360/this.triangleCount*(i+1)));
                normal = VectorUtil.createNormal(p21, m2, p22);
                glNormal3f(normal.x, normal.y, normal.z);
                glVertex3f(m2.x,m2.y,m2.z);
                glVertex3f(p21.x,p21.y,p21.z);
                glVertex3f(p22.x,p22.y,p22.z);
                
                //walls
                normal = VectorUtil.createNormal(p22, p11, p12);
                glNormal3f(normal.x, normal.y, normal.z);
                glVertex3f(p11.x,p11.y,p11.z);
                glVertex3f(p22.x,p22.y,p22.z);                
                glVertex3f(p12.x,p12.y,p12.z);
                
                normal = VectorUtil.createNormal(p11, p22, p21);
                glNormal3f(normal.x, normal.y, normal.z);
                glVertex3f(p11.x,p11.y,p11.z);
                glVertex3f(p21.x,p21.y,p21.z);                
                glVertex3f(p22.x,p22.y,p22.z);           
                
            }                    

        glEnd();
    }
    
     public int getTriangleCount() {
        return triangleCount;
    }

    public void setTriangleCount(int triangleCount) {
        this.triangleCount = triangleCount;
    }
    
}
