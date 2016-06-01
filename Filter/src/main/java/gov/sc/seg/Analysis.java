package gov.sc.seg;

import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

public class Analysis {

	public Analysis() {
		loadStopWord();
	}

	private void loadStopWord() {

	}

	public String[] parse(String str) {
		List<Term> res = NlpAnalysis.parse(str);
		String[] words = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			words[i] = res.get(i).getName();
		}
		return words;
	}
	
	public static void main(String[] args) {
		Analysis ana = new Analysis();
		String str = "尊敬的律师。:本人原是一名普通警察，2011年个人受贿3.04万元";
		System.out.println(ana.parse(str));
	}
}
