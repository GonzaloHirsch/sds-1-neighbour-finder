from numpy import random

def generate_static_file(filename, particle_total, area_length, matrix_size, interaction_radius, particle_radius):
    f = open(filename, 'w')

    # Adding the particle total
    f.write('{}\n'.format(particle_total))

    # Adding area length
    f.write('{}\n'.format(area_length))

    # Adding area length
    f.write('{}\n'.format(matrix_size))

    # Adding area length
    f.write('{}\n'.format(interaction_radius))

    # Property not to be used, 0 as default
    prop = 0

    # Adding the particle radius and prop
    for i in range(particle_total):
        f.write('{} {}\n'.format(particle_radius, prop))

    f.close()

def generate_dynamic_file(filename, particle_total, area_length):
    f = open(filename, 'w')

    # We only work with time 0
    f.write('0\n')

    # Velocity we are not using
    vx, vy = 0, 0

    # Adding the randomly generated
    for i in range(particle_total):
        f.write('{} {} {} {}\n'.format(random.uniform(0, area_length), random.uniform(0, area_length), vx, vy))

    f.close()

def generate_files(particle_total, area_length, matrix_size, interaction_radius, particle_radius):
    generate_static_file('./static.txt', particle_total, area_length, matrix_size, interaction_radius, particle_radius)
    generate_dynamic_file('./dynamic.txt', particle_total, area_length)

particle_total = int(input("Cantidad de particulas: "))
area_length = float(input("Longitud del area de estudio: "))
matrix_size = int(input("Cantidad de celdas de la matrix: "))
interaction_radius = float(input("Radio de interacción: "))
particle_radius = float(input("Radio de las particulas: "))

generate_files(particle_total, area_length, matrix_size, interaction_radius, particle_radius)

