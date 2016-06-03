package gov.sc.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Time
{
	public static String convert(String date)
	{
		if(isContainAlphabet(date))
			return convertEngDate(date);
		
		return convertChnDate(date);
	}
	
	public static int compareTo(String d1, String d2)
	{
		return d1.compareTo(d2);
	}
	
	//�ж��ַ����Ƿ���	���������֣�0-9
	private static boolean isContainArbNum(String str)
	{
		
        return Pattern.compile("[0-9]").matcher(str).find()? true:false;
	}
	
	//�ж��ַ����Ƿ���	��������	����...
	private static boolean isContainChnNum(String str)
	{
		
        return Pattern.compile("[һ�����������߰˾�ʮ����Ҽ��������½��ƾ�ʰ]").matcher(str).find()? true:false;
	}
	
	//�ж��ַ����Ƿ���	��ĸ		��a-z/A-Z(��ȥam pm)
	private static boolean isContainAlphabet(String str)
	{
		
        return Pattern.compile("[b-lB-L]|[no]|[NO]|[q-z]|[Q-Z]").matcher(str).find()? true:false;
	}
	
	//�ж��ַ����Ƿ���	�ؼ��ַ�	��������...,:/-...
	private static boolean isContainKeyword(String str)
	{
			
	    return Pattern.compile("[�����պŵ�ʱ�������賿����������,:��/-]").matcher(str).find()? true:false;
	}
	
	//�ж��ַ��Ƿ�� Ӣ�����ڡ�
	private static boolean isContainWeek(String str)
	{
		
	    return Pattern.compile("(monday|mon.|mon|tuesday|tue.|tue|tue|tues.|tues|"
	    		+ "wednesday|wed.|wed|thursday|thur.|thurs.|thur|thurs|friday|fri.|fri"
	    		+ "saturday|sat.|sat|sunday|sun.|sun)").matcher(str).find()? true:false;
	}
	
	private static boolean isContainAmOrPm(String str)
	{
		
	    return Pattern.compile("(am|a.m.|am.|a.m|"
	    		+ "pm|p.m.|pm.|p.m)").matcher(str).find()? true:false;
	}
	
	private static boolean judgePot(String date, int i)
	{		
		return date.charAt(i)=='.' && isContainArbNum(String.valueOf(date.charAt(i-1)))?false:true;
	}
	
	private static int convertAmOrPm(String str)
	{
		HashMap<String, Integer>numMap = new HashMap<String, Integer>();
		numMap.put("am", 0);		numMap.put("a.m.", 0);
		numMap.put("am.", 0);		numMap.put("a.m", 0);
		numMap.put("pm", 12);		numMap.put("p.m.", 12);
		numMap.put("pm.", 12);		numMap.put("p.m", 12);
		
		return numMap.get(str);
	}
	
	//��������ת��Ϊ����������		�� ����һ��  - 2016
	private static String chnNumToArbNum(String hanzi)
	{
		HashMap<String, Integer>numMap = new HashMap<String, Integer>();
		numMap.put("��", 0);		numMap.put("��", 0);
		numMap.put("һ", 1);		numMap.put("Ҽ", 1);
		numMap.put("��", 2);		numMap.put("��", 2);
		numMap.put("��", 3);		numMap.put("��", 3);
		numMap.put("��", 4);		numMap.put("��", 4);
		numMap.put("��", 5);		numMap.put("��", 5);
		numMap.put("��", 6);		numMap.put("½", 6);
		numMap.put("��", 7);		numMap.put("��", 7);
		numMap.put("��", 8);		numMap.put("��", 8);
		numMap.put("��", 9);		numMap.put("��", 9);
		numMap.put("ʮ", 10);	numMap.put("ʰ", 10);
		
		int res = 0;
		boolean flag = true;
		for(int i=0; i<hanzi.length(); i++)
		{
			int tmp = numMap.get(String.valueOf(hanzi.charAt(i)));
			
			if(flag)
			{
				res = res * 10;
				if(tmp != 10 || i == 0)
					res += tmp;
			}
			else
			{
				flag = true;
				res += tmp;
			}
			
			if(tmp == 10)
				flag = false;
		}
		
		return String.valueOf(res);
	}	
	
	//Ӣ���·ݻ���ת��Ϊ��Ӧ����������
	public static String engToArbNum(String date)
	{
		HashMap<String, Integer>numMap = new HashMap<String, Integer>();
		numMap.put("jan.",1);		numMap.put("may",5);		numMap.put("sept.",9);
		numMap.put("feb.",2);		numMap.put("jun.",6);		numMap.put("oct.",10);
		numMap.put("mar.",3);		numMap.put("jul.",7);		numMap.put("nov.",11);
		numMap.put("apr.",4);		numMap.put("aug.",8);		numMap.put("dec.",12);
		
		numMap.put("jan.",1);		numMap.put("may",5);		numMap.put("sept.",9);
		numMap.put("feb.",2);		numMap.put("jun.",6);		numMap.put("oct.",10);
		numMap.put("mar.",3);		numMap.put("jul.",7);		numMap.put("nov.",11);
		numMap.put("apr.",4);		numMap.put("aug.",8);		numMap.put("dec.",12);		
		
		numMap.put("january",1);								numMap.put("september",9);
		numMap.put("february",2);	numMap.put("june",6);		numMap.put("october",10);
		numMap.put("march",3);		numMap.put("july",7);		numMap.put("november",11);
		numMap.put("april",4);		numMap.put("august",8);		numMap.put("december",12);
		
		numMap.put("jy.",7);		numMap.put("sep.",9);
		
		numMap.put("1st", 1);		numMap.put("11st", 11);		numMap.put("21st", 21);
		numMap.put("2nd", 2);		numMap.put("12nd", 12);		numMap.put("22nd", 22);
		numMap.put("3rd", 3);		numMap.put("13rd", 13);		numMap.put("23rd", 23);
		numMap.put("4th", 4);		numMap.put("14th", 14);		numMap.put("24th", 24);
		numMap.put("5th", 5);		numMap.put("15th", 15);		numMap.put("25th", 25);
		numMap.put("6th", 6);		numMap.put("16th", 16);		numMap.put("26th", 26);
		numMap.put("7th", 7);		numMap.put("17th", 17);		numMap.put("27th", 27);
		numMap.put("8th", 8);		numMap.put("18th", 18);		numMap.put("28th", 28);
		numMap.put("9th", 9);		numMap.put("19th", 19);		numMap.put("29th", 29);
		numMap.put("10th", 10);		numMap.put("20th", 20);		numMap.put("30th", 30);
		numMap.put("31st", 31);	
		
		return String.valueOf(numMap.get(date));
	}

	//��Ӣ�����ڣ���ʽ��Ӣʽ��ת��Ϊ��׼ 
	public static String convertEngDate(String date)
	{
		String[] setDate = new String[]{"2000", "01", "01", "00", "00", "00"};
		String month = "01";
		String day = "01";
		int apm = 0;
		try{
			for(int i=0,index=0; i<date.length(); i++)
			{
				while(date.charAt(i) == ' '  || isContainKeyword(String.valueOf(date.charAt(i))))
					i++;
					
				int begin;
				for(begin=i; i<date.length() && date.charAt(i)!=' ' &&
						!isContainKeyword(String.valueOf(date.charAt(i))); i++);
					
				String res = date.substring(begin, i);
				if(!isContainAmOrPm(res.toLowerCase()))
				{
					if(!isContainWeek(res.toLowerCase()))
					{
						if(isContainAlphabet(res))
							if(isContainArbNum(res))
								res = day = engToArbNum(res.toLowerCase());
							else
								res = month = engToArbNum(res.toLowerCase());
							else if(index < 2)
								day = res;
							
						
						res = String.valueOf(Integer.valueOf(res));
						
						if(Integer.valueOf(res) < 10)
							setDate[index++] = "0"+res;
						else
							setDate[index++] = res;
					}
				}
				else
					apm = convertAmOrPm(res.toLowerCase());
			}
			
			if(apm == 12)
			{
				int hour = Integer.valueOf(setDate[3])+apm;
				if(hour >= 24)
					setDate[3] = "00";
				else
					setDate[3] = String.valueOf(hour);
			}
			
			if(Integer.valueOf(day) < 10)
				day = "0"+day;
			if(Integer.valueOf(month) < 10)
				month = "0"+month;
		} catch(Exception e)
		{
			System.out.println("��������ȷ��ʽ");
			System.exit(0);
		}
		if(setDate[2].length()!=4 || month.length()!=2 && day.length()!=2)
		{
			System.out.println("��������ȷ��ʽ");
			System.exit(0);
		}
		
		return setDate[2]+"/"+month+"/"+day+" "+setDate[3]+":"+setDate[4]+":"+setDate[5];
	}
	
	//ת�������������֡������ġ����ĺͰ��������ֻ��Ϊ��׼��ʽ
	public static String convertChnDate(String date)
	{
		String[] setDate = new String[]{"2000", "01", "01", "00", "00", "00"};
		int apm = 0;
		try
		{
			for(int i=0,index=0; i<date.length(); i++)
			{
				while(date.charAt(i) == ' '  || isContainKeyword(String.valueOf(date.charAt(i))))
					i++;
					
				int begin;
				for(begin=i; i<date.length() && date.charAt(i)!=' ' && judgePot(date, i)
					&& !isContainKeyword(String.valueOf(date.charAt(i))); i++);
				
				String res = date.substring(begin, i);
				if(res.equals(""))	continue;
				if(!isContainAmOrPm(res.toLowerCase()))
				{
						//�ж�res�Ƿ����������֡�
					if(isContainChnNum(res))
						res = chnNumToArbNum(res);
					else
						res = String.valueOf(Integer.valueOf(res));
						
					if(Integer.valueOf(res) < 10)
						setDate[index++] = "0"+res;
					else
						setDate[index++] = res;
				}
				else
					apm = convertAmOrPm(res.toLowerCase());
			}
				
			if(setDate[0].length() < 4)
			{
				String tmp = setDate[0];
				setDate[0] = setDate[2];
				setDate[2] = tmp;
			}
			
			if(apm == 12)
			{
				int hour = Integer.valueOf(setDate[3])+apm;
				if(hour >= 24)
					setDate[3] = "00";
				else
					setDate[3] = String.valueOf(hour);
			}
		} catch(Exception e)
		{
			System.out.println("��������ȷ��ʽ");
			System.exit(0);
		}
		if(setDate[0].length()!=4 || setDate[1].length()!=2 && setDate[2].length()!=2)
		{
			System.out.println("��������ȷ��ʽ");
			System.exit(0);
		}
		
		return setDate[0]+"/"+setDate[1]+"/"+setDate[2]+" "+setDate[3]+":"+setDate[4]+":"+setDate[5];
	}
}
