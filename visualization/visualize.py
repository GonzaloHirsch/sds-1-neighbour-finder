import matplotlib.pyplot as plt
import numpy

particle_focus = int(input("Particula para enfocar: "))

# Function to extract particle positions from the particle map
def get_particle_positions(particle_map):
    X = []
    Y = []
    for value in particle_map.values():
        X.append(value[0])
        Y.append(value[1])
    return X, Y

# Function to extract the neighbours positions and radius
def get_neighbour_particle_positions(neighbours, positions, properties, particle_focus):
    X = []
    Y = []
    R = []
    for value in neighbours[particle_focus]:
        X.append(positions[value][0])
        Y.append(positions[value][1])
        R.append(properties[value][0])
    return X, Y, R

# Function to extract the radius from
def get_particle_radius(particle_map):
    R = []
    for value in particle_map.values():
        R.append(value[0])
    return R

def generate_circles(X, Y, R):
    circles = []
    for index in range(len(X)):
        circles.append(plt.Circle((X[index], Y[index]), R[index], color='b', fill=False))
        index += 1
    return circles

def generate_neighbour_circles(X, Y, R, area_length):
    circles = []
    for index in range(len(X)):
        circles.append(plt.Circle((X[index], Y[index]), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        # Covers the top right case
        circles.append(plt.Circle((X[index] - area_length, Y[index] - area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index], Y[index] - area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index] - area_length, Y[index]), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        # Covers top left case
        circles.append(plt.Circle((X[index] + area_length, Y[index] - area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index], Y[index] - area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index] + area_length, Y[index]), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        # Covers bottom right case
        circles.append(plt.Circle((X[index] - area_length, Y[index] + area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index], Y[index] + area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index] - area_length, Y[index]), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        # Covers bottom left case
        circles.append(plt.Circle((X[index] + area_length, Y[index] + area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index], Y[index] + area_length), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        circles.append(plt.Circle((X[index] + area_length, Y[index]), R[index], facecolor='g', fill=True, edgecolor=NEIGHBOUR_BORDER_COLOR))
        index += 1
    return circles

def generate_interaction_radius_circles(x, y, r, area_length):
    circles = []
    circles.append(plt.Circle((x, y), r, fill=False, color='r'))
    # Covers top right case
    circles.append(plt.Circle((x - area_length, y - area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x, y - area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x - area_length, y), r, fill=False, color='r'))
    # Covers top left case
    circles.append(plt.Circle((x + area_length, y - area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x, y - area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x + area_length, y), r, fill=False, color='r'))
    # Covers bottom right case
    circles.append(plt.Circle((x - area_length, y + area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x, y + area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x - area_length, y), r, fill=False, color='r'))
    # Covers bottom left case
    circles.append(plt.Circle((x + area_length, y + area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x, y + area_length), r, fill=False, color='r'))
    circles.append(plt.Circle((x + area_length, y), r, fill=False, color='r'))
    return circles

def generate_focus_circles(x, y, r, area_length):
    circles = []
    circles.append(plt.Circle((x, y), r, color='r'))
    # Covers top right case
    circles.append(plt.Circle((x - area_length, y - area_length), r, color='r'))
    circles.append(plt.Circle((x, y - area_length), r, color='r'))
    circles.append(plt.Circle((x - area_length, y), r, color='r'))
    # Covers top left case
    circles.append(plt.Circle((x + area_length, y - area_length), r, color='r'))
    circles.append(plt.Circle((x, y - area_length), r, color='r'))
    circles.append(plt.Circle((x + area_length, y), r, color='r'))
    # Covers bottom right case
    circles.append(plt.Circle((x - area_length, y + area_length), r, color='r'))
    circles.append(plt.Circle((x, y + area_length), r, color='r'))
    circles.append(plt.Circle((x - area_length, y), r, color='r'))
    # Covers bottom left case
    circles.append(plt.Circle((x + area_length, y + area_length), r, color='r'))
    circles.append(plt.Circle((x, y + area_length), r, color='r'))
    circles.append(plt.Circle((x + area_length, y), r, color='r'))
    return circles

STATIC_FILE = "../static.txt"
DYNAMIC_FILE = "../dynamic.txt"
OUTPUT_FILE = "../output.txt"
NEIGHBOUR_BORDER_COLOR = "#204a08"

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
        static_properties[index - 3] = [ float(x) for x in line.rstrip("\n").split(" ")]
    index += 1

df = open(DYNAMIC_FILE, "r")

positions = {}

index = 0
for line in df:
    if index > 0:
        positions[index] = [float(x) for x in line.rstrip("\n").split(" ")]
    index += 1

of = open(OUTPUT_FILE, "r")

neighbours = {}

index = 1
for line in of:
    data = line.rstrip("\n").split(" ")
    if len(data) > 1:
        neighbours[index] = [int(x) for x in data[1:]]
    else:
        neighbours[index] = []
    index += 1

X, Y = get_particle_positions(positions)
X_neighbour, Y_neighbour, R_neighbour = get_neighbour_particle_positions(neighbours, positions, static_properties, particle_focus)
R = get_particle_radius(static_properties)
circles = generate_circles(X, Y, R)
neighbour_circles = generate_neighbour_circles(X_neighbour, Y_neighbour, R_neighbour, area_length)

# Add extra to the end border in order to get the last item included
ticks = numpy.arange(0, area_length + ((area_length * 0.5)/matrix_size), area_length/matrix_size)

fig, ax = plt.subplots(figsize=(7, 7))
ax.set_xlim((0, area_length))
ax.set_ylim((0, area_length))
plt.xticks(ticks)
plt.yticks(ticks)

for circle in circles:
    ax.add_artist(circle)

for circle in neighbour_circles:
    ax.add_artist(circle)

particle_focus_data = positions[particle_focus][0:2]
particle_focus_radius = float(static_properties[particle_focus][0])

interaction_radius_circles = generate_interaction_radius_circles(particle_focus_data[0], particle_focus_data[1], interaction_radius + particle_focus_radius, area_length)
for circle in interaction_radius_circles:
    ax.add_artist(circle)

generate_focus_circles = generate_focus_circles(particle_focus_data[0], particle_focus_data[1], particle_focus_radius, area_length)
for circle in generate_focus_circles:
    ax.add_artist(circle)
plt.grid()
plt.show()
