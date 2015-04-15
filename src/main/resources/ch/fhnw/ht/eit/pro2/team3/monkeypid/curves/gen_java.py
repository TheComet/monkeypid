#!/usr/bin/env python

if __name__ == '__main__':

    with open('out.txt', 'w') as f:
        f.write('double[][] array = {');
        strings = list()
        for line in open('example_step_response_pid', 'r'):
            strings.append('{' + ','.join([str(x) for x in line.split('\t')]) + '}')
        f.write(','.join(strings))

