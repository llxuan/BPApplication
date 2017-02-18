# Handwritten Numeral Recognition System 
## Introduction
This project contains three part, one is Android application, one is ios application, the other is application of osx. 

The android app is developed by eclipse+android, API level is range from 16 to 23.

The ios and osx application are developed by QT(C++) Python 3.4 and OC.

The design of UI picture is as follows:

Android:

![Android UI](https://github.com/llxuan/Handwritten-Numeral-Recognition-System/blob/master/picture/pic1.png)
ios/osx：

![ios UI](https://github.com/llxuan/Handwritten-Numeral-Recognition-System/blob/master/picture/pic2.png)
![osx UI](https://github.com/llxuan/Handwritten-Numeral-Recognition-System/blob/master/picture/pic3.png)

The main function of this project is to recognize whatever digit you input. There are two different methods to input, you can draw digit in the white board directly, besides, you can also just put in the picture that contains a digit. But we didn't deal with the picture that draws a series of digits. After the input, the screen will show you a dialog to tell the answer. Of course, we can't gurantee the answer provided is always correct, nevertheless, the overall correct rate is over 95%, which is satisfing.

## Implementation
### Training Data
We used the MNIST database as our training and testing dataset, and changed the raw black and write picture into the zero-one text file, in order to simplify the training process.
### Algorithm
We used the BP Netural Network to training the weight between each layer. For the sake of training efficiency, we made some improvements on the traditional BP algorithm：

1.Importing momentum

Momentum lets the learning process change the study rate value effectively and  it's no longer a constant value, what's more the adjustment to the bottom of study rate in a average direction,avoiding a large swing. To sum up, the momentum plays the role of smoothing.

2.Self-adaptive-study-rate adjustment

In this way, the algorithm can be modified along the error performance surface by changing the learning rate. So that, the convergence of training output can be accelerated.

And they both have a good performance.
![performance](https://github.com/llxuan/Handwritten-Numeral-Recognition-System/blob/master/picture/pic4.png)
