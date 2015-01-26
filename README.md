# interpreter
Basic stack-based interpreter for APT Lecture.

Runs with one stack, and a program counter (`pc`), that starts at `0`.

### Running
Compile all three classes, then run Interpreter with one argument, your input file.

For example:
```java Interpreter myfile.swl```


### Instructions
| Name | Description          |
| ------------- | ----------- |
| `PRINT` | Prints the top element |
| `INT x` | Pushes integer `x` to stack |
| `ADD` | Adds the top two elements |
| `SUB` | Subtracts the top two elements |
| `SWAP` | Swaps the two top elements |
| `DUP` | Duplicates top element |
| `POP` | Pops top element from stack (effectively just removes it) |
| `JGE x` | If top element is `>=0`, jump to `x` |
| `JEQ x` | If top element is `==0`, jump to `x` |
| `CALL x` | Push `pc+1` to stack, jump to `x` |
| `RET` | Pop top element, jump to it |

Lines can also be preceded by labels, the format for this is `LABEL: INSTRUCTION`

*(note the space between the colon and the instruction)*

#### Example file
```
INT 100
L1: PRINT
INT 1
SUB
JGE L1
```
