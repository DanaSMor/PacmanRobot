package AutoAlgo;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import Gameboard.Blocks;
import Geom.Point3D;

public class CalcNeighbor {

	private Vertex source, target;
	private	Collection<Blocks> blockArr;
	private ArrayList<Vertex> points;
	private int id;
	private HashMap<String, Point3D> hash;
	private int height, width;

	public CalcNeighbor(Collection<Blocks> blockArr, Vertex source, Vertex target,int width, int height) {
		this.source = source;
		this.target = target;
		this.blockArr = blockArr;
		points = new ArrayList<Vertex>();
		hash = new HashMap<String, Point3D>();
		id = 1;
		this.height = height;
		this.width = width;
	}

	public ArrayList<Vertex> getSkeleton() {
		buildPointArray();
		addNeighbor();

		return points;
	}

	public Point3D getPoint(String key) {
		Point3D p = hash.get(key);
		return p;
	}

	private void buildPointArray() {
		points.add(source);
		hash.put(""+source.getName(), source.getPoint());
		for(Blocks b : blockArr) {
			Rectangle r = b.getRect(width,height);
			r.grow(2, 2);
			points.add(createVrtex(r.getMaxX(),r.getMaxY()));
			points.add(createVrtex(r.getMaxX(),r.getMinY()));
			points.add(createVrtex(r.getMinX(),r.getMinY()));
			points.add(createVrtex(r.getMinX(),r.getMaxY()));
		}
		target.setName(points.size());
		hash.put(""+points.size(), target.getPoint());
		points.add(target);
	}

	private Vertex createVrtex(double x,double y) {
		Point3D p = new Point3D(x,y);
		hash.put(""+id, p);
		return new Vertex(p,id++);
	}

	private boolean isNeighbor(Point3D p,Point3D p2) {
		boolean flag=true;
		for(Blocks b : blockArr) {
			Rectangle r = b.getRect(width,height);
			flag = r.intersectsLine(p.x(), p.y(), p2.x(),p2.y());
			if(flag) return false;
		}
		return true;
	}

	private void addNeighbor() {
		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(source);
		boolean wasInQueue[] = new boolean[points.size()];
		wasInQueue[source.getName()] = true;
		while(!queue.isEmpty()) {
			Vertex current = queue.poll();
			if(!current.equals(target)) {
				for(Vertex v : points) {
					if(v.getName()!=0 && !current.equals(v) && isNeighbor(current.getPoint(), v.getPoint())) {
						current.addNeighbor(""+v.getName());
						if(!wasInQueue[v.getName()]) {
							wasInQueue[v.getName()] = true;
							queue.add(v);
						}
					}
				}
			}
		}
	}

}
