package calculator.fomy.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class History extends Activity{
	
	
	String historyString;
	StringBuilder hist;
	TextView line;
	Button b;
	FileInputStream fin;

	
	String[]historyArray;
	//ArrayAdapter adapter;
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.history_layout); 
		 Intent intent=getIntent();
		 
		 try {
			fin = openFileInput("history.txt");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int c;
		 String temp="";
		 try {
			while( (c = fin.read()) != -1){
			    temp = temp + Character.toString((char)c);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 try {
			fin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 historyString=temp;
		 line=(TextView) findViewById(R.id.line);
		 b=(Button)findViewById(R.id.back);
		 line.append("\n");
		 historyArray = historyString.split(",");
		 
		
		 //String [] histRev=reverseString(historyArray);
		 /*for(String s:histRev){
			 line.append(s+"\n");
		 }*/
		 for(String s:historyArray){
			 line.append(s+"\n");			
		 }
		 /*for (int i = historyArray.length-1; i >=(historyArray.length-1)-10; i--) {
			 if(historyArray[i]!=null)
			 line.append(historyArray[i]+"\n");
			 else
				 line.append(""); 
		 }*/
		 
		 
		OnClickListener back=new View.OnClickListener() {
			
			public void onClick(View v) {
				
				goBack();
				
			}
		};
		 
		b.setOnClickListener(back);
		 }
		 
		public void goBack(){
			Intent go= new Intent (this,CalculatorActivity.class);
			startActivity(go);
			finish();
		}
		public static String[] reverseString(String[] hist)
		{
		    String[] t = new String[hist.length];

		    for (int i = 0; i < hist.length; i++)
		    {
		        t[i] = "";
		        for (int j = hist[i].length() - 1; j >= 0; j--)
		            t[i] += hist[i].charAt(j);
		    }
		    return t;
		}
		 
	 }
	 
	 
	
	
	
	

