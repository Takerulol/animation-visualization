package edu.hsbremen.animvisu.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Vector;

import org.lwjgl.util.vector.Vector3f;

import edu.hsbremen.animvisu.geom.AbstractGeometry;
import edu.hsbremen.animvisu.geom.Cube;
import edu.hsbremen.animvisu.geom.Cylinder;
import edu.hsbremen.animvisu.geom.Pyramid;

public class SceneFileLoader {
	public static Vector<AbstractGeometry> loadFile(String path) {
		Vector<AbstractGeometry> v = new Vector<AbstractGeometry>();
		
		try {
			FileInputStream f = new FileInputStream(path);
			DataInputStream d = new DataInputStream(f);
			BufferedReader b = new BufferedReader(new InputStreamReader(d));
			
			String line;
			
			while((line = b.readLine()) != null) {
				line = line.trim().replaceAll("\\s+", " ");
				Scanner s = new Scanner(line);
				AbstractGeometry geom = createModel(s.next(),s.nextFloat(),s.nextFloat(),s.nextFloat(),
						s.nextFloat(),s.nextFloat(),s.nextFloat(),s.nextFloat());
				if (s.hasNext()) setColor(geom,s.nextFloat(),s.nextFloat(),s.nextFloat(),s.nextFloat(),s.nextFloat(),s.nextFloat());
				v.add(geom);
				s.close();
			}
			
			b.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR INTRUDER ALERT! NO FILE FOUND!");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERROR INTRUDER ALERT! WRONG INPUT!");
			System.exit(1);
		}
		
		
		
		
		return v;
	}

	private static void setColor(AbstractGeometry geom, float c1x,
			float c1y, float c1z, float c2x,
			float c2y, float c2z) {
		
		geom.setColorFront(new Vector3f(c1x, c1y, c1z));
		geom.setColorBack(new Vector3f(c2x, c2y, c2z));
	}

	private static AbstractGeometry createModel(String type, float x, float y, float z,
			float rx, float ry, float rz, float size) {
		AbstractGeometry geom = null;
		if(type.equals("p")) {
			geom = new Pyramid();
		} else if (type.equals("c")) {
			geom = new Cube();
		} else if (type.equals("cy")) {
			geom = new Cylinder();
		} else {
			return null;
		}
		geom.setPosition(x, y, z);
		geom.setRotation(rx, ry, rz);
		geom.setSize(size);
		return geom;
	}
}
