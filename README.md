# Simulacion de Sistemas - TP1

## File Generation

For the generation fo random input **pip** needs to be installed by using:
```
pip install numpy
```
or **pip3** if using OSx
```
pip3 install numpy
```

Being inside the _generator_ folder, run the following command to create the random input files:
```
python input_generator.py
```
or **python3** if using OSx
```
python3 input_generator.py
```

The generated files are **static.txt** and **dynamic.txt**, which are located at the root of the project.

The contents for the **static.txt** are:
```
particle_total
area_length
matrix_size
interaction_radius
radius_1 property_1
...
radius_n property_n
```

The contents for the **dynamic.txt** are:
```
iteration
pos_x_1 pos_y_1 vel_x_1 vel_y_1
...
pos_x_n pos_y_n vel_x_n vel_y_n
```

## Simulation
Run the command to package the project:
```
mvn clean package
```
Run the command to execute the algorithm(optional flags for _Brute Force_ (-bf) and _Periodic Borders_ (-pb) can be used):
```
java -jar ./target/sds-tp1-1.0-SNAPSHOT-jar-with-dependencies.jar -sf ./static.txt -df ./dynamic.txt
```
This will generate a file **output.txt** in the root directory of the project with all the neighbours

## Visualization
The visualization can be done in Octave or python

### Octave
Using the Octave interpreter, and being in the _Visualization_ directory the visualizing function can be used (arguments are path to static file, path to dynamic file and particle to be studied):
```
visualize("../static.txt", "../dynamic.txt", "../output.txt", 65)
```

### Python

For the generation fo random input **matplotlib** needs to be installed by using:
```
pip install matplotlib
```
or **matplotlib** if using OSx
```
pip3 install matplotlib
```

Being inside the _visualization_ folder, run the following command to create the random input files:
```
python visualize.py
```
or **python3** if using OSx
```
python3 visualize.py
```