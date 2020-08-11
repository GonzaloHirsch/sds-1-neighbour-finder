#!/bin/bash

for M in 2 5 9 13
do
    ./generator/statistics_generator.sh 100 $M
    ./generator/statistics_generator.sh 250 $M
    ./generator/statistics_generator.sh 500 $M
    ./generator/statistics_generator.sh 750 $M
    ./generator/statistics_generator.sh 1000 $M
    ./generator/statistics_generator.sh 1250 $M
    ./generator/statistics_generator.sh 1500 $M
    ./generator/statistics_generator.sh 1750 $M
    ./generator/statistics_generator.sh 2000 $M
done