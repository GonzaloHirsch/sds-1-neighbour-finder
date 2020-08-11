import matplotlib.pyplot as plt
import statistics
import numpy

def calculateTimeMean(times):
    return statistics.mean(times)

def calculateTimeSD(times, mean):
    if len(times) > 1:
        return statistics.stdev(times, mean)
    else:
        return 0

def pointsGivenMatrixSize(time_data):
    particles = []
    means = []
    stds = []

    for N in time_data:
        particles.append(N)
        means.append(time_data[N]['mean'])
        stds.append(time_data[N]['std'])

    return particles, means, stds


STAT_FILE = "../statistics.txt"

sf = open(STAT_FILE, "r")

method_stats = {}

for line in sf:
	data = line.rstrip("\n").split(" ")
	method = data[0]
	M = int(data[1])
	N = int(data[2])
	time = int(data[3])

	if not method in method_stats:
		method_stats[method] = {}

	if not M in method_stats[method]:
		method_stats[method][M] = {}

	if not N in method_stats[method][M]:
		method_stats[method][M][N] = []

	method_stats[method][M][N].append(time)

for method in method_stats:
    for M in method_stats[method]:
        for N in method_stats[method][M]:
            mean = calculateTimeMean(method_stats[method][M][N])
            std = calculateTimeSD(method_stats[method][M][N], mean)
            method_stats[method][M][N] = {'mean': mean, 'std': std}

print(method_stats)

particles, means, stds = pointsGivenMatrixSize(method_stats['BF'][10])

# Set the x axis label
plt.xlabel('Number of Particles')
# Set the y axis label
plt.ylabel('Average execution time (ms)')
# Set a title of the current axes.
plt.title('Execution time based on particle count')

plt.errorbar(particles, means, yerr=stds, fmt='o', color='black',
             ecolor='lightgray', elinewidth=3, capsize=0);
plt.show()