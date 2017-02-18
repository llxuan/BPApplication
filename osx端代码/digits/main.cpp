#include "mainwindow.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.setFixedSize(803, 549);
    w.setWindowFlags(Qt::WindowCloseButtonHint);
    //w.setWindowTitle("图片识别");
    w.setWindowFlags(Qt::FramelessWindowHint );//| Qt::WindowStaysOnTopHint);
    w.show();

    return a.exec();
}
