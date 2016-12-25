## NPStack - Genetic Box stacking Algorithm.

Completed to satisfy **COMP317-16B - Assignment 4** at **The University of Waikato**

This program is given a file containing a list of the 3 dimensions of some boxes. It then uses a Genetic Natural Selection like approach to find the tallest possible stack of these boxes that doesn't use the same box twice, allow overhanging or flush sides. (i.e. the width and depth must be smaller than that of the box underneath)

The Genetic Algorithm stops and outputs the best solution when a given number of candidate evaluations is reached. The Genetic Algorithm code has been implemented in a general sense in the GeneticEvolver package, in which an Interface is provided to allow our specific problem to be implemented in a seperate Solution package. The full javadoc html documentation can be found in the Documentation/HTML folder. If a webserver is pointed to this directory, the javadoc files serverd will all link to one another.

The program is run from the directory it is in, ('-classpath .' may need to be added to ensure it finds the User defined packages) and is given a path to a file containing the data of the boxes. In /davidwatson/Solution/TestFiles/ you will find 6 example test files named boxes01.txt. The second argument to the program is the number of candidate solutions to evaluate. The resulting number may be higher as this condition is only checked after each generational iteration and will stop once the number is greater than or equal to the specified argument.

## Usage: 
`$java NPStack davidwatson/Solution/TestFiles/boxes01.txt <num-candidate-evals> `

Also included in the packages is a program called BoxGenerator which creates random lists of boxes. An integer can be supplied to give a total number of boxes otherwise, or if given 0, a random number between two default values is used instead. To change the other parameters such as the max and min size of a boxes dimensions, the finalised data fields near the top of the class need to be changed and the code recompiled. More information about this program can be found in the javadoc documentation. 
