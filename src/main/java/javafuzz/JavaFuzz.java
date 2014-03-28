/*
 * Maintainer: Emmanouel Kellinis (me@cipher.org.uk) 
 * Java Classes Fuzzer - Reflection Based
 * http://www.cipher.org.uk
 */

package javafuzz;

import gnu.getopt.Getopt;

import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.io.*;


public class JavaFuzz {
    
    /** Creates a new instance of JavaFuzz */
    public JavaFuzz()  {}
    
    /**
     * @param args the command line arguments
     */ 
    
    //Static Values Init 
    public static int Exceed =0;
    //Global Overflowing Data
    public static  int    ExceedInt   =0;// Integer.MAX_VALUE;
    public static  double ExceedDouble=0;// Double.MAX_VALUE;
    public static  float  ExceedFloat =0;// Float.MAX_VALUE;
    public static  short  ExceedShort =0;// Short.MAX_VALUE;
    public static  long   ExceedLong  =0;// Short.MAX_VALUE;
    //Set Values
    static public  short smin = Short.MIN_VALUE;
    static public  short smax = Short.MAX_VALUE;
    static public  int   imin = Integer.MIN_VALUE;
    static public  int   imax = Integer.MAX_VALUE;
    static public  long  lmin = Long.MIN_VALUE;
    static public  long  lmax = Long.MAX_VALUE;
    static public  float fmin = Float.MIN_VALUE;
    static public  float fmax = Float.MIN_VALUE;
    static public  double dmin=Double.MIN_VALUE;
    static public  double dmax=Double.MAX_VALUE;
    static public  byte bmin;
    static public  byte bmax;
    static public  char cmin;
    static public  char cmax;
    static public  boolean bomin;
    static public  boolean bomax;
    static public  String stmin;
    static public  String stmax;
	
