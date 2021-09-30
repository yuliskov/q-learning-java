# q-learning-java
Reinforcement learning - Q learning in Java. 

Original code taken from here: http://technobium.com/reinforcement-learning-q-learning-java/

## Usage

The init() function loads R matrix from the file "resources\r_matrix.txt" and calculates corresponding Q matrix.

 
## Example output

```
States:    0   1   2   3   4   5   6   7   8   9
Possible states from 0 :[  -1   0  -1  -1  -1  -1  -1  -1  -1 100]
Possible states from 1 :[   0  -1   0  -1  -1  -1  -1  -1  -1  -1]
Possible states from 2 :[  -1   0  -1  -1  -1   0  -1  -1  -1  -1]
Possible states from 3 :[  -1  -1  -1  -1   0  -1   0  -1  -1  -1]
Possible states from 4 :[  -1  -1  -1   0  -1   0  -1  -1  -1  -1]
Possible states from 5 :[  -1  -1   0  -1   0  -1  -1  -1   0  -1]
Possible states from 6 :[  -1  -1  -1   0  -1  -1  -1   0  -1  -1]
Possible states from 7 :[  -1  -1  -1  -1  -1  -1   0  -1   0 100]
Possible states from 8 :[  -1  -1  -1  -1  -1   0  -1   0  -1  -1]
Possible states from 9 :[   0  -1  -1  -1  -1  -1  -1   0  -1 100]
Q matrix
From state 0:    0.00 153.90   0.00   0.00   0.00   0.00   0.00   0.00   0.00 190.00 
From state 1:  171.00   0.00 138.51   0.00   0.00   0.00   0.00   0.00   0.00   0.00 
From state 2:    0.00 153.90   0.00   0.00   0.00 138.51   0.00   0.00   0.00   0.00 
From state 3:    0.00   0.00   0.00   0.00 124.66   0.00 153.90   0.00   0.00   0.00 
From state 4:    0.00   0.00   0.00 138.51   0.00 138.51   0.00   0.00   0.00   0.00 
From state 5:    0.00   0.00 138.51   0.00 124.66   0.00   0.00   0.00 153.90   0.00 
From state 6:    0.00   0.00   0.00 138.51   0.00   0.00   0.00 171.00   0.00   0.00 
From state 7:    0.00   0.00   0.00   0.00   0.00   0.00 153.90   0.00 153.90 190.00 
From state 8:    0.00   0.00   0.00   0.00   0.00 138.51   0.00 171.00   0.00   0.00 
From state 9:    0.00   0.00   0.00   0.00   0.00   0.00   0.00   0.00   0.00 100.00 

Print paths
From state 0: 0-9
From state 1: 1-0-9
From state 2: 2-1-0-9
From state 3: 3-6-7-9
From state 4: 4-3-6-7-9
From state 5: 5-8-7-9
From state 6: 6-7-9
From state 7: 7-9
From state 8: 8-7-9
From state 9: 9

Print policy
From state 0 goto state 9
From state 1 goto state 0
From state 2 goto state 1
From state 3 goto state 6
From state 4 goto state 3
From state 5 goto state 8
From state 6 goto state 7
From state 7 goto state 9
From state 8 goto state 7
From state 9 goto state 9
```
