package jmeterClass;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import org.apache.commons.codec.binary.Base64;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

public class JHelper{
	
	//JMeterContext对象，context上下文，等同于beanshell中的ctx
	private JMeterContext jmeterContext = JMeterContextService.getContext();
	
	//获取当前Thread的variable对象，等同于beanshell中的vars
	private JMeterVariables jmeterVariables = jmeterContext.getVariables();
	
	//获取当前请求的结果对象，等同于beanshell中的prev
	private SampleResult sampleResult = jmeterContext.getPreviousResult();
	
	//变量var，用于判断jmeter变量是否为空
	private Object var = null;	
	
	//初始化时变量标志
	private Object initVar = null;
	
	/***添加threadId变量标志线程,threadId = ip:线程编号.线程重复次数.当前时间戳,threadId_md5 = md5(threadId)
	 *  将threadId_md5写入jmeter threadId变量中,在jmeter中用${threadId}来引用
	 */
	private String threadId = "";
	private String threadId_md5 = "";
	
	public JHelper(){
		
		//threadRunTime变量标志thread重复次数
		initVar = jmeterVariables.getObject("threadRunTime");
    	//counterKey不存在才创建，可以重复执行，不会重置计数器
    	if(initVar == null){
    		this.setCount("threadRunTime");  
    		
    	}  
    	else{
    	//计数器存在则计数+1
    		this.count("threadRunTime");    		
    	}
    	
    	threadId = JHelper.getLocalHostIP() + ":"+ this.getCount("threadRunTime")+ "." + (jmeterContext.getThreadNum() + 1) + "."  
    			+ JHelper.getTime(0);
    	
    	threadId_md5 = JHelper.getMD5(threadId);
    	jmeterVariables.put("threadId_str", threadId);
		jmeterVariables.put("threadId", threadId_md5);
		jmeterVariables.put("threadNum", this.getThreadNum()+"");
		jmeterVariables.put("threadRunTime", this.getThreadRunTime()+"");
	}
	
	public String getThreadId(){		
		return threadId;
	}
	
	//获取线程编号
	public int getThreadNum(){		
		return jmeterContext.getThreadNum() + 1;
	}
	//获取单个线程执行次数
	public int getThreadRunTime(){		
		return this.getCount("threadRunTime");
	}
	
	public String getThreadId_md5(){		
		return threadId_md5;
	}
	
	//获取本机时间的unix时间戳
	public static int getCurrentTime(){
		int time = (int) (Calendar.getInstance().getTimeInMillis()/1000L);
		return time;
	}
	
	
	//按指定格式获取当前日期,yyyyMMddHHmmss 24小时格式
	public static String getCurrentTime(String Format){
		SimpleDateFormat format = new SimpleDateFormat(Format);
		String currentDateString = format.format(new Date());
		return currentDateString;
	}
	
	//将linux时间戳转为
	public static String stampToDate(int timeStamp,String format){
        String res;
        //window时间戳单位为毫秒
        String t = timeStamp+"000";        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(t);         
       
        res = simpleDateFormat.format(lt);
        return res;
    }
	
	//获取指定时间，参数为与本机时间相差的秒数,正数表示当前时间之前，负数为当前时间之后
	public static int getTime(int timeGap){
		return getCurrentTime() - timeGap;
		
	}	
	
	//10数字进制转化为16进制字符串
	public static String radixToHex(int num){
		return redixConvert(num,16);
		//return Integer.toHexString(num);
	}
	
	//重写getMD5方法，bit默认为32位
	public static String getMD5(String sourceStr){
		return getMD5(sourceStr,32);
	}
	
