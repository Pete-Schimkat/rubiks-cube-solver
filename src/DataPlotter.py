import matplotlib.pyplot as plt
plt.rcParams.update({'font.size': 22})
import numpy as np
import os
import random
import math

result_path = ".\\main\\resources\\Results\\"


def count_words_in_strings(file_path):
    word_counts = []

    with open(file_path, 'r') as file:
        contents = file.read()
        strings = contents.split(',')

        for string in strings:
            string = string.strip()
            word_count = len(string.split())
            word_counts.append(str(word_count))

    return word_counts

def read_data_from(file_path): 
    data = []
    with open(file_path,"r") as file:
        for line in file:
            row = list(map(int, line.strip().split(',')))
            row = [val + 1 if val == 0 else val for val in row]
            data.append(row)
    return data


def plot_wordcount_file(weight,colormain,colordata):
    data = read_data_from(f"{result_path}\solutionLengths{weight}.txt")

    data = np.array(data) / (1)
    transposed_data = list(map(list, zip(*data)))
    x = np.arange(len(transposed_data[0])) 

    data1 = read_data_from(f"{result_path}\solutionLengths{3}.txt")

    data1 = np.array(data1) / (1)
    transposed_data1 = list(map(list, zip(*data1)))
    x1 = np.arange(len(transposed_data1[0])) 

    plt.figure(figsize=(10, 6))
    for line_data in transposed_data1:
        plt.plot(x1+3, line_data, color="lightgrey", linewidth=1,label="_nolegend_")

    x_equal_y = x + 3
    plt.plot(x_equal_y, x_equal_y, color=colormain, linewidth=1.5)

    average_line = np.mean(transposed_data, axis=0)
    plt.plot(x+3, average_line, color=colordata, linewidth=2)
    print(average_line)

    average_line1 = np.mean(transposed_data1, axis=0)
    print(average_line1)
    plt.plot(x1+3, average_line1, color="red", linewidth=2)



    plt.xlabel('Shortest Solution')
    plt.ylabel('Found Solution')
    if(isinstance(weight, int)):
        weight = weight
    else:
        weight = weight.replace("_",".")
    plt.title(f'Solution Length (weight = {weight})')
    plt.grid(axis="y")
    plt.xticks(x+3)
    plt.yticks(np.arange(int(min(average_line))-1, int(max(average_line))+8, 2))
    plt.legend(["average","optimal solution"], loc=2, frameon=True )


    plt.show()

def plot_two_averages(weight,color1,color2):
    data1 = read_data_from(f"{result_path}SevenEdge\\NoEdgePerm\ResultsWithWeight{weight}")
    data2 = read_data_from(f"{result_path}SevenEdge\\NoEdgePerm\ResultsWithWeight{weight}")

    data1 = np.array(data1) / (1000*1)
    transposed_data1 = list(map(list, zip(*data1)))
    x1 = np.arange(len(transposed_data1[0])) 

    data2 = np.array(data2) / (1000*1)
    transposed_data2 = list(map(list, zip(*data2)))
    x2 = np.arange(len(transposed_data2[0])) 

    

    for line_data in transposed_data1:
        plt.semilogy(x1+3, line_data, color='pink', linewidth=1,label='_nolegend_')

    for line_data in transposed_data2:
        plt.semilogy(x2+3, line_data, color='lightblue', linewidth=1,label='_nolegend_')

    np.set_printoptions(formatter={'float_kind':'{:25f}'.format})
    average_line1 = np.mean(transposed_data1, axis=0)
    print(np.mean(transposed_data1, axis=0))
    plt.semilogy(x1+3, average_line1, color=color1, linewidth=2)
    
    average_line2 = np.mean(transposed_data2, axis=0)
    print(np.mean(transposed_data2, axis=0))
    plt.semilogy(x2+3, average_line2, color=color2, linewidth=2)
    
    if(weight == 1): 
        title = "(unweighted)"
    else: 
        weight = weight.replace("_",".")
        title = f"(weight = {weight})"
    plt.xlabel('Solution length')
    plt.ylabel('Time in seconds')
    #plt.title(f'6 and 7 Edges {title}')
    plt.title("3 databases (6-edge, unweighted)")
    plt.grid(axis="y")
    plt.xticks(x2+3)
    plt.legend(["no EdgePerms","with EdgePerms"], loc=2, frameon=True )
    plt.show()

