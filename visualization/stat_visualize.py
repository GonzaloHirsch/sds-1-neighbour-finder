import matplotlib.pyplot as plt
import statistics
import itertools
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


STAT_FILE = "./statistics.txt"

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

for method in method_stats:
    plt.clf()

    # Set the x axis label
    plt.xlabel('Number of Particles')
    # Set the y axis label
    plt.ylabel('Average execution time (ms)')

    #Plotting every M line for a given method
    for M in method_stats[method]:
        # Set a title of the current graph.
        plt.title('Execution time based on particle count using ' + method + ' and periodic borders')

        #Retrieving the data for the given M
        particles, times, stds = pointsGivenMatrixSize(method_stats[method][M])

        #Sorting the data so the line is correctly drawn
        particles, times, stds = zip(*sorted(zip(particles, times, stds)))
        #Plotting the line
        label = 'M = ' + str(M)
        plt.plot(particles, times, label=label)

        #Labelling the lines with the M value
        if method == 'CMI':
            plt.legend()

        plt.errorbar(particles, times, yerr=stds, fmt='o', color='black',
                     ecolor='lightgray', elinewidth=3, capsize=0);

    plt.savefig(method + '.png')