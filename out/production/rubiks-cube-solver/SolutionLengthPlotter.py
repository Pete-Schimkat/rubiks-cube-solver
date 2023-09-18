import matplotlib.pyplot as plt
import numpy as np
import os
import random



def main():
    data = []
    i = 0
    max_values = []
    with open(".\wordcounts3.txt","r") as file: 
        for line in file:
            if(i < 0):
                i+=1
                continue 
            row = list(map(int, line.strip().split(',')))
            row = [val + 1 if val == 0 else val for val in row]
            data.append(row)

    data = np.array(data) / (1)


    transposed_data = list(map(list, zip(*data)))

    x = np.arange(len(transposed_data[0])) 

    plt.figure(figsize=(10, 6))
    for line_data in transposed_data:
        plt.plot(x+3, line_data, color='lightgrey', linewidth=1)

    x_equal_y = x + 3
    plt.plot(x_equal_y, x_equal_y, color='orange', linewidth=1.5)

    average_line = np.mean(transposed_data, axis=0)
    plt.plot(x+3, average_line, color='red', linewidth=2)


    plt.xlabel('Shortest Solution')
    plt.ylabel('Found Solution')
    plt.title('Solution Length (weight = 3)')
    plt.grid(axis="y")
    plt.xticks(x+3)
    plt.yticks(np.arange(int(min(average_line))-1, int(max(average_line))+2, 2))

    plt.show()


if __name__ == "__main__":
    main()

