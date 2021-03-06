**sed 格式**

```
# execute sed directly from shell
sed [options] {sed-command} {input-file}

# execute commands from a file:
sed [options] -f {sed-commands-in-a-file} {input-file}

# execute multiple sed commands
sed [options] -e {sed-command-1} -e {sed-command-2} {input-file}
# seperate commands by semicolon(;)
sed [options] '{sed-commands}' {input-file}
```


**sed scripting flow(REPR)**

1.  read a line into pattern space
2.  execute sed commands in sequence on the line in the pattern space
3.  print the content in the pattern space and empty the pattern space
4.  repeat this again util the end of the input file is reached

**pattern space and hold space**

+   Pattern space

used as part of the typical sed execution flow. Pattern space is the internal sed buffer where sed places, and modifies, the line it reads from the input file.

+   Hold space

This is an additional buffer available where sed can hold temporary data. Sed allows you to move data back and forth between pattern space and hold space, but you cannot execute the typical sed commands on the hold space.

pattern space gets deleted at the end of every cycle in a typical sed execution flow. However,
the content of the hold space will is retained from one cycle to the next; it is not deleted between cycles.

**sed option**

+   modify the input file directly

    if you want to back up the orignal input file,add a file extension whenever you use -i, also --in-place=

    ```
    sed -i {sed-command} {input-file}
    sed -ibak {sed-command} {input-file}
    ```

+   disable the default print -n


**specify address range**

+   If you don't specify an address range before the sed command, by default it matches all the lines. 
+   n,m   from nth to mth line 
+   n,+m  m lines starting with n
+   n~m   start at the nth line and pick up every mth line from there.
+   /pattern/  lines match pattern
eg: 1~2 match 1,3,5,7  2~2 match 2,4,6,8

**exclamation mark**

After the address (or address-range), and before the command, a !  may be inserted, which specifies that the command shall only be  executed  if  the address  (or address-range) does not match.

**sed commands**

+   p command

    Usually when p is used you will use the -n option to suppress the the default printing that
    happens as part of the standard sed flow. 
    Otherwise, when execute p (print) as one of the commands, the line will be printed twice.


+   d command

    delete lines(delete from pattern space)

    ```
    # delete empty line   

    sed '/^$/ d' a.txt
    
    # delete comment line   

    sed '/^#/ d' a.txt
    ```

+   w command(w outfile)

    write the pattern space into outfile

    ```
    #write odd line into output.txt
    sed '1~2 w output.txt' a.txt
    ```


+   substitute command

    ```
    sed '[address-range|pattern-range] s/originalstring/replacement-string/[substitute-flags]' inputfile
    ```

    substitute flags as follows:

    +   global flag(g) substitute all matches
    +   number flag(1,2..) Only the n-th instance of original-string will trigger the substitution.
    +   print flag(p) When the substitution is successful, it prints the changed line. 
    Like most print commands in sed, it is most useful when combined with the -n option to 
    suppress default printing of all lines.
    +   write flag(w) When the substitution is successful, it writes the changed line to a file.
    +   ignore case flag(i)
    +   execute flag(e) execute whatever is available in the pattern space as a shell command, 
        the output will be returned to the pattern space

    sed substitution delimiter(change it when dealing with /)   
    ```
    sed 's|/usr/local/bin|/usr/bin|' path.txt
    ```

    & 

    When & is used in the replacement-string, it replaces it with whatever
    text matched the original-string or the regular-expression
    substitution grouping(enclosing with '(' and ')') ,back reference 
    ```
    #enclose whole line with '<>'
    echo -e "line1\nline2" | sed 's/.*/<&>/'
    #output
    <line1>
    <line2>
    
    # substitution grouping and back reference
    echo '1 two 3 four' | sed 's/\([0-9]\)\(.*\)\([0-9]\)/\3\2\1/' 
    #output
    3 two 1 four
    ```
    

+   append line after(a command)
    ```
    sed '[address] a the-line-to-append' input-file
    ```

+   Insert Line Before (i command)
    ```
    sed '[address] i the-line-to-insert' input-file
    ```

+   Change Line (c command)
    ```
    sed '[address] c the-line-to-insert' input-file
    ```

+   Print Hidden Characters (l command)(list command)

+   Print Line Numbers (= command)

    ```
    #print total line of a file:
    sed -n '$ =' a.txt
    ```

+   Change Case (using the y 'transform' command) (tr)

+   Quit Sed (q command)(q vs Q)

    q will print whatever in the pattern space if default print is not depressed.  
    Q will stop without print

+   read from file(r command)

    The sed r command will read the content of another file and print it at a specified location while processing the input-file.

+   print pattern space(n command)

    The sed n command prints the current pattern space and fetches the next line from the input-file. This happens in the middle of command
execution, and so it can change the normal flow if it occurs between other commands.


+   N command:

    it adds a newline (\n) at the end of the current pattern space, appends the next line from the input-file to the current pattern space, and continues with the sed standard flow by executing the rest of the sed commands.

    ```
    #add line number at the same line:
    sed '=' /etc/passwd | sed '{N;s/\n/ /}'
    ```

+   P command:

    Upper case P command also prints the pattern space, but only until it encounters a new line (\n).

+   D command: 

    The upper case D command does not read the next line to the pattern space after deleting it, nor does it completely clear the pattern buffer (unless it only has one line). 

    Instead, it does the following:

    +   Deletes part of the pattern space until it encounters new line (\n).

    +   Aborts the rest of the sed commands and starts command execution from the beginning on the remaining content in the pattern buffer.

+   x command

    swap pattern space with hold space

+   h command:

    copy pattern space to hold space,content in hold space is overwritten.

+   H command:

    append pattern space to hold space,and add a new line at the end.



+   g command:

    copy hold space to pattern space,content in pattern space is overwritten.



+   G command:

    append hold space to pattern space and add a new line at the the end.

+   Loop and branch command
    +   :label defines the label.
    +   b label branches the execution flow to the label. Sed jumps to the line marked by the label and continues executing the rest of the commands from there.

    Note: You can also execute just the b command (without any label name). In this case, sed jumps to the end of the sed script file.

    +   t command

        The sed command t label branches the execution flow to the label only if the previous substitute command was successful. 

        That is, when the previous substitution was successful, sed jumps to the line marked by the label and continues executing the rest of the commands from there, otherwise it continues normal execution flow.

**regular expressions**

^ $ . * \\+ \? [ ] \\| \\{ \\} \b \num(back reference)

**Sed as an Interpreter**

```
#!/bin/sed -f
#!/bin/sed -nf
```


**Simulating Unix commands in sed**

```
cat filename
sed -n 'p' filename 
sed 'n' filename
sed 'N' filename

grep pattern filename
sed -n 's/pattern/&/p' filename
sed -n '/pattern/p' filename

grep -v pattern filename
sed -n '/pattern/ !p' filename

head -n filename
sed 'n+1,$ d' filename
sed -n '1,n p' filename
sed 'n q' filename
sed 'n+1 Q' filename
```

**some examples**

```
# 输出奇数行:
sed -n '1~2 p' input_file.txt
awk '{if(NR%2 == 1){print }}' input_file.txt

# 输出偶数行
sed -n '2~2 p' input_file.txt
awk '{if(NR%2 == 0){print }}' input_file

# combine n lines;
sed  -e ':a;N;s/\n/ /g;n~n ! ba'  input_file
```
