package gov.sc.filter;

import java.util.ArrayList;
import java.util.List;

public class Canopy {
	private List<Point> points = new ArrayList<Point>(); // ���о���ĵ�
	private List<List<Point>> clusters = new ArrayList<List<Point>>(); // �洢��
	private double T2 = -1; // ��ֵ

	public Canopy(List<Point> points) {
		for (Point point : points)
			this.points.add(point);
	}

	public void cluster() {
		T2 = getAverageDistance(points);
		while (points.size() != 0) {
			List<Point> cluster = new ArrayList<Point>();
			Point basePoint = points.get(0); // ��׼��
			cluster.add(basePoint);
			points.remove(0);
			int index = 0;
			while (index < points.size()) {
				Point anotherPoint = points.get(index);
				double distance = getDistance(basePoint, anotherPoint);
				if (distance <= T2) {
					cluster.add(anotherPoint);
					points.remove(index);
				} else {
					index++;
				}
			}
			clusters.add(cluster);
		}
	}

	/**
	 * �õ�Cluster����Ŀ
	 * 
	 * @return ��Ŀ
	 */
	public int getClusterNumber() {
		return clusters.size();
	}

	/**
	 * ����������֮��ľ���
	 */
	public double getDistance(Point A, Point B) {
		return (WawaKMeans.getDistance(A.data, B.data));
	}

	public double getAverageDistance(List<Point> points) {
		double sum = 0;
		int pointSize = points.size();
		for (int i = 0; i < pointSize; i++) {
			for (int j = i + 1; j < pointSize; j++) {
				Point pointA = points.get(i);
				Point pointB = points.get(j);
				sum += getDistance(pointA, pointB);
			}
		}
		int distanceNumber = pointSize * (pointSize - 1) / 2;
		double T2 = sum / distanceNumber; // ƽ�����
		return T2;
	}

	private double[] getCenterPoint(List<Point> points) {
		double[] data = new double[points.get(0).data.length];
		for (Point point : points) {
			for (int i = 0; i < point.data.length; i++) {
				data[i] += point.data[i];
			}
		}
		for (int i = 0; i < points.get(0).data.length; i++) {
			data[i] = data[i] / points.size();
		}
		return data;
	}

	public List<Point> getClusterCenterPoints() {
		List<Point> centerPoints = new ArrayList<Point>();
		for (List<Point> cluster : clusters) {
			centerPoints.add(new Point(getCenterPoint(cluster)));
		}
		return centerPoints;
	}

	public double getThreshold() {
		return T2;
	}
}
