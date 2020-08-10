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

## Simulation
Run the command:
```
mvn clean package
```
```
java -jar ./target/sds-tp1-1.0-SNAPSHOT-jar-with-dependencies.jar -sf ./test_files/static-2000.txt -df ./test_files/dynamic-2000.txt -rc 1 -m 10
```

## Visualization
Run in Octave the command:
```
visualize("../test_files/static-2000.txt", "../test_files/dynamic-2000.txt", "../output.txt", 5, 65)
```