	//md5加密，16/32位
	public static String getMD5(String sourceStr,int bit) {
		String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            
            if(bit == 16){
            	result = buf.toString().substring(8, 24);
            }else{
            	result = buf.toString();
            }
            
           
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
	
	//base64编码
	public static String getBase64(String plainText){  
        byte[] b=plainText.getBytes();  
        Base64 base64=new Base64();  
        b=base64.encode(b);  
        String s=new String(b);  
        return s;  
    }  
      
   //base64解码
    public static String getFromBase64(String encodeStr){  
        byte[] b=encodeStr.getBytes();  
        Base64 base64=new Base64();  
        b=base64.decode(b);  
        String s=new String(b);  
        return s;  
    } 
    
   
    
    //生成[min,max)区间的随机序列,n为序列长度
    public static int[] randomSet(int min, int max, int n) {  
    	//随机序列
    	HashSet<Integer> set = new HashSet<Integer>();
    	
        if (n > (max - min + 1) || max < min) {  
            return null;  
        }  
       /* for (int i = 0; i < n; i++) {  
            // 调用Math.random()方法  ,random取[0,1)区间的值
            int num = (int) (Math.random() * (max - min)) + min;  
            set.add(num);// 将不同的数存入HashSet中  
        }  */
        int setSize = 0;  
        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小  
        
        while (setSize < n) {  
        	int num = (int) (Math.random() * (max - min)) + min;  
            set.add(num); // 将不同的数存入HashSet中
        	setSize = set.size();
        	}  
        
        
        Integer[] temp = set.toArray(new Integer[] {});//关键语句  
        
        //将Integer型数组转为int型数组  
        int[] intArray = new int[temp.length];  
        for (int i = 0; i < temp.length; i++) {  
            intArray[i] = temp[i].intValue();  
        }
        
        //按大小排序
        if(intArray.length > 1){
        	 for(int i = 0;i<intArray.length-1;i++){
             	int temp1 = 0;
             	for(int j = i+1;j<intArray.length;j++){
             		if(intArray[i] > intArray[j]){
             			temp1 = intArray[i];
             			intArray[i] = intArray[j];
             			intArray[j] = temp1;
             		}
             	}
             }
        }
       
		return intArray;  
        
    }  
    
    //重写redixConvert方法，默认sourceRadix为10进制
    public static String redixConvert(Object objToConvert,int targetRedix){
    	return redixConvert(objToConvert,10,targetRedix);
    }
	
    //进制转换
    public static String redixConvert(Object objToConvert,int sourceRadix,int targetRedix){
    	RedixConvert redix = new RedixConvert(objToConvert,sourceRadix,targetRedix);    	
    	return redix.convert();
    }
    
    //获取本机ip
    public static String getLocalHostIP(){    	    	
    	return JMeterUtils.getLocalHostIP();
    }
    
    //获取本机名称
    public static String getLocalHostName(){    	    	
    	return JMeterUtils.getLocalHostName();
    }
    
    //初始化计数器，默认初始值为1，步长为1
    public int setCount(String counterKey){
    	int flag = setCount(counterKey,1,1);
    	return flag;
    }    
    
    //初始化计数器
    public int setCount(String counterKey,int counterBegin,int counterStep){
    	//flag标记，0为新创建，1为已存在，不需要创建
    	int flag = 1;
    	var = jmeterVariables.getObject(counterKey);
    	//counterKey不存在才创建，可以重复执行，不会重置计数器
    	if(var == null){
    		Counter counter = new Counter(counterBegin,counterStep);       	
        	jmeterVariables.putObject(counterKey, counter);
        	flag = 0;
    	}  
    	
    	return flag;
    	
    }
    
    //获取当前计数
    public int getCount(String counterKey){
    	Counter counter = (Counter)jmeterVariables.getObject(counterKey);
    	return counter.currentNum;
    }
    
    //获取前一步的计数
    public int getPreviousCount(String counterKey){
    	Counter counter = (Counter)jmeterVariables.getObject(counterKey);
    	return counter.previousNum;
    }
    
    //执行一次计数   
    public int count(String counterKey){
    	//-1：参数不存在，0：计数成功
    	int flag = -1;
    	var = jmeterVariables.getObject(counterKey);
    	if(!(var == null)){
    		Counter counter = (Counter)jmeterVariables.getObject(counterKey);
        	counter.count();
        	jmeterVariables.putObject(counterKey, counter);
        	flag = 0;
    	}
    	
    	return flag;
    }
    
    //删除计数器
    public void removeCounter(String counterKey){
    	jmeterVariables.remove(counterKey);
    }
    
  
}