    public static int  etternalLoop=0;
    //Recursion 
    public static  int   Recursion  =23;// Default
    //Array Size
    public static  int   ArraySize  =800;// Default
    //String size
    public static  int   StringSize  =1024;// Default
    //String starting text
    public static String Start="";
    //Methods Attack
    public static int attackMethods =0;
    //Enumerate Constant's position
    public static int oo=0;
	//Enforced Constant
	public static Object pp = null;
    //Create Helper class
    public static Helper  help = new Helper();
    //MAX or MIN Values
    public static String limit="";
 	//Filter Out - IgnoreMethod 
    public static String[] im;
	//Filter In - Fuzz only these methods
    public static String[] nim;
	//Check Filter-in and Filter-out
	public static int checkFI=0;
    public static int checkFO=0;
	//Attack and Construct Abstracts 
	public static int ANCA=0;
	//Replace Class ArrayLists
	public static ArrayList  findThisClass = new ArrayList();
    public static ArrayList  replaceItWithThisClass = new ArrayList();
	//Collect all interfaces and Abstracts
	public static String InterfacesAndAbstracts = "";
	//Auto Break
	public static int AutoBreak = 0;
	//String Global
	public static String IamBigString="";
	//Create Arrays
	public static byte[] ab;
	public static byte[][] abb;
	public static short[] as;
	public static short[][] ass;
	public static int[] ai;
	public static int[][] aii;
	public static long[] al;
	public static long[][] all;
	public static float[] af;
	public static float[][] aff;
	public static double[] ad;
	public static double[][] add;
	public static boolean[] abo;
	public static boolean[][] aboo;
	public static char[] ac;
	public static char[][] acc;
	public static String[] ast ;
	public static String[][] astt ;
	

    
public static void main(String[] args) {
String[] argv= args;
Getopt g = new Getopt("JavaFuzz", argv, ":vf:c:b:e:i:n:p:g:s:r:a:k:l:mou:");
int c;
String arg;
int vv=0,rr=0;
String ff="",ee="",cc="",ss="",ii="";

//Command Line Arguments
 while ((c = g.getopt()) != -1)
   {
     switch(c)
       {
          case 'v':
             //Verbose
            vv=1;
            break;
          case 'o':
             //Verbose
            oo=1;
            break;
           case 'm':
             //Methods Attack Flag
            attackMethods=1;
            break;
          case 'f':
            //Classes File
            arg = g.getOptarg();
            ff=arg;
            break;
          case 'k':
            //Set Value 
            arg = g.getOptarg();
            String[] getManyTypes = arg.split(",");
			
			for (int findAllTypes=0;findAllTypes<getManyTypes.length;findAllTypes++)
			{

			String[] values = getManyTypes[findAllTypes].split("=");
            if (values.length!=2 ){usage();System.exit(0);}
            else {
            if (values[0].equals("int")){ 
            imax =  Integer.parseInt(values[1]);
            imin = -Integer.parseInt(values[1]);
            }
            else if (values[0].equals("float")){ 
            fmax =  Float.parseFloat(values[1]);
            fmin = -Float.parseFloat(values[1]);
            }
            else if (values[0].equals("double")){ 
            dmax =  Double.parseDouble(values[1]);
            dmin = -Double.parseDouble(values[1]);
            }
            else if (values[0].equals("short")){ 
            smax =  Short.parseShort(values[1]);
            }
            else if (values[0].equals("long")){ 
            lmax =  Long.parseLong(values[1]);
            lmin = -Long.parseLong(values[1]);
            }
            else {usage();System.exit(0);}

            }
 			
			}
            break;

          case 'c':
            //Class
            arg = g.getOptarg();
            cc=arg;
            break;
		  case 'i':
			//Ignore methods 
			arg =  g.getOptarg();
			String[] ignoremany = arg.split(",");
			
			if (ignoremany.length==1) {
				im = new String[1];
				im[0] = arg;
				}
			else { 
				im = new String[ignoremany.length];
				im=ignoremany;
				}
			checkFO =1;
			break;
		  case 'n':
			//Fuzz only these methods 
			arg =  g.getOptarg();
			String[] nignoremany = arg.split(",");

			if (nignoremany.length==1) {
				nim = new String[1];
				nim[0] = arg;
				}
			else { 
				nim = new String[nignoremany.length];
				nim=nignoremany;
				}
			checkFI =1;
			break;
		 case 'p':
            //Enforce constant
            arg = g.getOptarg();
           	String[] values = arg.split("=");

            if (values.length!=2 ){usage();System.exit(0);}
            else {
            if (values[0].equals("int"))		{pp = (Object) Integer.parseInt(values[1]);}
            else if (values[0].equals("float")) {pp = (Object) Float.parseFloat(values[1]);}
            else if (values[0].equals("double")){pp = (Object) Double.parseDouble(values[1]);}
            else if (values[0].equals("short")) {pp = (Object) Short.parseShort(values[1]);}
            else if (values[0].equals("long"))  {pp = (Object) Long.parseLong(values[1]);}
			else if (values[0].equals("string"))  {pp = (Object) values[1];}
            else {usage();System.exit(0);}
			}


			oo=1;
			if (pp==null){usage();System.exit(0);}
            break;
          case 'e':
            //Extend
            arg = g.getOptarg();
            ee=arg;
            break;
		  case 'g':
	        //Attack and construct abstracts
			arg = g.getOptarg();
			if (arg.equals("a")) {AutoBreak=1;}
			else {
			String[] getReplacements = arg.split(",");
			for (int findAllTypes=0;findAllTypes<getReplacements.length;findAllTypes++)
			{	
				String[] replacementValues = getReplacements[findAllTypes].split("=");
				if (replacementValues.length!=2 ){usage();System.exit(0);}
				else if((replacementValues.length==2) && (replacementValues[1].equals("{A}"))) {
					try{
					findThisClass.add(replacementValues[0]);
					if (Class.forName(replacementValues[0]).isInterface())
					replaceItWithThisClass.add(findSubclass(replacementValues[0],"FindSubs.txt"));
					else if (Modifier.isAbstract((Class.forName(replacementValues[0])).getModifiers()))
					replaceItWithThisClass.add(findSubclass(replacementValues[0],"FindSubs.txt"));					
					
						}
					catch(Exception e){System.out.println(e);}}
				else {findThisClass.add(replacementValues[0]);replaceItWithThisClass.add(replacementValues[1]);	}
			}
	        	ANCA = 1;}
            break;
          case 'r':
            //Recursions
            arg = g.getOptarg();
            try {Recursion= Integer.parseInt(arg);} catch (Exception e){usage();System.exit(0);}
            break;
          case 'a':
            //Array Size
            arg = g.getOptarg();
            try {ArraySize= Integer.parseInt(arg);} catch (Exception e){usage();System.exit(0);}
            break;
          case 'l':
            //String Size
            arg = g.getOptarg();
            try {StringSize= Integer.parseInt(arg);} catch (Exception e){usage();System.exit(0);}
            break;
          case 'u':
            //String Size
            arg = g.getOptarg();
            try {limit= arg;} catch (Exception e){usage();System.exit(0);}
            break;
          case 's':
            //String
            arg = g.getOptarg();
            try {ss=arg;} catch (Exception e){usage();System.exit(0);}
            Start=ss;
            break;
          case ':':
            usage();
            break;
          case '?':
            //usage();  
            break; // getopt() already printed an error
            //
         default:
            usage();
            break;
       }
   }
	     // Create Big String once
		 if (Start.equals("")){ IamBigString = BigString("A",StringSize);}
	     else{IamBigString=BigString(Start,StringSize);}
			 //initialise variables
			bmin = -128;
			bmax = 127;
			 //Limits : char '\u0000' to '\uffff' 
			cmin='\u0000';
			cmax='\uffff';
			 //Limits : boolean true/false - this one doesnt make much sense but anyways
			bomin=false;
			bomax=true;
			 //Limits : string 
			stmin =IamBigString.substring(0,1);
			stmax =IamBigString;;
		     //Initialise Arrays
			ab 	= new byte[ArraySize];//{bmax,bmin}
			//Multi-dimensional arrays -- monkey business at the moment will do it more genericly soon
			abb	= new byte[ArraySize][ArraySize];//{bmax,bmin}
			//Limits : short -32,768 and a maximum value of 32,767
			as  = new short[ArraySize];//{smax,smin}
			ass = new short[ArraySize][ArraySize];//{smax,smin}
			//Limits : int minimum value of -2,147,483,648 and a maximum value of 2,147,483,647 
			ai  = new int[ArraySize];//{imax,imin}
			aii = new int[ArraySize][ArraySize];//{imax,imin}
			//Limits : long minimum value of -9,223,372,036,854,775,808 and a maximum value of 9,223,372,036,854,775,807
			al  = new long[ArraySize]; //{lmax,lmin}
			all = new long[ArraySize][ArraySize];
			//Limits : float  single-precision 32-bit IEEE 754 floating point
			af  = new float[ArraySize];//{fmax,fmin}
			aff = new float[ArraySize][ArraySize];//{fmax,fmin}
			//Limits : double double-precision 64-bit IEEE 754 floating point
			ad  = new double[ArraySize];//{dmax,dmin}
			add = new double[ArraySize][ArraySize];//{dmax,dmin}
			abo = new boolean[ArraySize];//{bomax,bomin}
			aboo= new boolean[ArraySize][ArraySize];//{bomax,bomin}
			ac  = new char[ArraySize];//{cmax,cmin}
			acc = new char[ArraySize][ArraySize];//{cmax,cmin}
			ast = new String[ArraySize];//{stmin,stmax}
			astt= new String[ArraySize][ArraySize];//{stmin,stmax}
	
	
         if     (ee.equals("int"))      { ExceedInt    = Integer.MAX_VALUE;}
         else if(ee.equals("double"))   { ExceedDouble = Double.MAX_VALUE; }
         else if(ee.equals("float"))    { ExceedFloat  = Float.MAX_VALUE;  }
         else if(ee.equals("short"))    { ExceedShort  = Short.MAX_VALUE;  }
         else if(ee.equals("long"))     { ExceedLong   = Long.MAX_VALUE;   }

       if ((!ff.equals("") && !cc.equals(""))||(ff.equals("") && cc.equals(""))||(checkFI!=0 && checkFO!=0)){usage();}
       else {
    
       if      (!cc.equals("")) {
                try {   summarize(cc,vv);
		
				if (vv==1&&(!InterfacesAndAbstracts.equals(""))) 
						{
						System.out.println("\n************************");
						System.out.println("The following classes may not have been instantiated");
						System.out.println("************************\n\nTry replacing classes with -g");
						System.out.println(InterfacesAndAbstracts+"\n");
						}
        
        		} catch (Exception ex) {
                  usage();
                  System.out.println("+Invalid Class");
                }}
       else if (!ff.equals("")) {
                try {   recursiveAttack(ff,vv);
				
				if (vv==1&&(!InterfacesAndAbstracts.equals(""))) 
						{
						System.out.println("\n************************");
						System.out.println("The following classes may not have been instantiated");
						System.out.println("************************\n\nTry replacing classes with -g");
						System.out.println(InterfacesAndAbstracts+"\n");
						}
                
				} catch (Exception ex) {
                  usage();
                  System.out.println("+Classes File ERROR");
                }}
       }
        System.exit(1);
       
        
    }
   
