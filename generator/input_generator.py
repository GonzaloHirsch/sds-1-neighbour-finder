from numpy import random

def generate_static_file(filename, area_length, particle_total, particle_radius = 0.25):
    f = open(filename, 'w')

    # Adding the particle total
    f.write('{}\n'.format(particle_total))

    # Adding area length
    f.write('{}\n'.format(area_length))

    # Property not to be used, 0 as default
    prop = 0

    # Adding the particle radius and prop
    for i in range(particle_total):
        f.write('{} {}\n'.format(particle_radius, prop))

    f.close()

def generate_dynamic_file(filename, area_length, particle_total):
    f = open(filename, 'w')

    # We only work with time 0
    f.write('0\n')

    # Velocity we are not using
    vx, vy = 0, 0

    # Adding the randomly generated
    for i in range(particle_total):
        f.write('{} {} {} {}\n'.format(random.uniform(0, area_length), random.uniform(0, area_length), vx, vy))

    f.close()

def generate_files(index, area_length, particle_total):
    generate_static_file('../test_files/static-' + str(index) + '.txt', area_length, particle_total)
    generate_dynamic_file('../test_files/dynamic-' + str(index) + '.txt', area_length, particle_total)

particle_totals = [10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 2000]
area_length = 20
index = 0
for total in particle_totals:
    index += 1
    generate_files(index, area_length, total)
