JavaFuzz Android
================


Android JavaFuzz Version. In order to compile type:

    gradle build

Instrument Android APK
----------------------

In order to instrument it you need:

- smali
- zip/unzip
- aapt

Once you have obtained the javafuzz.jar you have to dex it:

    dx --dex --output=afuz.dex build/libs/javafuzz-1.0.jar

Uncompress it to obtain the necessary java xml resources:

    unzip build/libs/javafuzz-1.0.jar

Then uncompress the apk you need to instruments in the same directory:

    unzip your.apk

Instrument the code:

    baksmali classes.dex
    baksmali afuz.dex
    smali out
    cp out.dex classes.dex

Then build the final instrumented apk:

    aapt remove your.apk classes.dex
    aapt add your.apk classes.dex
    aapt add your.apk gnu/getopt/MessagesBundle*


Run The Fuzzer
--------------

Transfer the instrumented apk to the "device":

    adb push your.apk /sdcard/
    mkdir -p /data/local/tmp
    ANDROID_DATA=/data/local/tmp /system/bin/dalvikvm -Xss256k -Xmx100m \
        -Xbootclasspath:/system/framework/core.jar:/system/framework/ext.jar:/system/framework/framework.jar:/system/framework/android.policy.jar:/system/framework/services.jar \
        -classpath /sdcard/your.apk javafuzz.JavaFuzz -c class.to.Test -v -m


Manual
------

    NAME
         JavaFuzz - Java Class Fuzzer

    SYNOPSIS
         JavaFuzz.jar [-v] -c class [-e type] [-l StringLength]...

    DESCRIPTION
         JavaFuzz is a java classes fuzzer based on the the Java Reflection
         API. The reflection API represents, or reflects, the classes, interfaces,
         and objects in the current Java Virtual Machine. Using the reflection
         API JavaFuzz can contruct and invoke any given class (or list of classes).
         After getting the types that a class accepts will construct the classes using
         large values.


    OPTIONS
         -v   Verbose - Fully Print Exceptions. Very usuafull and you better use
              that if you want to spot any weird exceptions.

         -m   Fuzz methods of a Class, Can take Long time to finish

         -c   Classname
              Input is Class name e.g java.net.URL , you cannot use -f at the same
              time.

         -f   Read Class names from a file. Classnames should be on in each line.

         -s   You can set the fuzzing String, for example http://www.example.com
              if you dont want repeats, use it with -l1

         -e   You can set the type you want to overflow with the MAX_VALUE on top
              for example if you want to pass twice the size of a double to a class
              which is defined to accept only double you do "-e double"
              Warning: If you do that with an integer it will overflow  and
              become -2.
              Values can be : int, double, float, long, short.

         -r   Number of recursions until constructs the class [Default 20]
              If needs more it will set type to null and consider it Infinite.
              Usually when trying to construct types that dont get any arguments
              it will be fine, if it  will try to construct classes that their
              types accept arguments and so on... JavaFuzz will keep constructing
              types until it gets the asked types.

         -k   Set the value for int,float,long,short,double
              e.g. -k int=100  or -k double=20000 or -k int=19,float=49 and so on.

         -a   Set size of used array when fuzzing  [Default 800]
              This option can be maximum Integer.MAX_VALUE

         -l   Set length of used Strings when fuzzing [Default 1024]
              This option can be maximum Integer.MAX_VALUE

         -o   Find if a specific class requires a cosntant and brute-force
              all possible possitions until the constant is in the correct
              positiont. [This option will add further delays]

         -i   JavaFuzz will ignore the specified method(s) helpful when you found a bug
              in a method but you want to dig deeper. (Seperate methods with commas)
              e.g. for java.awt.Image you could use -i getGraphics,getScaledInstance

         -n   JavaFuzz will fuzz the specified method(s) only
              e.g. for java.awt.Font you could use -n applySize,pDispose
              NOTE: You cannot use -i at the same time

         -u   Fuzz only high or low values respectively e.g. Integer high is +MAX_VALUE
              and low value is -MAX_VALUE (or MIN_VALUE) [-u low or -u high]

        - p   Enforce a Constant and bruteforce the position.  Thetype can
              be int,double,float,short,string   e.g. -p double=1

         -g   Use it when you want to replace a class, for example it could be used to replace
              abstract classes or interfaces -g org.replace.this=org.with.this
              the auto replacement mode can be invoked using -g org.replace.this={A}
              and for complete automation use -ga



    EXAMPLES

             java -jar JavaFuzz.jar -c java.lang.String -v
             java -jar JavaFuzz.jar -f classes.txt -v -e int
             java -jar JavaFuzz.jar -c java.net.URL -e int -s http://www.example.com

    BUGS
             Version <= 0.3
             It cannot construct classes with types :
             a) Multidimensional array that is not int,double,float,short,long,string
             b) Array that is not int,double,float,short,long,string

    FIXES/UPDATES
             Version >= 0.7
             Enforce a Constant and bruteforce the position.  The type can be int,double,float,short,string
             flag is -p

             Version >= 0.6
             You can filter in and out method(s) and you can supply multiple types with -k
             Minor error handling fixes

             Version >= 0.5
             The bugs listed in <=0.3 are fixed. If you find the same problem let me know

    AUTHOR
         Emmanouel Kellinis <me at cipher dot org dot uk>


License
-------

The code is under GPLv2 unless specified otherwise in the single files.