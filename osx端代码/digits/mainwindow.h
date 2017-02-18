#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QWidget>
#include <QtGui>
#include <QMouseEvent>
#include <QPaintEvent>
#include <QResizeEvent>
#include <QColor>
#include <QPixmap>
#include <QPoint>
#include <QPainter>
#include <QPalette>
#include<QFileDialog>
#include<qDebug>
#include <QMessageBox>
#include<QImage>
#include<QMovie>
#include<Python.h>
#include<QObject>
#include<QProgressDialog>
#include<QThread>
#include <QtConcurrent/QtConcurrent>
#include<string>

#include<QFile>
#include<QTextStream>
namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    void mousePressEvent (QMouseEvent *event);
    void mouseMoveEvent (QMouseEvent *event);
    void mouseReleaseEvent(QMouseEvent *event);
    void learnthread();
private slots:


    void on_recognition_clicked();

    void on_clear_clicked();

    void on_selectpix_clicked();

    void on_learning_clicked();

    void finished_slot();


private:
    Ui::MainWindow *ui;
    bool press;
    QPoint startPos;
    QPoint endPos;
    QFutureWatcher<void>*FutureWatcher;
    QProgressDialog *ProgressDialog;
    QString file_path;
    QFuture<void> future;
    QMovie*movie;
    QMovie*blackboard;
   // int flag;//判断是学习完成还是终止了学习
};


class paintarea : public QWidget
{
    Q_OBJECT
public:
    explicit paintarea(QWidget *parent = 0);
    ~paintarea();
    //鼠标事件重定义
void mousePressEvent (QMouseEvent *event);
void mouseMoveEvent (QMouseEvent *event);
//重画事件重定义
void paintEvent (QPaintEvent *);
void savepix();
void clearpix();
void showimage(QString path);
signals:

private:
int paintlock;
QPoint startPos;
QPoint endPos;
QColor color;
QPixmap *pix;
QString imagepath;
//QImage *img;
int style;
int weight;
public slots:


};
#endif // MAINWINDOW_H
