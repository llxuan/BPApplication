# -*- coding: utf-8 -*-  
#coding=utf-8 

from numpy import *
from PIL import Image
import os
import string

def image_to_tuple(imgFile, noise=0.005, effect=0.01, threshold=127, saveFile=None):
    img = Image.open(imgFile)
    # 大图片进行缩略化,保证待处理图片不超过1M
    if img.width > 1024 or img.height > 1024:
        if img.width > img.height:
            new_width = 1024
            new_height = new_width / img.width * img.height
        else:
            new_height = 1024
            new_width = new_height / img.height * img.width
        img.thumbnail((new_width, new_height), Image.ANTIALIAS)
    # 灰度处理
    gray = img.convert('L')
    # 二值处理 
    table = [int(i >= threshold) for i in range(256)]
    two = gray.point(table, '1')
    # 缩小有效区域（优化版 ）
    bound = getBound(two, noise)

    left, up, right, down = bound    
    part = two.crop((left, up, right, down))
    if down - up > right - left:
        new_img = Image.new("1", (int(down-up), int(down-up)), 1)
        new_img.paste(part, (int(down-up-right+left)//2,0))
    else:
        new_img = Image.new("1", (int(right-left), int(right-left)), 1)
        new_img.paste(part, (0,int(right-left-down+up)//2))        
    if saveFile:
        new_img.save(saveFile)
    # 28x28格子划分,统计每个格子中的有效像素点
    result = []
    length, extent = new_img.width / 28, new_img.height / 28
    for i in range(28):
        for j in range(28):
            u = int(i * extent)
            l = int(j * length)
            d = int((i + 1) * extent) if int((i + 1) * extent) < new_img.height else new_img.height
            r = int((j + 1) * length) if int((j + 1) * length) < new_img.width else new_img.width
            unit = new_img.crop((l, u, r, d))
            unit_list = unit.histogram()
            result.append(int(unit_list[0] / sum(unit_list[:2]) > effect))
    return tuple(result)

def getBound(source, noise):
    list_pos = []
    list_len = []   
    ud = True
    for i in range(source.height):
        unit = source.crop((0, i, source.width, i + 1))
        unit_list = unit.histogram()
        if (unit_list[0] / source.width) > noise:
            if ud:
                list_pos.append(i)
                list_len.append(1)
                ud = False
            else:
                list_len[len(list_len) - 1] += 1
        elif not ud:
            ud = True
    if not list_pos:
        raise ValueError("FIND NOTHING!!!")
    max_len = max(list_len)
    up = list_pos[list_len.index(max_len)]
    down = up + max_len
    
    list_pos.clear()
    list_len.clear()
    lr = True
    for i in range(source.width):
        unit = source.crop((i, up, i + 1, down))
        unit_list = unit.histogram()
        if (unit_list[0] / max_len) > noise:
            if lr:
                list_pos.append(i)
                list_len.append(1)
                lr = False
            else:
                list_len[len(list_len) - 1] += 1
        elif not lr:
            lr = True
    max_len = max(list_len)
    left = list_pos[list_len.index(max_len)]
    right = left + max_len
    
    return (left, up, right, down)

#将训练集变为二值化的txt
def pictureInupt(filename):
    t = image_to_tuple(filename, threshold=95, saveFile='/Users/apple/Desktop/digits recognition/transfer.png')
    fileP=open('/Users/apple/Desktop/digits recognition/data.txt','w')
    for i, j in enumerate(t):
        fileP.write(str(j))
        if not (i + 1) % 28:
            fileP.write('\n')
 
            
#读入文件中数字信息
def initialVector(filename):
    inputVect = zeros((1,784)) #输入特征向量
    fr = open(filename,'r')
    for i in range(28):
        lineStr = fr.readline()
        for j in range(28):
            inputVect[0,28*i+j] = int(lineStr[j])          
    lineStr = fr.readline()
    #inputVect=mat(inputVect)
    fr.close()
    return inputVect

#初始化权值矩阵
def initialWeight():
    W1 = zeros((512,784)) #隐层对输入层的权值
    W2 = zeros((10,512))    #输出层对隐层的权值    
    i=0
    j=0
    #从txt文件中读取权值
    fileW1=open('/Users/apple/Desktop/digits recognition/W1.txt','r')
    fileW2=open('/Users/apple/Desktop/digits recognition/W2.txt','r')
    for item in fileW1:
        item=item.split('\t')
        for data in item:
            W1[i][j]=float(data)
            j=j+1
            if(j==784):
                i=i+1
                j=0
                if(i==512):
                    break                   
    j=0
    i=0
    for item in fileW2:
        dataline=item.split('\t')
        for data in dataline:
            W2[i][j]=float(data)
            j=j+1
            if(j==512):
                i=i+1
                j=0
                if(i==10):
                    break                
    #print(W1,W2)    
    return W1,W2

#定义sigmoid函数
def sigmoid(x):
    return 1/(1+exp(-x))

#计算误差能量
def errorEnerge(errorVect):
    err=dot(errorVect,errorVect.T)/2
    return err

#对sigmoid函数求导
def sigmoidDervation(x):
    return exp(-x)/((1+exp(-x))*(1+exp(-x)))

#对新的输入进行分类
def classify(inputVect,W1,W2):
    result=[] 
    inputOut=sigmoid(inputVect.T)               #输入层输出784*1
    #print("输入层输出：",inputOut)
    hideNet=dot(W1,inputOut)                    #计算隐层神经元的净输入矩阵 512*784 784*1=>512*1
    #print("隐层输入：",hideNet)
    hideOut=sigmoid(hideNet)                    #计算隐层神经元的输出 512*1
    #print("隐层输出：",hideOut)
    outNet=dot(W2,hideOut)                      #计算输出层神经元的净输入矩阵10*512 512*1=>10*1
    #print("输出层输入：",outNet)
    output=sigmoid(outNet.T)                    #计算输出 1*10
    #print("输出层输出：",output)
    oShape=output.shape
    maxvalue=0
    value=0
    for i in range(oShape[0]):
        for j in range(oShape[1]):
            if maxvalue<output[i][j]:
                maxvalue=output[i][j]
                value=j
    return value

#封装好的识别函数
def identify(filename):
    pictureInupt(filename)
    W1,W2=initialWeight() 
    inputVect=initialVector('/Users/apple/Desktop/digits recognition/data.txt')
    value=classify(inputVect, W1, W2)
    return value


