import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* ѵ����������
*/

public class TrainingDataManager 
{
	private String[] traningFileClassifications;//ѵ�����Ϸ��༯��
	private File traningTextDir;//ѵ�����ϴ��Ŀ¼
	//private static String defaultPath = "G:\\WorkSpace\\beyesi\\yuliao\\SogouC\\Sample";
	//  private static String defaultPath = "D:\\workspace\\beyesi\\yuliao\\SogouC";
	//private static String defaultPath = "C:\\beiyesi\\Sample";
	//private static String defaultPath = "..\\trs2txt\\nf1012_test\\1\\sample";
	private static String defaultPath = ".\\sample";
	
	public TrainingDataManager() 
	{
		traningTextDir = new File(defaultPath);
		if (!traningTextDir.isDirectory()) 
		{
			throw new IllegalArgumentException("ѵ�����Ͽ�����ʧ�ܣ� [" +defaultPath + "]");
		}
		this.traningFileClassifications = traningTextDir.list();
	}
	/**
	* ����ѵ���ı�������������Ŀ¼��
	* @return ѵ���ı����
	*/
	public String[] getTraningClassifications() 
	{
		return this.traningFileClassifications;
	}
	/**
	* ����ѵ���ı���𷵻��������µ�����ѵ���ı�·����full path��
	* @param classification �����ķ���
	* @return ���������������ļ���·����full path��
	*/
	public String[] getFilesPath(String classification) 
	{
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		String[] ret = classDir.list();
		for (int i = 0; i < ret.length; i++) 
		{
			ret[i] = traningTextDir.getPath() +File.separator +classification +File.separator +ret[i];
		}
		return ret;
	}
	/**
	 * ���ض��������������
	 */
	public String[] get2ClassificationsName(String classification){
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		String[] ret = classDir.list();
		return ret;
	}
	
	/**
	 * ���ض��������ı�·��
	 */
	public String[] get2FilesPath(String classification,String classification2) 
	{
		File classDir = new File(traningTextDir.getPath() +File.separator +classification+File.separator +classification2);
		String[] ret = classDir.list();
		for (int i = 0; i < ret.length; i++) 
		{
			ret[i] = traningTextDir.getPath() +File.separator +classification +File.separator +classification2+File.separator +ret[i];
		}
		return ret;
	}
	

	/**
	* ���ظ���·�����ı��ļ�����
	* @param filePath �������ı��ļ�·��
	* @return �ı�����
	* @throws FileNotFoundException
	* @throws IOException
	*/
	public static String getText(String filePath) throws FileNotFoundException,IOException 
	{
	
		InputStreamReader isReader =new InputStreamReader(new FileInputStream(filePath),"utf-8");
		BufferedReader reader = new BufferedReader(isReader);
		String aline;
		StringBuilder sb = new StringBuilder();
		int t=0;
		while ((aline = reader.readLine()) != null)
		{   
			t++;
			//if (t>1){
			sb.append(aline + " ");
			//}
		}
		isReader.close();
		reader.close();
		return sb.toString();
	}

	/**
	* ����ѵ���ı��������е��ı���Ŀ
	* @return ѵ���ı��������е��ı���Ŀ
	*/
	public int getTrainingFileCount()
	{
		int ret = 0;
		for (int i = 0; i < traningFileClassifications.length; i++)
		{
		//	ret +=getTrainingFileCountOfClassification(traningFileClassifications[i]);
			ret+=getTrainingFileCount2(traningFileClassifications[i]);
		}
		return ret;
	}
	/**
	 * ���ض���ѵ���ı��������е��ı���Ŀ
	 * @param classification
	 * @return
	 */
	public int getTrainingFileCount2(String classification)
	{
		int ret = 0;
		for (int i = 0; i < get2ClassificationsName(classification).length; i++)
		{
			ret +=getTrainingFileCountOfClassification2(classification,get2ClassificationsName(classification)[i]);
		}
		return ret;
	}

	/**
	* ����ѵ���ı������ڸ��������µ�ѵ���ı���Ŀ
	* @param classification �����ķ���
	* @return ѵ���ı������ڸ��������µ�ѵ���ı���Ŀ
	*/
	public int getTrainingFileCountOfClassification(String classification)
	{
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		return classDir.list().length;
	}
	/**
	 * ���ض���ѵ���ı������ڸ��������µ�ѵ���ı���Ŀ
	 * @param classification
	 * @param classification2
	 * @return
	 */
	public int getTrainingFileCountOfClassification2(String classification,String classification2)
	{
		//File classDir = new File(traningTextDir.getPath() +File.separator +classification+File.separator +classification2);
		File classDir = new File(traningTextDir.getPath() +File.separator +classification+File.separator +classification2);
		return classDir.list().length;
	}

	/**
	* ���ظ��������а����ؼ��֣��ʵ�ѵ���ı�����Ŀ
	* @param classification �����ķ���
	* @param key �����Ĺؼ��֣���
	* @return ���������а����ؼ��֣��ʵ�ѵ���ı�����Ŀ
	*/
	public int getCountContainKeyOfClassification(String classification,String key) 
	{
		int ret = 0;
		/*
		try 
		{
			String[] filePath = getFilesPath(classification);
			for (int j = 0; j < filePath.length; j++) 
			{
				String text = getText(filePath[j]);
				if (text.contains(key)) 
				{
					ret++;
				}
			}
		}
		catch (FileNotFoundException ex) 
		{
			Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null,ex);
	
		} 
		catch (IOException ex)
		{
			Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null,ex);
	
		}*/
		for(int i=0;i<get2ClassificationsName(classification).length;i++){
			ret+=getCountContainKeyOfClassification2(classification,get2ClassificationsName(classification)[i],key);
		}
		return ret;
	}
	/**
	 * ���ض���������������а����ؼ��֣��ʵ�ѵ���ı�����Ŀ
	 * @param classification1
	 * @param classification2
	 * @param key
	 * @return
	 */
	public int getCountContainKeyOfClassification2(String classification1,String classification2,String key) 
	{
		int ret = 0;
		try 
		{
			String[] filePath = get2FilesPath(classification1,classification2);
			for (int j = 0; j < filePath.length; j++) 
			{
				String text = getText(filePath[j]);
				if (text.contains(key)) 
				{
					ret++;
				}
			}
		}
		catch (FileNotFoundException ex) 
		{
			Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null,ex);
	
		} 
		catch (IOException ex)
		{
			Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null,ex);
	
		}
		return ret;
	}
}