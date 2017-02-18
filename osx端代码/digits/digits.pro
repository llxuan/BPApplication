#-------------------------------------------------
#
# Project created by QtCreator 2016-11-19T10:12:53
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = digits
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    paintarea.cpp

HEADERS  += mainwindow.h \
    paintarea.h

FORMS    += mainwindow.ui
INCLUDEPATH += -I /Library/Frameworks/Python.framework/Versions/3.5/include/python3.5m
LIBS += -L /Library/Frameworks/Python.framework/Versions/3.5/lib/ -lpython3.5

RESOURCES += \
    resourse.qrc


