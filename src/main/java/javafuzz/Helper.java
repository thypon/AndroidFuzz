/*
 * Maintainer: Emmanouel Kellinis (me@cipher.org.uk) 
 * Java Classes Fuzzer - Reflection Based
 * http://www.cipher.org.uk
 */
 
 package javafuzz; 
 
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class Helper {
	
//Return a random constant to the constructor
public static Object returnConsant(Class cls)throws Exception {
	Object constant = null;
	if (cls == Object.class) return constant;
	Field[] fields = cls.getDeclaredFields();
	
	for (int n=0; n<fields.length; n++)   { fields[n].setAccessible(true);
     	try {
             	if(fields[n].get(null) != null)
         	{
             	if(fields.length>1){constant =
			 	fields[n+1].get(null);return constant;}
             	else {constant = fields[n].get(null);return constant;}
         	}
	
         	}
 		catch(Exception e) {}               }
	return constant;
	}

//Bruteforce constant in the constructor
public static void BeefConstructor(Constructor cons,Object[] args, Object constant, int v, int g) throws Exception{
	String output="";
	if (constant==null) {
		if (args.length>0) {outArgs(args);out(DoIt(cons,args,v));}
		else out(DoIt(cons,v));
		}
	
	else {
		out("+++++++++++++++++++++++++++++++++++");
		out("Try the simple version - no constant");
		outArgs(args);out(DoIt(cons,args,v));
		out("++++++++++++++++++++++++++++++++++++");
		if (g==1){
		for (int a=0;a<args.length;a++)	{
			Object[] tmpr = new Object[args.length];
			for (int p=0;p<args.length;p++) {tmpr[p]=args[p];}
			tmpr[a]=constant;
			outArgs(tmpr);out(DoIt(cons,tmpr,v));
										}
        }
		 }
	
	}
//Beef with Method
public static void BeefConstructor(Constructor cons,Object[] args, Method mtd, Object constant,Object[] MethodArgs,int v,int g) throws Exception{
	String output="";
	if (constant==null) {
		if (args.length>0) {
			if (MethodArgs.length>0){outArgs(args);out(DoIt2(cons,mtd,args,MethodArgs,v));}
			else {outArgs(args);out(DoIt2(cons,mtd,args,new Double(1),v));}
			}
		else {
			if (MethodArgs.length>0){outArgs(args);out(DoIt2(cons,mtd,MethodArgs,new Integer(1),v));}
			else {outArgs(args);out(DoIt2(cons,mtd,v));}
			}
		}
	
	else {
		if (args.length>0) {
	    
		out("+++++++++++++++++++++++++++++++++++");
		out("Try the Obvious version - no constant");
		if (MethodArgs.length>0){outArgs(args);out(DoIt2(cons,mtd,args,MethodArgs,v));}
		else {outArgs(args);out(DoIt2(cons,mtd,args,new Double(1),v));}
		out("++++++++++++++++++++++++++++++++++++");
		
		if (g==1) {
		for (int a=0;a<args.length;a++)	{
			Object[] tmpr = new Object[args.length];
			for (int p=0;p<args.length;p++) {tmpr[p]=args[p];}
			tmpr[a]=constant;
            if (MethodArgs.length>0){outArgs(tmpr);out(DoIt2(cons,mtd,tmpr,MethodArgs,v));}
			else {outArgs(args);out(DoIt2(cons,mtd,tmpr,new Double(1),v));}
			}
		}	
		}
		else {
			if (MethodArgs.length>0){outArgs(args);out(DoIt2(cons,mtd,MethodArgs,new Integer(1),v));}
			else {outArgs(args);out(DoIt2(cons,mtd,v));}
		}
		
	
	}
}	

	
//Little print helpers
public static void out(String str) {System.out.println("\n"+str);}	
public static void outArgs(Object[] obj) {System.out.println("\nSubmit: ");for (int a=0;a<obj.length;a++) System.out.print(" "+obj[a]);}	
//Overloaded Construction
public static String DoIt (Constructor cons,Object[] args,int v) 
{try {cons.newInstance(args);return "No Problem";} catch(Exception e){if (v==1) {return "Exception: "+e.getCause();}else{return "Exception";}} }
public static String DoIt (Constructor cons, int v) 
{try {cons.newInstance();	 return "No Problem";} catch(Exception e){if (v==1) {return "Exception: "+e.getCause();}else{return "Exception";}} }
//Overloaded Construction/Method
public static String DoIt2 (Constructor cons,Method mtd, Object[] args, Object[] MethodArgs,int v) 
{try {mtd.invoke(cons.newInstance(args), MethodArgs);	 return "No Problem";} catch(Exception e){if (v==1) {return "Exception: "+e.getCause();}else{return "Exception";}} }
public static String DoIt2 (Constructor cons, Method mtd, Object[] MethodArgs,int one,int v) 
{try {mtd.invoke(cons.newInstance(), MethodArgs);	 return "No Problem";} catch(Exception e){if (v==1) {return "Exception: "+e.getCause();}else{return "Exception";}} }
public static String DoIt2 (Constructor cons,Method mtd, Object[] args, double two,int v) 
{try {mtd.invoke(cons.newInstance(args));	 return "No Problem";} catch(Exception e){if (v==1) {return "Exception: "+e.getCause();}else{return "Exception";}} }
public static String DoIt2 (Constructor cons,Method mtd, int v) 
{try {mtd.invoke(cons.newInstance());	 return "No Problem";} catch(Exception e){if (v==1) {return "Exception: "+e.getCause();}else{return "Exception";}} }


}


