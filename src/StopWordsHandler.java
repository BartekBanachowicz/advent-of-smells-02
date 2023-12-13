import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
* ͣ�ôʴ�����
* @author phinecos 
* 
*/
public class StopWordsHandler 
{	
	ArrayList<String> lss=new ArrayList<String>();
//	private static String stopWordsList[];
//	private static String stopWordsList[] ={"��", "����","Ҫ","�Լ�","֮","��","��","��","��","��","��","��","Ӧ","��","ĳ","��","��","��","λ","��","һ","��","��","��","��","��","��","��",""};//����ͣ�ô�
	public  boolean IsStopWord(String word)
	{/*	StopWordsHandler s=new StopWordsHandler();
		for(int i=0;i<s.stopWordsList.length;++i)
		{
			if(word.equalsIgnoreCase(s.stopWordsList[i]))
				return true;
		}
		return false;*/
		for(int i=0;i<lss.size();++i)
		{
			if(word.equalsIgnoreCase(lss.get(i)))
				return true;
		}
		return false;
	}
	public StopWordsHandler(String path){
		try{
		FileInputStream fi=new FileInputStream(path);
		InputStreamReader isReader=new InputStreamReader(fi,"GBK");
	//	FileReader isReader =new FileReader("E:\\workspace\\beyesi\\ͣ�ôʱ�.txt");
	//	System.out.println("ʧ�ܣ�");
		BufferedReader reader = new BufferedReader(isReader);
		int i=0;
		String aline;
		//		for(int i=0;reader.readLine() != null;i++){
		while ((aline = reader.readLine()) != null){
			lss.add(aline);
	//		stopWordsList[i]=aline;
	//		System.out.println(aline);
			i++;
		}

		}catch(Exception ex){
			System.out.println("��ȡͣ�ôʱ��ı�ʱʧ�ܣ�");
		}
	//	String stopWordsList[] = null;
	//	for(int j=0;j<lss.size();j++){
	//		System.out.println(lss.get(j));
	//		stopWordsList[j]=lss.get(j);
	//	}
	}

	
}
