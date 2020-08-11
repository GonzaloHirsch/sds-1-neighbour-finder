#!/bin/bash

particle_radius="0.25";
interraction_radius="1";
area_length="20";

destdir=generator/variables_generator

if [ -f "$destdir" ]
then
    echo "$1" > "$destdir"
    echo "$area_length" >> "$destdir"
    echo "$2" >> "$destdir"
    echo "$interraction_radius" >> "$destdir"
    echo "$particle_radius" >> "$destdir"
fi

for value in {1..4}
do
    cat generator/variables_generator | python generator/input_generator.py
    for num in {1..10}
    do
      java -jar ./target/sds-tp1-1.0-SNAPSHOT-jar-with-dependencies.jar -sf ./static.txt -df ./dynamic.txt -pb -bf
    done
done
echo All done