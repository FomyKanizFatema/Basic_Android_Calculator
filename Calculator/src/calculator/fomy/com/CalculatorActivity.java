package calculator.fomy.com;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

//import android.view.View.OnClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class CalculatorActivity extends Activity{
	
	
	//Variable Declaration
	TextView screen;        
	Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,bAdd,bSub,bDiv,bMul,bAns,bDot,bCE,bC,bMem,bDel,bMC,bMR,bMP,bMM;
	String exp,number1, number2, sign,output;	
	String[] history={"History"};
	double num1,num2,result;
	boolean lastDot,lastOperator,lastNumaric,clear,calculationDone,dotInSingleTerm,noOperator,memoryR;
	int savedIndex=0;
	SharedPreferences pref;
	Editor editor;
	Context context ;
	SharedPreferences.Editor edit;
	StringBuilder sb;
	List historyList;
	double memory,lastAnswer;
	String tempStringToCheckDot;
	
	
	
	
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        
        //Initialization
        screen=(TextView) findViewById(R.id.screen);    
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(screen.getWindowToken(), 0);
		b0=(Button) findViewById(R.id.btn0);
		b1=(Button) findViewById(R.id.btn1);
		b2=(Button) findViewById(R.id.btn2);
	    b3=(Button) findViewById(R.id.btn3);
	    b4=(Button) findViewById(R.id.btn4);
	    b5=(Button) findViewById(R.id.btn5);
	    b6=(Button) findViewById(R.id.btn6);
	    b7=(Button) findViewById(R.id.btn7);
	    b8=(Button) findViewById(R.id.btn8);
	    b9=(Button) findViewById(R.id.btn9);        
	    bAdd=(Button) findViewById(R.id.btnAdd);
	    bSub=(Button) findViewById(R.id.btnSub);
	    bMul=(Button) findViewById(R.id.btnMul);
	    bDiv=(Button) findViewById(R.id.btnDiv);        
	    bDot=(Button) findViewById(R.id.btnDot);
	    bCE=(Button) findViewById(R.id.btnCE);
	    bC=(Button) findViewById(R.id.btnC);
	    bMem=(Button) findViewById(R.id.btnMem);
	    bDel=(Button) findViewById(R.id.btnDel);
	    bAns=(Button) findViewById(R.id.btnAnswer);
	    bMC=(Button) findViewById(R.id.btnMC);
	    bMP=(Button) findViewById(R.id.btnMPlus);
	    bMM=(Button) findViewById(R.id.btnMminus);
	    bMR=(Button) findViewById(R.id.btnMR);
	    
	    dotInSingleTerm=lastDot=lastOperator=false;
	    noOperator=true;
	    lastNumaric=true;
	    clear=true;
	    savedIndex=0;
	    sb=new StringBuilder();
	    memory=0;
	    lastAnswer=0;
	    File file;
	    file= new File(getFilesDir(), "history.txt");
	    
	  memoryR = false;
	   // pref= getSharedPreferences("hist", MODE_PRIVATE);
		
	    OnClickListener numberClickListener=new View.OnClickListener() {
			
			public void onClick(View v) {
				if(calculationDone==true||memoryR==true)
					screen.setText("");
				
				switch (v.getId()) {

	                case R.id.btn0: 
	                	screen.append(b0.getText()); 
	                	lastOperator=false;
	                    break;
	                
	                case R.id.btn1:
	                	screen.append(b1.getText()); 
	                	lastOperator=false;
	                    break;
	                    
	                case R.id.btn2:
	                	screen.append(b2.getText());
	                	lastOperator=false;	                	
	                    break;
	                    
	                case R.id.btn3:
	                	screen.append(b3.getText());
	                	lastOperator=false;
	                    break;
	                    
	                case R.id.btn4:
	                	screen.append(b4.getText());
	                	lastOperator=false;
	                    break;
	                    
	                case R.id.btn5:
	                	screen.append(b5.getText());
	                	lastOperator=false;
	                    break;
	                    
	                case R.id.btn6:
	                	screen.append(b6.getText());
	                	lastOperator=false;
	                    break;
	                    
	                case R.id.btn7:
		            	screen.append(b7.getText());
		            	lastOperator=false;
		                break;
		                
	                case R.id.btn8:
	                	screen.append(b8.getText());
	                	lastOperator=false;
	                    break;
	                    
	                case R.id.btn9:
	                	screen.append(b9.getText());
	                	lastOperator=false;
	                    break;
	                    
	                default:
	                    break;
	                    
					}
				lastOperator=false;
            	lastDot=false;
            	lastNumaric=true ;
            	clear=false;
            	calculationDone=false;
            	memoryR=false;
			}
		};//NumberClickListener ends here
		
		
		OnClickListener operatorClickListener=new View.OnClickListener() {
			
			public void onClick(View v) {
				
				if(calculationDone==true){
					screen.setText("");
					
				}
				
				if(sign=="+"||sign=="-"||sign=="x"||sign=="/"){{
						calculation();					
	                	lastOperator=false;
	                	lastNumaric=true ;
	                	lastDot=false;
	                	dotInSingleTerm=false;
	                	sign=null;
	                	calculationDone=true;}
					}
					
				
				switch(v.getId()){
				
				
					case R.id.btnDot:
					
					
	                	if(calculationDone==true){
	                	}
	                	else{
	                		if(dotInSingleTerm==false)
	                		screen.append(bDot.getText());
		                	 dotInSingleTerm=true;
		                	 lastDot=true;
		                	clear=false;
		                	calculationDone=false;	
	                	}	                	
	                    break;
                
                case R.id.btnCE:
                	 String fileContents = " ";
                	    FileOutputStream outputStream;

                	    try {
                	        outputStream = openFileOutput("history.txt", MODE_PRIVATE);
                	        outputStream.write(fileContents.getBytes());
                	        outputStream.close();
                	    } catch (Exception e) {
                	        e.printStackTrace();
                	    }
                	
                	
                	lastDot=false;
                	lastOperator=false;
                	clear=true;
                	sign=null;
                	calculationDone=false;
                	dotInSingleTerm=false;
                    break;
                    
                case R.id.btnC:
                	dotInSingleTerm=false;
                	screen.setText("");
                	lastDot=false;
                	lastOperator=false;                	
                	clear=true;
                	sign=null;
                	calculationDone=false;
                    break;
                    
                case R.id.btnDel:
                	String text=screen.getText().toString();
                	try{
                		if(text.equals("")){ 
                			text=" ";
                       		screen.setText(text);
                        	}
                    	else if(text.equals("Syntex Error")){
                    		text=" ";
                           	screen.setText(text);
                            }
                    		
                		else if(text.length()>1){
                			
                			if (text.charAt(text.length() - 1)=='+'
                        			||text.charAt(text.length() - 1)=='-'
                        			||text.charAt(text.length() - 1)=='x'
                        			||text.charAt(text.length() - 1)=='/'){
                				text=text.substring(0, text.length() - 1);
                				lastOperator=false;
                        		                	
                        	}
                			else{
                				text=text.substring(0, text.length() - 1);
                			}
		                	screen.setText(text) ; 
		                }
                	   else if(text.length()==1){
                		screen.setText("") ;
                	   		}
                	}
                	catch(Exception e){
                		screen.setText(" ") ;
                	}
                	
                		
                	if(text.charAt(text.length() - 1)=='.')
                	lastDot=true;
                	else if (text.charAt(text.length() - 1)=='+'
                			||text.charAt(text.length() - 1)=='-'
                			||text.charAt(text.length() - 1)=='x'
                			||text.charAt(text.length() - 1)=='/'){
                		                	lastOperator=true;
                		                	
                	}
                	
                	calculationDone=false;
                    break;
                    
                case R.id.btnMem:
                	goToNewActivity();
                	lastDot=false;
                	lastOperator=false;
                	calculationDone=false;
                    break;
                    
                case R.id.btnAdd:
                	try{
                		if(lastOperator==true){
                			
                		}
                	if(screen.getText().toString().equals("Syntex Error")){
                		String s=" ";
                		screen.setText(s);
                		}
                	String scrn=screen.getText().toString();
                	if(!scrn.equals("Syntex Error")){
                	if(scrn.indexOf('+')>=0||scrn.indexOf('-')>=0||scrn.indexOf('x')>=0||scrn.indexOf('/')>=0){
                		calculation();
                		lastOperator=false;
	                	lastNumaric=true ;
	                	lastDot=false;
	                	dotInSingleTerm=false;
	                	sign=null;
	                	calculationDone=true;
	                	}
                	
                	number1=screen.getText().toString();                	
                	if(lastNumaric==true &&lastOperator==false){
                	screen.append(bAdd.getText());
                	lastOperator=true;
                	lastDot=false;
                	clear=false;
                	sign=bAdd.getText().toString();
                	calculationDone=false;
                	 dotInSingleTerm=false;
                	}
                	}
                	else{
                		screen.setText("Syntex Error");
                	}
				}
				catch(Exception e){
            		String s=" ";
            		screen.setText(s);
            	}
                    break;
                case R.id.btnSub:
                	try{
                		if(lastOperator==true){
                			
                		}
                	if(screen.getText().toString().equals("Syntex Error")){
                		String s=" ";
                		screen.setText(s);
                		}
                	if(lastOperator==false&&screen.getText().toString().indexOf('+')>=0||screen.getText().toString().indexOf('-')>=0||screen.getText().toString().indexOf('x')>=0||screen.getText().toString().indexOf('/')>=0){
                		calculation();
                		lastOperator=false;
	                	lastNumaric=true ;
	                	lastDot=false;
	                	dotInSingleTerm=false;
	                	sign=null;
	                	calculationDone=true;
                	}
                	
                	number1=screen.getText().toString();
                	if(lastNumaric==true  &&lastOperator==false){
                		screen.append(bSub.getText());
                	
                	lastOperator=true;
                	lastDot=false;
                	lastNumaric=false;
                	clear=false;
                	sign=bSub.getText().toString();
                	calculationDone=false;
                	 dotInSingleTerm=false;
                	
                	}
				}
				catch(Exception e){
            		String s=" ";
            		screen.setText(s);
            	}
                    break;
                case R.id.btnMul:
                	try{
                		if(lastOperator==true){
                			
                		}
                	if(screen.getText().toString().equals("Syntex Error")){
                		String s=" ";
                		screen.setText(s);
                		}
                	
                	else{
                	if(screen.getText().toString().indexOf('+')>=0||screen.getText().toString().indexOf('-')>=0||screen.getText().toString().indexOf('x')>=0||screen.getText().toString().indexOf('/')>=0){
                		calculation();
                		lastOperator=false;
	                	lastNumaric=true ;
	                	lastDot=false;
	                	dotInSingleTerm=false;
	                	sign=null;
	                	calculationDone=true;
                	}
                	
                	if(lastNumaric==true &&lastOperator==false){
                		screen.append(bMul.getText());
                	
                	lastOperator=true;
                	lastDot=false;
                	lastNumaric=false;
                	clear=false;
                	sign=bMul.getText().toString();
                	calculationDone=false;
                	 dotInSingleTerm=false;
                	}
                	}
                	}
                	catch(Exception e){
                		String s=" ";
                		screen.setText(s);
                	}
                    break;
                case R.id.btnDiv:
                	try{
                		if(lastOperator==true){
                			
                		}
                	if(screen.getText().toString().equals("Syntex Error")){
                		
                		}
                	if(screen.getText().toString().equals("")){
                		String s=" ";
                		screen.setText(s);
                		}
                	if(screen.getText().toString().indexOf('+')>=0||screen.getText().toString().indexOf('-')>=0||screen.getText().toString().indexOf('x')>=0||screen.getText().toString().indexOf('/')>=0){
                		calculation();
                		lastOperator=false;
	                	lastNumaric=true ;
	                	lastDot=false;
	                	dotInSingleTerm=false;
	                	sign=null;
	                	calculationDone=true;
                	}
                	
                	if(lastNumaric==true&&lastOperator==false){
	                	screen.append(bDiv.getText());
	                	lastOperator=true;
	                	lastDot=false;
	                	lastNumaric=false;
	                	clear=false;
	                	sign=bDiv.getText().toString();
	                	calculationDone=false;
	                	dotInSingleTerm=false;
                	}
                	}
                	catch(Exception e){
                		String s=" ";
                		screen.setText(s);
                	}
                	
                    break; 
                    
                case R.id.btnAnswer:
                	String s=screen.getText().toString();
                		if (screen.getText().toString().equals(" ")
                				||screen.getText().toString().equals("")
                				||screen.getText().toString().equals(null))
                		{
			            		s=" ";
			              		screen.setText(s);
                		}
                	
                	
                	char lastCh=s.charAt(s.length() - 1);
                	
                	if( lastCh=='+'||lastCh=='-'||lastCh=='x'||lastCh=='x')
                		{lastOperator=true;}
                	else
                		{lastOperator=false;}
                	
                	
                try{
                	
                	if(lastOperator==false){
                		calculation(); 
                		}
                	else if(lastOperator==true){
                		screen.setText("Syntex Error");
                		calculationDone=true;
                	}
                	else if(screen.getText().toString().indexOf('+')!=0&&screen.getText().toString().indexOf('-')!=0&&screen.getText().toString().indexOf('x')!=0&&screen.getText().toString().indexOf('/')!=0){
                		
                	}
                }
                catch(Exception e){
                	s=" ";
            		screen.setText(s);
                }
                	lastDot=false;
                	lastOperator=false;
                	lastNumaric=true ;
                	sign=null;
                	//calculationDone=true;
                		
	                break;

				}
				memoryR=false;
				
			}
		};
		
		
		//Memory Option MR MC M+ M-
		
		OnClickListener memoryButtonListener=new View.OnClickListener() {
			
			public void onClick(View v) {
				switch(v.getId()){
				
				case R.id.btnMPlus:
					try{
						if(screen.getText().toString().equals("")){
							String s=" ";
		            		screen.setText(s);
						}
						if(screen.getText().toString().equals("Syntex Error")){
							String s=" ";
		            		screen.setText(s);
						}
						else if(lastOperator==true){
					    	 screen.setText("Syntex Error");
					    	
					     }
						 else if(lastNumaric==true&&screen.getText().toString().indexOf('+')!=0&&screen.getText().toString().indexOf('-')!=0&&screen.getText().toString().indexOf('x')!=0&&screen.getText().toString().indexOf('/')!=0){
                			 memory= memory+Double.parseDouble(screen.getText().toString());
						 }
						 else if(lastOperator==false&&screen.getText().toString().indexOf('+')>=0||screen.getText().toString().indexOf('-')>=0||screen.getText().toString().indexOf('x')>=0||screen.getText().toString().indexOf('/')>=0){
			           			calculation();
			           			memory= memory+Double.parseDouble(output);
							     }
						 else{
							 memory=memory;
						 }
						
					}
					catch(Exception e){
						String s=" ";
	            		screen.setText(s);
					}
					break;
					
				case R.id.btnMminus:
					try{
						if(screen.getText().toString().equals("")){
							String s=" ";
		            		screen.setText(s);
						}
						if(screen.getText().toString().equals("Syntex Error")){
							String s=" ";
		            		screen.setText(s);
						}
						else if(lastOperator==true){
					    	 screen.setText("Syntex Error");
					    	
					     }
						 else if(lastNumaric==true&&screen.getText().toString().indexOf('+')!=0&&screen.getText().toString().indexOf('-')!=0&&screen.getText().toString().indexOf('x')!=0&&screen.getText().toString().indexOf('/')!=0){
                			 memory= memory-Double.parseDouble(screen.getText().toString());
						 }
						 else if(lastOperator==false&&screen.getText().toString().indexOf('+')>=0||screen.getText().toString().indexOf('-')>=0||screen.getText().toString().indexOf('x')>=0||screen.getText().toString().indexOf('/')>=0){
			           			calculation();
			           			memory= memory-Double.parseDouble(output);
							     }
						 else{
							 memory=memory;
						 }
						
					}
					catch(Exception e){
						String s=" ";
	            		screen.setText(s);
					}
					break;
					
				case R.id.btnMC:
					memory=0;
					screen.setText("0");
					break;
					
				case R.id.btnMR:
					try{
						if(screen.getText().toString().equals("Syntex Error")){
						screen.setText(Double.toString(memory));
						
						}
						else if (lastOperator==true) {
							screen.append(Double.toString(memory));
						}
						else{
							screen.setText(Double.toString(memory));
						}
						
					}
					catch(Exception e){
						screen.setText(Double.toString(memory));
						//calculationDone=true;
					}
					memoryR=true;
					break;
				}
				}				
		};
		
		
		b0.setOnClickListener(numberClickListener);
	    b1.setOnClickListener(numberClickListener);
	    b2.setOnClickListener(numberClickListener);
	    b3.setOnClickListener(numberClickListener);
	    b4.setOnClickListener(numberClickListener);
	    b5.setOnClickListener(numberClickListener);
	    b6.setOnClickListener(numberClickListener);
	    b7.setOnClickListener(numberClickListener);
	    b8.setOnClickListener(numberClickListener);
	    b9.setOnClickListener(numberClickListener);
	    bAdd.setOnClickListener(operatorClickListener);
	    bSub.setOnClickListener(operatorClickListener);
	    bMul.setOnClickListener(operatorClickListener);
	    bDiv.setOnClickListener(operatorClickListener);
	    bDot.setOnClickListener(operatorClickListener);
	    bCE.setOnClickListener(operatorClickListener);
	    bC.setOnClickListener(operatorClickListener);
	    bMem.setOnClickListener(operatorClickListener);
	    bDel.setOnClickListener(operatorClickListener);
	    bAns.setOnClickListener(operatorClickListener);
	    
	    bMC.setOnClickListener(memoryButtonListener);
	    bMP.setOnClickListener(memoryButtonListener);
	    bMM.setOnClickListener(memoryButtonListener);
	    bMR.setOnClickListener(memoryButtonListener);
    	   
  
    }

