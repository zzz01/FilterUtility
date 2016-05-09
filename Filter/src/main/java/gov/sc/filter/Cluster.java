package gov.sc.filter;

import gov.sc.model.TFIDFMeasure;
import gov.sc.tokeniser.Tokeniser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cluster {
	private List<List<String>> clusterList;// ���������Ľ��
	private List<Integer> countList;// ʱ������ͳ����������Ľ��

	public List<List<String>> getResultOfClusterAndSort(
			LinkedHashMap<Integer, List<String>> map, int k) throws IOException {

		clusterList = new ArrayList<List<String>>(map.size());

		if (map.size() < 1) {
			System.out.println("û���ĵ�����");
			System.in.read();
			return null;
		}
		String[] docs = new String[map.size()];
		int m = 0;
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) it
					.next();
			List<String> val = (List<String>) entry.getValue();
			docs[m++] = val.get(k - 1);
		}

		TFIDFMeasure tf = new TFIDFMeasure(docs, new Tokeniser());
		double[][] data = new double[docs.length][];
		int docCount = docs.length; // �ĵ�����
		for (int i = 0; i < docCount; i++) {
			data[i] = tf.GetTermVector2(i);
		}
		WawaKMeans kmeans = new WawaKMeans(data);
		kmeans.Start();

		WawaCluster[] clusters = kmeans.getClusters();
		sortNewsContent(clusters);
		countList = new ArrayList<Integer>();
		for (int i = 0; i < clusters.length; i++) {
			int count = 0;
			List<Integer> members = clusters[i].CurrentMembership;
			for (int j : members) {
				clusterList.add(map.get(j));
				++count;
			}
			countList.add(count);
		}
		return clusterList;
	}

	// / <summary>
	// / ����Ľӿڣ��õ������緢�����Ų��Ҽ���������ֵ����ΪList<List<String>>
	// / </summary>
	// / <param name="k">k��ʾʱ�����ڵ���ֵ</param>
	// / <returns></returns>
	public List<List<String>> getResultOfCountAndTime(int k) {
		List<List<String>> resultList = new ArrayList<List<String>>(
				countList.size());
		List<String> temp;
		// ����������ݽṹclusterList�Լ�countList������緢�����Ų��Ҽ���������resultList
		for (int i = 0; i < countList.size(); i++) {
			String min = "99999999999999";
			int row = 0;// ��ס��Сʱ���Ӧ��clusterList���к�
			for (int j = 0; j < countList.get(i); j++) {
				String curStr = transformTime(clusterList.get(j).get(k - 1));
				if (curStr.compareTo(min) < 0) {
					min = curStr;// �õ������ʱ��
					row = j;// �ı��¼ֵ
				}
			}

			temp = clusterList.get(row);// ��clusterList��Ӧ�к����ڵ�List<String>��temp
			temp.add(String.valueOf(countList.get(i)));// �����������
			resultList.add(temp);// д�뵽resultList

			// ɾ���ÿ���������clusterList�е���
			for (int j = 1; j <= countList.get(i); j++)
				// ��ʾ�ظ���countList.get(i)��
				clusterList.remove(0);
		}
		return resultList;
	}

	// / <summary>
	// / ���ڸ�ʽ��ת��
	// / </summary>
	// / <param name="time">ʱ��</param>
	// / <returns></returns>
	private String transformTime(String time) {
		String result = null;
		char[] ch = new char[time.length()];
		int k = 0;
		time = time.trim();
		for (int i = 0; i < time.length(); i++) {
			char c = time.charAt(i);
			if (c != '-' || c != ' ' || c != ':' || c != '/') {
				ch[k++] = c;
			}
		}
		result = String.valueOf(ch);
		return result;
	}

	// / <summary>
	// / �Ծ��������ݵ���������ɸߵ��͵�����
	// / </summary>
	// / <param name="clusters"></param>
	// / <returns></returns>
	private void sortNewsContent(WawaCluster[] clusters) {
		int i, j;
		WawaCluster temp;
		int n = clusters.length;
		for (i = 0; i < n - 1; i++) {
			for (j = 0; j < n - i - 1; j++) {
				if (clusters[j].CurrentMembership.size() < clusters[j + 1].CurrentMembership
						.size()) {
					temp = clusters[j];
					clusters[j] = clusters[j + 1];
					clusters[j + 1] = temp;
				}
			}
		}
	}
}
