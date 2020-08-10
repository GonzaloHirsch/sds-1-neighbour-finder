import matplotlib.pyplot as plt
import numpy

particle_focus = int(input("Particula para enfocar: "))

def get_particle_positions(particle_map):
    X = []
    Y = []
    for value in particle_map.values():
        X.append(float(value[0]))
        Y.append(float(value[1]))
    return X, Y

def get_particle_radius(particle_map):
    R = []
    for value in particle_map.values():
        R.append(float(value[0]))
    return R

def generate_circles(X, Y, R):
    circles = []
    for index in range(len(X)):
        circles.append(plt.Circle((X[index], Y[index]), R[index], color='b', fill=False))
        index += 1
    return circles

STATIC_FILE = "../static.txt"
DYNAMIC_FILE = "../dynamic.txt"
OUTPUT_FILE = "../output.txt"
SCALING_FACTOR = 65

sf = open(STATIC_FILE, "r")

static_properties = {}

index = 0
for line in sf:
    if index == 0:
        particle_count = int(line.rstrip("\n"))
    elif index == 1:
        area_length = float(line.rstrip("\n"))
    elif index == 2:
        matrix_size = int(line.rstrip("\n"))
    elif index == 3:
        interaction_radius = float(line.rstrip("\n"))
    else:
        static_properties[index - 3] = line.rstrip("\n").split(" ")
    index += 1

df = open(DYNAMIC_FILE, "r")

positions = {}

index = 0
for line in df:
    if index > 0:
        positions[index] = line.rstrip("\n").split(" ")
    index += 1

of = open(OUTPUT_FILE, "r")

neighbours = {}

index = 1
for line in of:
    data = line.rstrip("\n").split(" ")
    if len(data) > 1:
        neighbours[index] = data[1:]
    else:
        neighbours[index] = []

X, Y = get_particle_positions(positions)
R = get_particle_radius(static_properties)
circles = generate_circles(X, Y, R)
ticks = numpy.arange(0, area_length, area_length/matrix_size)

fig, ax = plt.subplots(figsize=(7, 7))
ax.set_xlim((0, area_length))
ax.set_ylim((0, area_length))
plt.xticks(ticks)
plt.yticks(ticks)
for circle in circles:
    ax.add_artist(circle)

particle_focus_data = positions[particle_focus][0:2]
particle_focus_radius = float(static_properties[particle_focus][0])

circle_interaction_radius = plt.Circle((float(particle_focus_data[0]), float(particle_focus_data[1])), interaction_radius, fill=False, color='r')
circle_focus_particle = plt.Circle((float(particle_focus_data[0]), float(particle_focus_data[1])), particle_focus_radius, color='r')
ax.add_artist(circle_interaction_radius)
ax.add_artist(circle_focus_particle)
plt.grid()
plt.show()
