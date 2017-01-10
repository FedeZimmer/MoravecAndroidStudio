package tedxperiments.math.entrenamente;


import java.util.ArrayList;
import java.util.Calendar;


public class DataScore {
	
	//ID
	//User
	
	private int level;
	private int operand_1;
	private int operand_2;
	private String operator;
	private long CorrResult;
	private long EnteredAnswer;
	ArrayList<Integer> vectorAnswer = new ArrayList<Integer>();
	private int correct;
	private int erase;
	ArrayList<Long> resptime = new ArrayList<Long>();
	private long totaltime;
	private int confidence;
	private int effort;
	private int initalconf;
	private int initialeff;
	
	ArrayList<Integer> vectorConf = new ArrayList<Integer>();//Esto no esta en el Json
	ArrayList<Long> confTime = new ArrayList<Long>();//Esto no esta en el Json
	ArrayList<Integer> vectorEffort = new ArrayList<Integer>();//Esto no esta en el Json ESTO NO FUNCIONA porque arrastra.
	ArrayList<Long> effortTime = new ArrayList<Long>();//Esto no esta en el Json
	
	private Calendar expdate1; //Es un objeto que tiene (int year, int month, int day, int hour, int minute, int second)
	private Calendar expdate2;
	
	private int tref;
	private int timeexceeded;
	private int correctinarow;
	private long OneScore;
	//Score?
		
	private String gametype;
	private String operationtype;
	private int hintdisplayed;
	private int hintssavailable;
	private int hidden;
	private int arcorder;
	private int acccorrect;
	
	
	public void setLevel(int L) {
		level = L;
	}
	public int getLevel() {
			return level;
	}
	
	public void setOp1(int O) {
		operand_1 = O;
	}
	public int getOp1() {
			return operand_1;
	}
	
	public void setOp2(int o) {
		operand_2 = o;
	}
	public int getOp2() {
			return operand_2;
	}
	
	public void setOperator(String op) {
		operator = op;
	}
	public String getOperator() {
			return operator;
	}
	public void setResult(long R) {
		CorrResult = R;
	}
	public long getResult() {
			return CorrResult;
	}
	public void setAnswer(long A) {
		EnteredAnswer = A;
	}
	public long getAnswer() {
			return EnteredAnswer;
	}
	
	public void setTimes(ArrayList<Long> T) {
		resptime = (ArrayList<Long>) T.clone();
	}
	public ArrayList<Long> getTimes() {
			return resptime;
	}
	
	public void setVectorAns(ArrayList<Integer> V) {
		vectorAnswer = (ArrayList<Integer>) V.clone();
	}
	public ArrayList<Integer> getVectorAns() {
			return vectorAnswer;
	}
	public void setVectorConf(ArrayList<Integer> Vc) {
		vectorConf = (ArrayList<Integer>) Vc.clone();
	}
	public ArrayList<Integer> getVectorConf() {
			return vectorConf;
	}
	public void setVectorEffort(ArrayList<Integer> Ve) {
		vectorEffort = (ArrayList<Integer>) Ve.clone();
	}
	public ArrayList<Integer> getVectorEffort() {
			return vectorEffort;
	}
	
	public void setTimesConf(ArrayList<Long> Ct) {
		confTime = (ArrayList<Long>) Ct.clone();
	}
	public ArrayList<Long> getTimesConf() {
			return confTime;
	}
	
	public void setTimesEff(ArrayList<Long> Et) {
		effortTime = (ArrayList<Long>) Et.clone();
	}
	public ArrayList<Long> getTimesEff() {
			return effortTime;
	}
	
	public void setCorrect(int C) {
		correct = C;
	}
	public int getCorrect() {
			return correct;
	}
	
	public void setErase(int E) {
		erase = E;
	}
	public int getErase() {
			return erase;
	}
	
	public void setConfidence(int Co) {
		confidence = Co;
	}
	public int getConfidence() {
			return confidence;
	}
	
	public void setEffort(int E) {
		effort = E;
	}
	public int getEffort() {
			return effort;
	}
	
	public void setInitConfidence(int ICo) {
		initalconf = ICo;
	}
	public int getInitConfidence() {
			return initalconf;
	}
	
	public void setInitEffort(int IE) {
		initialeff = IE;
	}
	public int getInitEffort() {
			return initialeff;
	}
	
	public void setDate1(Calendar D) {
		expdate1 = D;
	}
	public Calendar gettheDate1() {
			return expdate1;
	}
	
	public void setDate2(Calendar D) {
		expdate2 = D;
	}
	public Calendar gettheDate2() {
			return expdate2;
	}
	
	public void setReftime(int reftime){
		tref=reftime;
	}
	
	public int getReftime(){
		return tref;
	}
	
	
	public void setTimeEx(int TimeEx){
		timeexceeded=TimeEx;
	}
	
	public int getTimeEx(){
		return timeexceeded;
	}
	public void setCorrinarow(int CiR){
		correctinarow=CiR;
	}
	
	public int getCorrinarow(){
		return correctinarow;
	}
	
	public void setGameType(String gt) {
		gametype = gt;
	}
	public String getGameType() {
			return gametype;
	}
	
	public void setOpType(String ot) {
		operationtype = ot;
	}
	public String getOpType() {
			return operationtype;
	}
	
	public void setHintDisp(int hd){
		hintdisplayed=hd;
	}
	
	public int getHintDisp(){
		return hintdisplayed;
	}
	
	public void setHintAva(int ha){
		hintssavailable=ha;
	}
	
	public int getHintAva(){
		return hintssavailable;
	}
	
	public void setHidden(int hdd){
		hidden=hdd;
	}
	
	public int getHidden(){
		return hidden;
	}
	
	public void setArcorder(int ao){
		arcorder=ao;
	}
	
	public int getArcorder(){
		return arcorder;
	}
	
	public void setAcccorrect(int ac){
		acccorrect=ac;
	}
	
	public int getAcccorrect(){
		return acccorrect;
	}
	
		
	public long getMyScore() {
		totaltime = resptime.get(resptime.size() - 1);
		
		OneScore= (long) (1000*correct*(0.6*level+ 0.4*Math.exp(-totaltime/(long)(tref*1000/2))));
		//tiene que ser un Tref que depende del nivel.
	return OneScore;
	}
	
	public long getTotTime() {
		totaltime = resptime.get(resptime.size() - 1);
		return totaltime;
	}


}
