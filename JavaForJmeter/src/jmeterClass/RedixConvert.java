package jmeterClass;

//进制转换类
public class RedixConvert {
	
	public int sourceRedix;
	public int targetRedix;
	public Object objToConvert;
	public String strToConvert;
		
	public RedixConvert( Object objToConvert,int sourceRedix, int targetRedix) {
		
		this.objToConvert = objToConvert;
		this.sourceRedix = sourceRedix;
		this.targetRedix = targetRedix;		
		this.strToConvert = objToConvert.toString();
	}
	
	public String convert(){
		//先全部转换为10进制
		int temp10 = Integer.valueOf(strToConvert, sourceRedix);
		//System.out.println("temp10="+temp10);
		String result = "";
			
		switch(targetRedix){
			case 2:
				result = Integer.toBinaryString(temp10); 
				break;
			case 8:
				result = Integer.toOctalString(temp10);
				break;
			case 10:
				result = temp10+"";
				break;
			case 16:
				result = Integer.toHexString(temp10);
				break;
			
		}
		return result;
	}	

}
