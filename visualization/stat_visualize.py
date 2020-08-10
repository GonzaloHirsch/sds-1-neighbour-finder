import matplotlib.pyplot as plt
import statistics
import numpy

def calculateTimeMean(times):
    return statistics.mean(times)

def calculateTimeSD(times, mean):
    return statistics.stdev(times, mean)

STAT_FILE = "../statistics.txt"

sf = open(STAT_FILE, "r")

method_stats = {}

for line in sf:
	data = line.rstrip("\n").split(" ")

	if not data[0] in method_stats:
		method_stats[data[0]] = {}

	if not data[1] in method_stats[data[0]]:
		method_stats[data[0]][data[1]] = {}

	if not data[2] in method_stats[data[0]][data[1]]:
		method_stats[data[0]][data[1]][data[2]] = []

	method_stats[data[0]][data[1]][data[2]].append(int(data[3]))

for method in method_stats:
    for M in method_stats[method]:
        for N in method_stats[method][M]:
            if len(method_stats[method][M][N]) > 1:
                mean = calculateTimeMean(method_stats[method][M][N])
                std = calculateTimeSD(method_stats[method][M][N], mean)
                method_stats[method][M][N] = [mean, std]
            else:
                method_stats[method][M][N].append(0)

print(method_stats)

