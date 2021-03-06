**command style**

```
awk -Fs '/pattern/ {action}' input-file
awk -Fs '{action}' input-file
# awk commands in separate file
awk -Fs -f myscript.awk input-file
```

*-F* is the field separator. If you don't specify, it will use an empty space as field delimiter.

*-f* executes awk commands from a separate file

The */pattern/* and the *{action}* should be enclosed inside single quotes.

*/pattern/* is optional. If you don't provide it, awk will process all the records from the input-file. If you specify a pattern, it will process only those records from the input-file that match the given pattern.

*{action}* - These are the awk programming commands, which can be one or multiple awk commands. The whole action block (including all the awk commands together) should be closed between { and }

*input-file* - The input file that needs to be processed.

tips: multiple actions/awk-commands separate with semicolon(;)


**Awk program structure/workflow**

*BEGIN block*:optional,executed once before body block

```
BEGIN {awk-commands}
```

*body block*:executed once for every line

```
/pattern/ {awk-commands}
```

*END block*:optional,executed once after block

```
END {awk-commands}
```

![](https://raw.github.com/zhoufeng1989/notes/master/linux-tools/images/awk-flow.jpg)


**print command**

print default print full record (without any argument);

print specific fields in a record :passing *$field-number* as a print command argument.

$0 represent the full record

**printf**

format string to output

**awk built-in variables**

*FS*: field separator

can be multiple,note default FS is not only only a single white space,
like this: FS="[*; ]"


*OFS*

FS is for input field separator. OFS is for output field separator. OFS is 
printed between consecutive fields in the output. By default, awk 
prints the output fields with space between the fields.

*notice:*

When you don't separate values with a comma in the print statement, 
awk will not use the OFS; instead it will print the values with nothing 
in between.

```
awk 'BEGIN { print "test1","test2" }' 
=> test1_test2(_ represents OFS)
awk 'BEGIN { print "test1" "test2" }' 
=> test1test2
```

*RS*

record separator,The default record separator used by awk is new line.

*ORS*

RS is for input record separator. ORS is for output record separator.

*NR*

When used inside the loop, this gives the line number. When used in the END block, this gives the total number of records in the file.

*NF*

number of field,$NF:content of last field

*FILENAME*

the name of the file Awk is currently processing.
When you read the values from the standard input, FILENAME variable will be set to the value of "-"
FILENAME inside the BEGIN block will return empty value "", as  the BEGIN block is for the whole awk program, and not for any specific file.

*FNR*

NR keeps growing between multiple files. When the body block starts 
processing the 2nd file, NR will not be reset to 1, instead it will continue from the last NR number value of the previous file.

FNR will give you record number within the current file. 



**variables**

There are no data types in Awk. Whether an awk variable is a number or a string depends on the context in which the variable is used in.

**operator**

 - unary operator
    -/+/++/--

 - arithmetic operator
    +- * / %

 - string operator

    space is string operator that does string concatenation

 - assignment operator
    = +=  -=  *= /= %=

 - comparison operator
    \> >= < <= == != && ||

    If you don't specify any action, awk will print the whole record if it matches the conditional comparison.

find max uid in linux system

```
awk \
'BEGIN{FS=":"} \
$3>maxuid{\
    maxuid=$3;max_record=$0\
}\
END{print "maxuid="maxuid, "max_record="max_record}' \
/etc/passwd
```

 - regular expression operators

    ~ match

    !~ not match

    calculate user count who login with /bin/bash

    ```
    awk 'BEGIN {FS=":"} $NF~/\/bin\/bash/ {count++} END{print "count="count}' /etc/passwd
    ```


**conditional statements**

 - if

```
if (condition-expression)

    action

if (condition-expression)

{

    action1;

    action2;

}
```

 - if else

```
if (condition-expression)

{

    ....

}

  else

{

....

}
```

 - ternary operator:

```
conditional-expression ? action1 : action2 ; 
```


**loops**

 - while

```
while(condition)

    action
```

 - do while
    
```
do

    action

while(condition)
```

 - for

```
for(initialization;condition;increment/decrement) 
     actions
```

 - contine and break

 - exit

The exit statement causes the script to immediately stop executing the current commands, and also ignores the remaining lines from the
input file.



**array**

+   arrary in awk is associate,you need not to declare array before you use it.  
    for awk's perspective, all indexes are strings; arrayname[123] is the same as arrayname['123'].   
    calculate user count group by shell:

    ```
    # test wheather index in array
    
    if ( index in array-name )
    
    # for loops:
    # var is the index of arrayname
    for (var in arrayname)
         actions
    ```

+   delete array element: delete arrayname[index];

    delete array: delete arrayname;

+   sort array

    *asort(arrayname)*  
    sort array by value,index re-organized from 0, orign array index is missing;

    *asort(arrayname, newarray)*    
    store new array in newarray, orign array is unchanged;

    sort array index  
    *asorti(arrayname)*: sort array by index, index becomes value, index re-organized from 0, origin value is mssing;   
    *asorti(arrayname, newarray)*: store new array in newarray, origin array is unchanged;



**string functions**

+   index  
index function can be used to get the index (location) of the given string (or character) in an input string.

```
awk 'BEGIN{print index("defabc","ab")}'
=> 4
```

+   length
the length of string

+   split
split string into array with separator 
```
split(input-string,output-array,separator)
```

+   substr:
```
substr(input-string, location, length)
```

+   sub:  
```
sub(original-string,replacement-string,string-variable) 
```
original-string can be regular expression;

+   gsub:   
gsub stands for global substitution. gsub is exactly same as sub,except that all occurrences of original-string are changed to 
replacement-string.



**Argument processing**  
ARGC contains the total number of arguments passed to the awk script.   
ARGV is an array contains all the arguments passed to the awk script in the index from 0 through ARGC   
ARGV[0] will always contain awk.   
When you pass 5 arguments, ARGC will contain the value of 6.   
The ARGIND is the index to this ARGV array to retrieve the current file.

**environment variable**   
ENVIRON is an array that contains all the shell environment values. The index to the ENVIRON array is the environment variable name.

```
awk 'BEGIN{OFS="=";for (x in ENVIRON){print x, ENVIRON[x]}}'
```

**profile tools**   
pgawk

**function**  

+   user-defined functions    

```
function fn-name(parameters)
{
function-body
}
```

+   system function   
```
awk 'BEGIN{system("pwd")}'
=> zhoufeng
```

+   timestamp function   
```
awk 'BEGIN{print systime()}'
=> 1387322514
```

+   getline   
after getline is executed, the awk script sets the value of NF, NR, FNR, and $0 built-in variables appropriately.

example: alternate output between two files

![](https://raw.github.com/zhoufeng1989/notes/master/linux-tools/images/awk-getline.png)


```
# get input from *inx command
awk 'BEGIN{"date" | getline tmp;close("date");print tmp}'

=> Wed Dec 18 07:52:05 CST 2013
```