public void calculation(){
	
	exp=screen.getText().toString();	
	String[] parts;
	
	
if(sign.equals("+")){
	
	int counter = 0;
	for( int i=0; i<exp.length(); i++ ) {
	    if( exp.charAt(i) == '-' ) {
	        counter++;
	    } 
	}
	if (counter>1){
		
			try{
				parts=exp.split(sign,3);
				parts[0]=sign+parts[1];
				parts[1]=parts[2];
			}
			catch(Exception e){
				int index=exp.indexOf('+', 0);
				char[] expChars = exp.toCharArray();			
		        expChars[index] = ',';
		        exp = String.valueOf(expChars);            
		        parts = exp.split(",",3);
		        parts[0]=sign+parts[1];
				parts[1]=parts[2];
			}
		
	}
	else{
			try{
				parts=exp.split(sign,2);			
			}
			catch(Exception e){
				int index=exp.indexOf('+', 0);
				char[] expChars = exp.toCharArray();			
	            expChars[index] = ',';
	            exp = String.valueOf(expChars);            
	            parts = exp.split(",",2);
			}
		
		if(parts[1].equals("") ){
			output="Syntex Error";
		}
		else if(parts[0].equals("")){
			parts[1]=sign+parts[1];
			result=Double.parseDouble(parts[1]);
			output=Double.toString(result);
		}
		else{
		num1=Double.parseDouble(parts[0]);
		num2=Double.parseDouble(parts[1]);
		result=num1+num2;
		output=Double.toString(result);
		}
		
		
	}
}
	else if(sign.equals("-")){
		int counter = 0;
		for( int i=0; i<exp.length(); i++ ) {
		    if( exp.charAt(i) == '-' ) {
		        counter++;
		    } 
		}
		if (counter>1){
			parts=exp.split(sign,3);
			parts[0]=sign+parts[1];
			parts[1]=parts[2];
			num1=Double.parseDouble(parts[0]);
			num2=Double.parseDouble(parts[1]);
			result=num1-num2;
			output=Double.toString(result);
		}
		else{
				parts=exp.split(sign,2);
				
				if(parts[1].equals("") ){
					output="Syntex Error";
				}
				else if(parts[0].equals("")){
					parts[1]=sign+parts[1];
					result=Double.parseDouble(parts[1]);
					output=Double.toString(result);
				}
				else{
				num1=Double.parseDouble(parts[0]);
				num2=Double.parseDouble(parts[1]);
				result=num1-num2;
				output=Double.toString(result);
				}
		}
		
	}
	else if(sign.equals("x")){
		parts=exp.split(sign,2);
		
		if(parts[0].equals("") ||parts[1].equals("") ){
			output="Syntex Error";
		}
		else{
			num1=Double.parseDouble(parts[0]);
			num2=Double.parseDouble(parts[1]);
			result=num1*num2;
			output=Double.toString(result);
			
		}
		
	}
	else if(sign.equals("/")){
		parts=exp.split(sign,2);
		
		if(parts[0].equals("") ||parts[1].equals("") ){
			output="Syntex Error";
		}
		else{
		num1=Double.parseDouble(parts[0]);
		num2=Double.parseDouble(parts[1]);
		result=num1/num2;
		output=Double.toString(result);
		}
		
	}
	
	else{
		output="Syntex Error";
	}

	try{
		
		screen.setText(output);
		}
		catch(Exception e){
			screen.setText("Syntex Error");
		}
	
	sign=null;
	saveData();
	calculationDone=true;
	
}
public void saveData(){
	exp=exp+"="+output;
	sb.append(exp);
    sb.append(",");

   
    String fileContents = sb.toString();
    FileOutputStream outputStream;

    try {
        outputStream = openFileOutput("history.txt", MODE_APPEND);
        outputStream.write(fileContents.getBytes());
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

}



public void goToNewActivity(){
	try{
	
	Intent showHistory=new Intent(this,History.class);
	
	startActivity(showHistory);
	finish();
	}
	catch(Exception e){
		
	}
	
}
}