def plot_weights(color1,color2):
    
    data1 = read_data_from(f"{result_path}SevenEdge\\WithEdgePerm\\ResultsWithWeight{1}")
    data2 = read_data_from(f"{result_path}SevenEdge\\WithEdgePerm\\ResultsWithWeight1_5")
    data3 = read_data_from(f"{result_path}SevenEdge\\WithEdgePerm\\ResultsWithWeight{3}")

    data1 = np.array(data1) / (1000*1)
    transposed_data1 = list(map(list, zip(*data1)))
    x1 = np.arange(len(transposed_data1[0])) 

    data2 = np.array(data2) / (1000*1)
    transposed_data2 = list(map(list, zip(*data2)))
    x2 = np.arange(len(transposed_data2[0])) 

    data3 = np.array(data3) / (1000*1)
    transposed_data3 = list(map(list, zip(*data3)))
    x3 = np.arange(len(transposed_data3[0])) 
    

    #for line_data in transposed_data2:
     #   plt.semilogy(x2+3, line_data, color='lightblue', linewidth=1,label='_nolegend_')

    #for line_data in transposed_data3:
     #   plt.semilogy(x3+3, line_data, color='pink', linewidth=1,label='_nolegend_')

    np.set_printoptions(formatter={'float_kind':'{:25f}'.format})
    average_line1 = np.mean(transposed_data1, axis=0)
    print(np.mean(transposed_data1, axis=0))
    plt.semilogy(x1+3, average_line1, color="green", linewidth=2)
    
    average_line2 = np.mean(transposed_data2, axis=0)
    print(np.mean(transposed_data2, axis=0))
    plt.semilogy(x2+3, average_line2, color=color1, linewidth=2)
    
    average_line3 = np.mean(transposed_data3, axis=0)
    print(np.mean(transposed_data3, axis=0))
    plt.semilogy(x3+3, average_line3, color=color2, linewidth=2)

    plt.xlabel('Solution length')
    plt.ylabel('Time in seconds')
    #plt.title(f'6 and 7 Edges {title}')
    plt.title(" 7-edge database (weighted)")
    plt.grid(axis="y")
    plt.xticks(x2+3)
    plt.legend(["1","1,5","3"], loc=2, frameon=True )
    plt.show()


def plot_data(weight, color_average):
    data = read_data_from(f"{result_path}SevenEdge\\WithEdgePerm\ResultsWithWeight{weight}")
    data = np.array(data) / (1000*1)
    transposed_data = list(map(list, zip(*data)))
    x = np.arange(len(transposed_data[0])) 

    plt.figure(figsize=(10, 6))


    for line_data in transposed_data:
        plt.semilogy(x+3, line_data, color='lightgrey', linewidth=1,label='_nolegend_')

    average_line = np.mean(transposed_data, axis=0)
    plt.semilogy(x+3, average_line, color=color_average, linewidth=2)

    
    if(weight == 1): 
        title = "(unweighted)"
    else: 
        weight = weight.replace("_",".")
        title = f"(weight = {weight})"
    plt.xlabel('Solution depth')
    plt.ylabel('Time in seconds')
    plt.title(f'6 Edges {title}')
    plt.grid(axis="y")
    plt.xticks(x+3)
    plt.legend(["average"], loc=2, frameon=True )
    plt.show()

def count_words_to_file(weight): 
    word_counts = count_words_in_strings(f'{result_path}SevenEdge\\NoEdgePerm\ResultsWithWeight{weight}Solutions')
    output_file = f'solutionLengths{weight}.txt'

    with open(output_file, 'w') as file:
        for i in range(0, len(word_counts), 10):
            line = ','.join(word_counts[i:i+10])
            file.write(line + '\n')

def main():
    plot_data("1","blue")
    plot_data("1_5","green")
    plot_data("3","red")
    plot_wordcount_file("1_5","orange","blue")
    plot_wordcount_file("3","orange","red")
    plot_two_averages("1","red","blue")
    plot_weights("blue","red")



if __name__ == "__main__":
    main()

