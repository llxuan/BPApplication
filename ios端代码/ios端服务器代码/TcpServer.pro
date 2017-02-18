#-------------------------------------------------
#
# Project created by QtCreator 2013-06-06T14:14:26
#
#-------------------------------------------------

QT       += core gui
QT       += network
greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = TcpServer
TEMPLATE = app


SOURCES += main.cpp\
        tcpserver.cpp \
    tcpclientsocket.cpp \
    server.cpp

HEADERS  += tcpserver.h \
    tcpclientsocket.h \
    server.h
INCLUDEPATH += -I /Library/Frameworks/Python.framework/Versions/3.5/include/python3.5m
LIBS += -L /Library/Frameworks/Python.framework/Versions/3.5/lib/ -lpython3.5
