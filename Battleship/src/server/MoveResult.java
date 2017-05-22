package server;

import utils.Point;
import utils.Enums.HitType;

public class MoveResult {
	HitType hitType;
	Point point;
	
	public MoveResult(HitType hitType, Point point) {
		super();
		this.hitType = hitType;
		this.point = point;
	}
	
	public HitType getHitType() {
		return hitType;
	}
	
	public void setHitType(HitType hitType) {
		this.hitType = hitType;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}
}