  public static Object Constant =null;
  public static int enumerateConstant =1;
  public static void summarize(String className, int v)

  { try{
    Exceed=1;
    Class cls = null;
   	try{ cls = Class.forName(className);}catch(Error e){}
	if (pp==null) {Constant = help.returnConsant(cls);}
	else {Constant = pp;}

    if (Constant!=null) 
    {System.out.println("\nNOTE: This class takes Constant values. Try -o flag\n");}
	//Check if we have interface 
    if (cls.isInterface())
	{	
		System.out.println("\n* "+cls.getName()+" is an Interface");	
	

	}
	
	if(Modifier.isAbstract(cls.getModifiers())) 
	{
		System.out.println("\n* "+cls.getName()+": Is an Abstract\n");
	
	}
	


    Constructor[] a = cls.getConstructors();
    Object[] args ;
    System.out.println("--------------------------------------");  
    for (int f=0;f<a.length;f++){
       Class[] ff =  a[f].getParameterTypes();
       Class[] types =  ff ;
       System.out.print("Constructor -> \t"+a[f].getName()+"\nTypes -> \t(");

       for (int k=0;k<ff.length;k++)
       {System.out.print(" "+ff[k].getName());}
       System.out.print(" )\n");
       System.out.println("Invoke -> \t"+className);
       Constructor cons = cls.getConstructor(types);
       
       //High Values - No Methods
       etternalLoop=0;
       if (limit.equals("high") | limit.equals("") ){
       args =  slapObject(ff,1,Exceed) ;
       System.out.print("\n[MAX] Status -> \t");
	   help.BeefConstructor(cons,args,Constant,v,oo);
       System.out.print("\n");
       }   
       //Low Values - No Methods
       if (limit.equals("low") | limit.equals("") ){
       args =  slapObject(ff,0,Exceed) ;
       System.out.print("[MIN] Status -> \t");
	   help.BeefConstructor(cons,args,Constant,v,oo);
       System.out.print("\n");	
       }
       //Method Attack
       if (attackMethods==1){
       //Hi Values
       if (limit.equals("high") | limit.equals("") ){    
       args =  slapObject(ff,1,Exceed) ;
	   methodSlap(a[f],cls,args,1,v);
       }
       //Low Values
       if (limit.equals("low") | limit.equals("") ){
       args =  slapObject(ff,0,Exceed) ;
       methodSlap(a[f],cls,args,0,v);
       }
       }
     
       System.out.println("--------------------------------------");
       
      
   }
    } catch(Exception e){}
  }
  
