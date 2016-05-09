package gov.sc.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import gov.sc.tokeniser.*;

public class TFIDFMeasure {

	
		private String[] _docs;
		private String[][] _ngramDoc;
		private int _numDocs=0;
		private int _numTerms=0;
		private ArrayList _terms;
		private int[][] _termFreq;
		private float[][] _termWeight;//���յõ�����һ����ά���飬�б�ʾ���ʣ��б�ʾ�ĵ���
		                             //ÿ��Ԫ�ص�ֵ��tf*idf,tf=��������Ӧ�ĵ��г��ֵĴ���idf=log(���ĵ���/���ָôʵ��ĵ���)
		private int[] _maxTermFreq;
		private int[] _docFreq;

        ITokeniser _tokenizer = null;


	    private Dictionary _wordsIndex=new Hashtable() ;

		public TFIDFMeasure(String[] documents,ITokeniser tokeniser) throws IOException
		{
			//System.out.println("TFIDFMeasure()");
			_docs=documents;
			_numDocs=documents.length ;
		    _tokenizer = tokeniser;
		   // System.out.println("start Init()");
			myInit();
		}

	    

	    private void GeneratNgramText()
		{
			
		}

		private ArrayList GenerateTerms(String[] docs) throws IOException
		{
			HashMap<String, Integer> map=new HashMap<String,Integer>();
			ArrayList uniques=new ArrayList() ;
			_ngramDoc=new String[_numDocs][] ;
			for (int i=0; i < docs.length ; i++)
			{
				List<String> words=_tokenizer.partition(docs[i]);	
//                //��ά���?ͳ�����д��������>1���򱣴浽ArrayList��			
//				for(int j=0;j<words.size();j++){//����hashmap
//					if(map.containsKey(words.get(j))){
//						map.put(words.get(j), map.get(words.get(j))+1);
//					}
//					else {
//						map.put(words.get(j), 1);
//					}
//				}
				
				for (int j=0; j < words.size(); j++)
					if (!uniques.contains(words.get(j)))				
						uniques.add(words.get(j)) ;
								
			}
			
			//����hashmap���ҽ���Arraylist
//			Iterator it=map.entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry<String, Integer> entry=(Map.Entry<String, Integer>)it.next();
//				String key=(String) entry.getKey();
//				int val=(Integer)entry.getValue();
//				if(val>1){
//					uniques.add(key);
//				}
//			}
			return uniques;
		}		

		private static Object AddElement(Dictionary collection, Object key, Object newValue)
		{
			Object element=collection.get(key);
			collection.put(key, newValue);
			return element;
		}

		private int GetTermIndex(String term)
		{
			Object index=_wordsIndex.get(term);
			if (index == null) return -1;
			return (Integer)index;
		}

		private void myInit() throws IOException
		{
			_terms=GenerateTerms (_docs );
			_numTerms=_terms.size() ;

			_maxTermFreq=new int[_numDocs] ;
			_docFreq=new int[_numTerms] ;
			_termFreq =new int[_numTerms][] ;
			_termWeight=new float[_numTerms][] ;

			for(int i=0; i < _terms.size() ; i++)			
			{
				_termWeight[i]=new float[_numDocs] ;
				_termFreq[i]=new int[_numDocs] ;

				AddElement(_wordsIndex, _terms.get(i), i);			
			}
			
			GenerateTermFrequency();
			GenerateTermWeight();			
				
		}
		
		private float Log(float num)
		{
			return (float) Math.log(num) ;//log2
		}

		private void GenerateTermFrequency() throws IOException
		{
			for(int i=0; i < _numDocs  ; i++)
			{								
				String curDoc=_docs[i];
				Dictionary freq=GetWordFrequency(curDoc);
				Enumeration enums=freq.keys();
				_maxTermFreq[i]=Integer.MIN_VALUE;
				while(enums.hasMoreElements()){
					String word=(String) enums.nextElement();
					int wordFreq=(Integer)freq.get(word);
					int termIndex=GetTermIndex(word);
                    if(termIndex == -1)
                        continue;
					_termFreq [termIndex][i]=wordFreq;
					_docFreq[termIndex] ++;

					if (wordFreq > _maxTermFreq[i]) _maxTermFreq[i]=wordFreq;	
				}
			}
		}
		

		private void GenerateTermWeight()
		{			
			for(int i=0; i < _numTerms; i++)
			{
				for(int j=0; j < _numDocs; j++)				
					_termWeight[i][j]=ComputeTermWeight (i, j);				
			}
		}

		private float GetTermFrequency(int term, int doc)
		{			
			int freq=_termFreq [term][doc];
			int maxfreq=_maxTermFreq[doc];			
			
			return ( (float) freq/(float)maxfreq );
		}

		private float GetInverseDocumentFrequency(int term)
		{
			int df=_docFreq[term];
			return Log((float) (_numDocs) / (float) df );
		}

		private float ComputeTermWeight(int term, int doc)
		{
			float tf=GetTermFrequency (term, doc);
			float idf=GetInverseDocumentFrequency(term);
			return tf * idf;
		}
		
		private  float[] GetTermVector(int doc)
		{
			float[] w=new float[_numTerms] ; 
			for (int i=0; i < _numTerms; i++)											
				w[i]=_termWeight[i][doc];	
			return w;
		}
        public double [] GetTermVector2(int doc)
        {
            double [] ret = new double[_numTerms];
            float[] w = GetTermVector(doc);
            for (int i = 0; i < ret.length; i++ )
            {
                ret[i] = w[i];
            }
            return ret;
        }

		public double GetSimilarity(int doc_i, int doc_j)
		{
			double [] vector1=GetTermVector2 (doc_i);
			double [] vector2=GetTermVector2 (doc_j);

			return TermVector.ComputeCosineSimilarity(vector1, vector2) ;

		}
		
		private Dictionary GetWordFrequency(String input) throws IOException
		{
			String convertedInput=input.toLowerCase() ;
					
            List<String> temp = new ArrayList<String>(_tokenizer.partition(convertedInput));
            String[] words=new String[temp.size()];
            temp.toArray(words);		
	        
			Arrays.sort(words);
			
			String[] distinctWords=GetDistinctWords(words);
						
			Dictionary result=new Hashtable();
			for (int i=0; i < distinctWords.length; i++)
			{
				Object tmp;
				tmp=CountWords(distinctWords[i], words);
				result.put(distinctWords[i], tmp);
				
			}
			
			return result;
		}				
				
		private static String[] GetDistinctWords(String[] input)
		{				
			if (input == null)			
				return new String[0];			
			else
			{
                List<String> list = new ArrayList<String>();
				
				for (int i=0; i < input.length; i++)
					if (!list.contains(input[i])) // N-GRAM SIMILARITY?				
						list.add(input[i]);
				String[] v=new String[list.size()];
				return (String[]) list.toArray(v);
			}
		}
		
		
		private int CountWords(String word, String[] words)
		{
			int itemIdx=Arrays.binarySearch(words, word);
			
			if (itemIdx > 0)			
				while (itemIdx > 0 && words[itemIdx].equals(word))				
					itemIdx--;				
						
			int count=0;
			while (itemIdx < words.length && itemIdx >= 0)
			{
				if (words[itemIdx].equals(word)) count++;				
				
				itemIdx++;
				if (itemIdx < words.length)				
					if (!words[itemIdx].equals(word)) break;					
				
			}
			
			return count;
		}

		public int get_numTerms() {
			return _numTerms;
		}

		public void set_numTerms(int terms) {
			_numTerms = terms;
		}				
}