//#include "paintarea.h"
#include "mainwindow.h"
//pixmap接收绘图，drawpix显示到界面
paintarea::paintarea(QWidget *parent) : QWidget(parent)
{
  paintlock=0;
setAutoFillBackground(true);
//setPalette(QPalette(Qt::white));
pix= new QPixmap(521,451);
//img=new QPixmap(571,481);
pix->fill(Qt::white);
style=static_cast<int>(Qt::SolidLine);
weight=20;
}

paintarea::~paintarea(){

}
void paintarea:: mousePressEvent (QMouseEvent *event){
startPos=event->pos();
}
void paintarea::mouseMoveEvent (QMouseEvent *event){
    if(paintlock==0){
    QPainter*painter=new QPainter;
    QPen pen;
    pen.setStyle((Qt::PenStyle)style);
    pen.setWidth(weight);
    pen.setColor(Qt::black);
    pen.setCapStyle(Qt::RoundCap);
    painter->begin(pix);
    painter->setPen(pen);
    painter->drawLine(startPos,event->pos());
    painter->end();
    startPos=event->pos();
    update();}

}
void paintarea::paintEvent (QPaintEvent *event){

    QPainter painter(this);
    painter.drawPixmap(QPoint(0,0),*pix);


}

void paintarea:: savepix(){
   pix->save("/Users/apple/Desktop/digits recognition/digits/b.png");//图片保存目录
   paintlock=1;
}
void paintarea:: clearpix(){
    QPixmap*clearPix=new QPixmap(521,451);
    clearPix->fill(Qt::white);
    pix=clearPix;
    paintlock=0;
    imagepath.clear();
    update();
}
void paintarea::showimage(QString path){
    clearpix();
    imagepath=path;
    pix->load(path);

    *pix=pix->scaled(571,481,Qt::KeepAspectRatio);
    paintlock=1;
    //p.drawPixmap(QPoint(0,0),*img);
}