  public static void DoIt (Constructor cons, Object[] args, int v){
       try {
	   cons.newInstance(args);
       System.out.print(" No Problem\n");
       }
       catch(Exception e){
       if (v==1){ System.out.print("Exception("+e.getCause() +")\n");}
       else {System.out.print("Exception\n");}
       }
   }
    public static void DoIt (Constructor cons, int v){
       try {
	   cons.newInstance();
       System.out.print(" No Problem\n");
       }
       catch(Exception e){
       if (v==1){ System.out.print("Exception("+e.getCause() +")\n");}
       else {System.out.print("Exception\n");}
       }
   }
   
        
  
  
  //Expand Methods and throw Slaped Objects in
  public static void  methodSlap(Constructor cs,Class cls,Object[] args,int hilo,int v)  {
  try {
  Method[] allMethods = cls.getDeclaredMethods();
  String hilow;
  System.out.println("\n~~ Methods Fuzzing ~~");

  Object tmpCLS = help.returnConsant(cls);
  if (pp!=null) {tmpCLS = pp;}
  
  for (int a=0;a<allMethods.length;a++)
  {             
				Class[] cc = allMethods[a].getParameterTypes();
                etternalLoop=0;
                Object[] MethodArgs =  slapObject(cc,hilo,Exceed) ;

				int Justincase =0;
				if (checkFO==1)
				{
				try {

				for	(int checkIgnore=0;checkIgnore<im.length;checkIgnore++)
				{
					if (allMethods[a].getName().equals(im[checkIgnore])) {Justincase=1;} 
				} 
				}
				catch(Exception e){}
				}
				
				else if (checkFI==1)
				{
				try {

				for	(int checkIgnore=0;checkIgnore<nim.length;checkIgnore++)
				{
					if (!allMethods[a].getName().equals(nim[checkIgnore])) {Justincase=1;} 
				} 
				}
				catch(Exception e){}
				}
				
				
				
				if (Justincase==1){ 
				if (checkFI==0) System.out.print("\nMethod -> \t"+allMethods[a].getName()+" ["+cls.getName()+"]: -=IGNORED=-\n");
				}
				
				else {
				System.out.print("\nMethod -> \t"+allMethods[a].getName()+" ["+cls.getName()+"]\nTypes -> \t(");
                for (int k=0;k<cc.length;k++){System.out.print(" "+cc[k].getName());}
                System.out.print(" )\n");
                help.BeefConstructor(cs,args,allMethods[a],tmpCLS,MethodArgs,v,oo);
				}
				
				
			
  }
  System.out.println("\n~~~~~~~~~~~~~~~~~~~~~");
  }
  catch(Exception e){}
   
  }
  
  

public static Object[] slapObject (Class[] cls,int hilow,int E) {
    etternalLoop++;;        
    Object[] list = new Object[cls.length];String ArrayOfOtherStuff ="";
     try{
    E=0;
    for (int k=0;k<cls.length;k++){
    String current = cls[k].getName();     
    boolean max=false;
    if(hilow==1){max=true;}
    if (current.equals("int")) {
        if(max){list[k]=(imax+ExceedInt);}
        else {list[k]=imin-(ExceedInt);}
    }
    else if (current.equals("[I")){list[k]=ai;}
    else if (current.equals("[[I")){list[k]=aii;}
    else if (current.equals("char")){
        if(max){list[k]=cmax;}
        else {list[k]=cmin;}
   }
    else if (current.equals("[C")){list[k]=ac;}
    else if (current.equals("[[C")){list[k]=acc;}
    else if (current.equals("float")){
        if(max){list[k]=fmax+ExceedFloat;}
        else {list[k]=fmin-(ExceedFloat);}
   }
    else if (current.equals("[F")){list[k]=af;}
    else if (current.equals("[[F")){list[k]=aff;}
    else if (current.equals("short")){
        if(max){list[k]=smax+ExceedShort;}
        else {list[k]=smin-(ExceedShort);}
   }
    else if (current.equals("[S")){list[k]=as;}
    else if (current.equals("[[S")){list[k]=ass;}
    else if (current.equals("boolean")){
        if(max){list[k]=bomax;}
        else {list[k]=bomin;}
   }
    else if (current.equals("[Z")){list[k]=abo;}
    else if (current.equals("[[Z")){list[k]=aboo;}
    else if (current.equals("double")){
        if(max){list[k]=dmax+ExceedDouble;}
        else {list[k]=dmin-(ExceedDouble);}
   }
    else if (current.equals("[D")){list[k]=ad;}
    else if (current.equals("[[D")){list[k]=add;}
    else if (current.equals("long")){
        if(max){list[k]=lmax+ExceedLong;}
        else {list[k]=lmin-(ExceedLong);}
   }
    else if (current.equals("[J")){list[k]=al;}
    else if (current.equals("[[J")){list[k]=all;}
    else if (current.equals("byte")){
        if(max){list[k]=bmax;}
        else {list[k]=bmin;}
   }
    else if (current.equals("[B")){list[k]=ab;}
    else if (current.equals("[[B")){list[k]=abb;}
    else if (current.equals("java.lang.String")){
        if(max)
        {
            list[k]=stmax;
        }
        else {list[k]=stmin;}
   }
    else if (current.equals("[Ljava/lang/String")){list[k]=ast;}
    else if (current.equals("[[Ljava/lang/String")){list[k]=astt;}
	else if (current.indexOf("[L")!=-1 && current.indexOf(";")!=-1) 
		    {list[k] = Array.newInstance(Class.forName(current.substring(2,current.length()-1)), ArraySize);}
    //Construct - Uknown Object
    else {
	
	    try {
		    Class clsa = Class.forName(current);
			
			//Replace abstract with a constructor that can implement it
			if (ANCA ==1)
			{

			for (int lookThroughAndCheck=0;lookThroughAndCheck<findThisClass.size();lookThroughAndCheck++)
			{
				if (findThisClass.get(lookThroughAndCheck).equals(clsa.getName())){ clsa = Class.forName(""+replaceItWithThisClass.get(lookThroughAndCheck));}
			}
			

			}
			
			if (AutoBreak==1)
			{
			if (clsa.isInterface()) 
			{      			
				clsa = Class.forName(findSubclass(clsa.getName(),"FindSubs.txt"));
			}
			if (Modifier.isAbstract(clsa.getModifiers())) 
			{			
			    clsa = Class.forName(findSupers(clsa.getName(),"FindSubs.txt"));
			}
			}

		  	if (clsa.isInterface())
			{	
				
				if(	InterfacesAndAbstracts.indexOf(clsa.getName()+" is an Interface")==-1)
				{InterfacesAndAbstracts = InterfacesAndAbstracts+"\n* "+clsa.getName()+" is an Interface";	}
			}
		
			
			if(Modifier.isAbstract(clsa.getModifiers())) 
				{
				
				if(	InterfacesAndAbstracts.indexOf(clsa.getName()+" is an Abstract")==-1)
				InterfacesAndAbstracts = InterfacesAndAbstracts+"\n* "+clsa.getName()+" is an Abstract";
				}
			
        	Object Constant1 = help.returnConsant(clsa);
		
			if (pp!=null){Constant1=pp;}
	        
			Constructor[] a = clsa.getConstructors();
        	Object[] args ;
            int check=0;
        	for (int f=0;f<a.length;f++)   {
	        Class[] ff =  a[f].getParameterTypes();
            Constructor cons = clsa.getConstructor(ff);
             if   (etternalLoop<Recursion){args =  slapObject(ff,1,0) ;}
             else {System.out.println("\n****Infinite Loop detected (use -r)****"); args=null;}

             		if (args.length>0){
                          for(int h=0;h<args.length;h++){ 
                          Object[] tmpr = new Object[args.length];
                          for (int p=0;p<args.length;p++) {tmpr[p]=args[p];}
                                //Show Submitted Values
                                System.out.print("\nSubmit Values - Sub-constructor ("+clsa.getName()+"):\n\t ");
								if (h>0) {tmpr[h-1] = pp;}
                                for (int display=0;display<args.length;display++)
                                {System.out.print(tmpr[display]+" ");}                    
                                try   {list[k]=cons.newInstance(tmpr);System.out.print(":No Problem\n");check=1;break;}
                                catch (Exception e){System.out.print(""+e.getCause());
                                try   {list[k]=cons.newInstance(args);System.out.print(":No Problem\n");check=1;break;}
                                catch (Exception ea){
						
				} 
                                }                    						   
                              }
						}
			
					else { try    {list[k]=cons.newInstance();check=1;break;} 
					catch  (Exception e){list[k]=null;break;}
						}       	
        	    
				if (check==1) {break;}
   				}
   				
   				if (check!=1) {list[k]=null;}
		    
		     }
    	catch (Exception e){System.out.println("hat?"+e);}
    	
    	
    	}//Major ELSE
    
    }
    
    return list;}
    
    
    
    
    
    catch(Exception e){/*Not able to construct types*/ }
    return list;
    }

    
        
static String BigString (String str,int size){
String tmp="";
for (int a=0;a<size;a++){tmp=tmp+str;}
return tmp; }


public static String findSubclass(String Pclass, String FileName)  {
            String subClass="",tryS="";                
      	    try {
			InputStream fstream =(JavaFuzz.class.getResourceAsStream(FileName)) ;
	    	DataInputStream in = new DataInputStream(fstream);
            while (in.available() !=0){ 
		try 
		{  
		tryS = in.readLine();
		Class Tester = Class.forName(tryS);
		Class[] inter = Tester.getInterfaces();
		for(int a=0;a<inter.length;a++) 
		{
		if ((inter[a].getName()).equals(Pclass))
		{
			if (!Modifier.isAbstract(Tester.getModifiers())) {return tryS;}
		}
		}


		}
		catch(Exception e){} 

		 }
            in.close();
			} catch (Exception e) {}
 return subClass;
}
		
public static String findSupers(String Pclass, String FileName)  {
		           String subClass="",tryS="";
			    try {
		     	    InputStream fstream =(JavaFuzz.class.getResourceAsStream(FileName)) ;
			    	DataInputStream in = new DataInputStream(fstream);
		           while (in.available() !=0)
				 	{
							try
							{
							tryS = in.readLine();
							Class Tester = Class.forName(tryS);
							Class inter = Tester.getSuperclass();

								if ((inter.getName()).equals(Pclass))
								{
									if (!Modifier.isAbstract(Tester.getModifiers()) && !Tester.isInterface()) {return tryS;}
								}
								}
				catch(Exception e){}
				 }
		           
					in.close();
				} catch (Exception e) {}
 	return subClass;
}

 
   

    
static void recursiveAttack(String FileName,int v) throws Exception {
    
     
                               
       FileInputStream fstream = new FileInputStream(FileName);
	 DataInputStream in = new DataInputStream(fstream);
             while (in.available() !=0)
		 { try {  JavaFuzz.summarize(in.readLine(), v);}catch(Exception e){} }
            in.close();
         
}
	public  static String version = "0.7.5";
    private static void usage() {
				System.out.println("\n= =============================================== =");	
                System.out.println("= JavaFuzzer - Classes Fuzzing (Reflection Based) =");
				System.out.println("= =============================================== =\n");
				System.out.println("= Version "+version+" =\n");
               	String output =
                                "\n"+"FLAGS"+
                                "\n"+"-v: Verbose - Fully Print Exceptions"+
                                "\n"+"-m: Fuzz methods of a Class, Can take Long time to finish"+
                                "\n"+"-f: Read Class names from a file"+
                                "\n"+"-c: Input is Class name, you cannot use -f at the same time"+
                                "\n"+"-s: You can set the fuzzing String, for example http://www.example.com"+
								"\n"+"    if you dont want repeats, use it with -l1 "+
                                "\n"+"-e: You can set the type you want to overflow with the MAX_VALUE on top "+
                                "\n"+"    Values can be : int or double or float or long or short"+
                                "\n"+"-r: Number of recursions until constructs the class [Default 20]"+
                                "\n"+"    If needs more it will set type to null and consider it Infinite"+
                                "\n"+"-k: Set the value for int,float,long,short,double"+
                                "\n"+"    e.g. -k int=100  or -k double=20000 or -k int=19,float=49 and so on."+                               
                                "\n"+"-a: Set size of used array when fuzzing  [Default 800]" +
                                "\n"+"-l: Set size of String when fuzzing [Default 1024], if -s is not defined A is default"+
                                "\n"+"-o: Enumerate possible constructor's Constant parameters"+
                                "\n"+"    Bruteforce's all possible positions for the constant (extra delay)"+
                                "\n"+"-i: JavaFuzz will ignore the specified method(s) helpful when you found a bug "+
                                "\n"+"    in a method but you want to dig deeper. (Seperate methods with commas)"+
                                "\n"+"    e.g. for java.awt.Image you could use -i getGraphics,getScaledInstance  "+    
                                "\n"+"-n: JavaFuzz will fuzz the specified method(s) only, for a given class"+    
                                "\n"+"    e.g. for java.awt.Font you could use -n applySize,pDispose  "+  
                                "\n"+"    NOTE: You cannot use -i at the same time"+                           
                                "\n"+"-u: Fuzz only positive or negative values respectively e.g. Integer high is +MAX_VALUE"+
                                "\n"+"    and low value is -MAX_VALUE (or MIN_VALUE). -u low or -u high "+
								"\n"+"-p: Enforce a Constant and bruteforce the position  "+
								"\n"+"    type can be int,double,float,short,string   e.g. -p double=1 "+
								"\n"+"-g: Use it when you want to replace a class, for example it could be used to replace"+
								"\n"+"    abstract classes or interfaces -g org.replace.this=org.with.this"+
							    "\n"+"    the auto replacement mode can be invoked using -g org.replace.this={A}"+
							    "\n"+"    and for complete automation use -ga "+
                                "\n\n"+"EXAMPLES"+
                                ""+""+
                                "\n"+"java -jar JavaFuzz.jar -c java.lang.String -v"+
                                "\n"+"java -jar JavaFuzz.jar -f classes.txt -v -e int"+
                                "\n"+"java -jar JavaFuzz.jar -c java.net.URL -e int -s http://www.example.com\n\n";
               System.out.println(output);
			   System.exit(1);

    }
    
    
   
 
}