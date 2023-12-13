import jeasy.analysis.MMAnalyzer;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;

/**
* ���ķִ���
*/
public class ChineseSpliter 
{
	/**
	* �Ը������ı��������ķִ�
	* @param text �������ı�
	* @param splitToken ���ڷָ�ı��,��"|"
	* @return �ִ���ϵ��ı�
	*/
	public static String split2(String text,String splitToken)
	{
		String result = null;
		MMAnalyzer analyzer = new MMAnalyzer();  
		try  	
        {		
			result = analyzer.segment(text, splitToken);	
		}  	
        catch (IOException e)  	
        { 	////
			System.out.println("02����");
        	e.printStackTrace(); 	
        } 	
        return result;
	}
	
	public static String split(String text, String splitToken) 
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			byte[] bt = text.getBytes();
			InputStream ip = new ByteArrayInputStream(bt);
			Reader read = new InputStreamReader(ip);
			IKSegmenter iks = new IKSegmenter(read, true);
			Lexeme t;
			while ((t = iks.next()) != null)
			{
				sb.append(t.getLexemeText() + " ");
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
}
