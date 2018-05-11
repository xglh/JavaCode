package jmeterClass;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//csv文件类
public class CsvBuilder {
	
	
	private File csvFile;
	private FileWriter fw;
	private FileReader fr;
	
	private String csvFilePath;
	
	public CsvBuilder(String csvFilePath){		
		
		this.csvFilePath = csvFilePath;
		//判断文件路径是否存在，不存在则创建
		String csvFileDirStr = csvFilePath.substring(0, csvFilePath.lastIndexOf("\\"));
		File csvFileDir = new File(csvFileDirStr);
		if(!csvFileDir.exists()){
			csvFileDir.mkdir();
		}
		
		//判断文件是否存在，不存在则创建
		csvFile = new File(csvFilePath);
		if(!csvFile.exists()){
		try {
			csvFile.createNewFile();
			
		} catch (IOException e) {

		e.printStackTrace();
				
			}
		}		
	
	}
	
	
	/**重写write方法，isAppend默认参数为true，默认使用追加模式
	 * @param s：
	 * @throws IOException
	 */
	public void write(Object s) throws IOException{
		this.write(s, true);
	}	
	
	/**写入一行文本到csv文件
	 * @param s：要写入的文本
	 * @param isAppend：是否追加，true为追加文本，false为覆盖文本
	 * @throws IOException
	 */
	public void write(Object s,boolean isAppend) throws IOException{	
		fr=new FileReader(csvFilePath);
		fw = new FileWriter(csvFilePath,true);
		
		if(!(fr.read()==-1)){
			fw.write("\n");
		}	
		
		fw.write(s.toString());		
		fw.close();
	}
	
	//清除文本内容
	public void clear(){
		try {
			fw = new FileWriter(csvFilePath);
			fw.write("");
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
