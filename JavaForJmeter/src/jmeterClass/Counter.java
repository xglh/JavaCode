package jmeterClass;
//计数器
public class Counter {
	
	public int countBegin;     //初始值
	public int countStep;      //步长，一次所加的值
	public int previousNum;    //前一次计数的值
	public int currentNum;     //当前值
	public int countTimes;     //计数次数
	
	//初始化,默认参数为(1,1)
	public Counter() {		
		this(1,1);
	}
	//初始化
	public Counter(int countBegin, int countStep) {		
		this.countBegin = countBegin;
		this.countStep = countStep;
		this.previousNum = countBegin;
		this.currentNum = countBegin;
		this.countTimes = 0;
	}
	//执行计数
	public int count(){
		previousNum = currentNum;
		currentNum += countStep;
		countTimes ++; 
		return currentNum;
	}
	
	//获取当前值
	public int getCount(){
		return currentNum;
	}
	
	//获取第几次计数的值
	public int getCount(int countTimes){
		return countBegin + countStep * countTimes;
	}
	
	//根据数字获取第几次计数
	public int getCounTimes(int num){
		return (num - countBegin) / countStep;
	}
}
