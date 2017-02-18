
# coding=utf8
from PIL import Image
from numpy import *
import os
import string

#一次性读取所有训练样本
def readTrainData(dirname):
    pathDir =  os.listdir(dirname)
    dataInput=[]
    aimInput=[]
    #读取指定目录的所有文件——训练样本集——已经处理过的txt
    for allDir in pathDir:
        child = os.path.join('%s%s' % (dirname, allDir))
        inputVect = zeros((1,784))  #输入特征向量
        desirVect = zeros((1,10))   #标准输出
        fr = open(child,'r')
        for i in range(28):
            lineStr = fr.readline()
            for j in range(28):
                inputVect[0,28*i+j] = int(lineStr[j])          
        lineStr = fr.readline()
        for j in range(10):
            desirVect[0,j] = int(lineStr[j])  
        #inputVect=mat(inputVect)
        #desirVect=mat(desirVect)
        fr.close()
        dataInput.append(inputVect)
        aimInput.append(desirVect)
    print("duwanle")
    return dataInput,aimInput

#初始化权值矩阵
def initialWeight(dimension,hide,output):
    W1 = zeros((hide,dimension)) #隐层对输入层的权值
    W2 = zeros((output,hide))    #输出层对隐层的权值
    #将权值随机初始化
    for i in range(hide):
        for j in range(dimension):
            W1[i][j]=random.uniform(-2.4/dimension,2.4/dimension)
    for i in range(output):
        for j in range(hide):
            W2[i][j]=random.uniform(-2.4/hide,2.4/hide)
    #从txt文件中读取权值
    
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

#读取指定目录的所有文件——训练样本集
def study(dirname):
    hide = 512
    errorSum=1000000.0                                 #定义一次训练完所有样本的误差能量
    error=0.0                                          #局部误差
    study_step=0.01                                    #学习步长
    W1pre = zeros((hide,784))                          #记录前一次的权值
    W2pre = zeros((10,hide))                            
    W1ch = zeros((hide,784))                           #记录权值改变量
    W2ch = zeros((10,hide))                            
    #初始化权值矩阵W,赋予较小的随机非零值
    W1,W2=initialWeight(784,hide,10)                   #隐层设为512个节点
    #print("权值矩阵：",W1)
    #print(W2)
    #初始化训练数据
    trainData,desireData = readTrainData(dirname)
    #flag为决定强制退出学习的开关
    fileF=open('/Users/apple/Desktop/digits recognition/flag.txt','r')
    flag=fileF.read()
    fileF.close()
    #对所有训练样本循环调整权值
    while errorSum>600 :  
        count=0        
        for inputVect in trainData:                     #输入训练集 1*784
            #正向计算工作信号和误差能量
            
            #print("输入训练集：",inputVect)
            #print("目标：",desirVect)
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
            desirVect=desireData[count]
            errorVect=output-desirVect                  #计算误差向量 1*10
            #print("误差：",errorVect)
            error=error+errorEnerge(errorVect)                #计算能量误差值
            #从输出层回退，修改各个权值
            #计算输出层的局部梯度向量
            localGranOut=errorVect.T*sigmoidDervation(outNet)#计算输出层所有神经元的局部梯度向量，矩阵对应元素相乘，得到的为10*1的矩阵
            #print("输出层到隐层局部梯度向量：",localGranOut)
            #计算输出层到隐层的误差能量对连接权值的求导
            GranOut=dot(localGranOut,hideOut.T)         #计算输出层所有神经元权向量10*1 1*512=>10*512  
            #print("输出层到隐层误差能量对连接权值求导：",GranOut)
            #计算隐层到输入层的局部梯度向量
            localGranHide=dot(localGranOut.T,W2)*sigmoidDervation(hideNet.T)#1*512的矩阵
            #print("隐层到输入层局部梯度向量：",localGranHide)
            #计算隐层到输入层的误差能量对连权值的求导
            GranHide=dot(localGranHide.T,inputOut.T)      #512*1 1*784=>512*784   
            #print("隐层到输入层误差能量对连接权值求导：",GranHide)
            
        
            #调整隐层对输出层的权值矩阵————优化！！
            #在每一个权值的变化上加一项正比于前次权值变化量的值，并根据反向传播法来产生新的权值变化
            #附有动量因子的权值调整公式，动量因子取0.95
            #计算W(t)-W(t-1)
            W1ch=W1-W1pre
            W2ch=W2-W2pre
            #跟新W(t-1)
            W1pre=W1
            W2pre=W2
            #计算W(t+1)=Mc(W(t)-W(t-1))-(1-Mc)*study_step*Gran
            W2=W2+0.05*W2ch-0.95*study_step*GranOut                          
            #print("新权值W2：",W2)
            #调整隐层对输入层的权值向量
            W1=W1+0.05*W1ch-0.95*study_step*GranHide
            #print("新权值W1：",W1)
            count=count+1              #学习次数加一        
            if(count%2000==0):
                print("误差能量：",error,"学习次数：",count)
                fileW1=open('/Users/apple/Desktop/digits recognition/tmp/W1.txt','w')
                fileW2=open('/Users/apple/Desktop/digits recognition/tmp/W2.txt','w')
                shapeW1=W1.shape
                shapeW2=W2.shape
                for i in range(shapeW1[0]):
                    for j in range(shapeW1[1]):
                        fileW1.write(str(W1[i][j])+'\t')
                for i in range(shapeW2[0]):
                    for j in range(shapeW2[1]):
                        fileW2.write(str(W2[i][j])+'\t')
                fileW1.close()
                fileW2.close()                
            
            
            
            #控制开关，随时控制程序是否继续进行
            fileF=open('/Users/apple/Desktop/digits recognition/flag.txt','r')
            flag=fileF.read()
            fileF.close()            
            if(flag!='1'):
                break;
        #优化！自适应学习速率
        if(errorSum>error):                             #若总的误差能量下降，说明调整有效，加大学习速率
            study_step=1.2*study_step
        elif(errorSum<error):                           #若总的误差能量下降，说明调整有效，减小学习速率
            study_step=0.8*study_step
        errorSum=error
        error=0
                                 
    #将学习后的权值记录到txt文件中
    fileW1=open('/Users/apple/Desktop/digits recognition/tmp/W1.txt','w')
    fileW2=open('/Users/apple/Desktop/digits recognition/tmp/W2.txt','w')
    shapeW1=W1.shape
    shapeW2=W2.shape
    for i in range(shapeW1[0]):
        for j in range(shapeW1[1]):
            fileW1.write(str(W1[i][j])+'\t')
    for i in range(shapeW2[0]):
        for j in range(shapeW2[1]):
            fileW2.write(str(W2[i][j])+'\t')
    fileW1.close()
    fileW2.close()
    return 1                                            #表示学习成功
            

