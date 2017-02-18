#include "tcpclientsocket.h"
#include<QDebug>
TcpClientSocket::TcpClientSocket(QObject *parent)
{
    connect(this,SIGNAL(readyRead()),this,SLOT(dataReceived()));
    connect(this,SIGNAL(disconnected()),this,SLOT(slotDisconnected()));
    Py_Initialize();//调用python
    if(!Py_IsInitialized())qDebug()<<"error";
}

void TcpClientSocket::dataReceived()
{


    QDataStream in(this );
    in.setVersion(QDataStream::Qt_5_6);
    if( dataSize == 0 )//dataSize是一个自己设的qint32
    {
       if( bytesAvailable() < (sizeof(quint32)+sizeof(QString)) )
       {
            return;
       }
       in >> dataSize;//按顺序读出。先入先出顺序
       in>>filename;
    }
    if( dataSize > qint32(bytesAvailable()))//图片比较小，让它存在clientSocket的缓存中，直到大小满足关系，即文件内容都传输完毕后才读取出来。
    {
       return;
    }

     QByteArray array;
     QImage img;
     QString m;
     m="收到图片";
    emit updateClients(m,m.length());
     in>>array;
     img.loadFromData(array);
     dataSize=0;

    if(!img.isNull()){
        qDebug()<<"right"<<endl;
        QPixmap pix;
        pix=QPixmap::fromImage(img);
        pix.save("/Users/apple/Desktop/digit ios/a.png");
      int i= recognize();

       QString t=QString::number(i,10);

        write(t.toLatin1());
    }
    else {
        qDebug()<<"error"<<endl;
    }



}

void TcpClientSocket::slotDisconnected()
{
    emit disconnected(this->socketDescriptor());
}
int TcpClientSocket::recognize(){
    PyObject *pModule = NULL;
    PyObject *pFunc = NULL;
    PyObject *pReturn = NULL;
    PyObject *pyParams = PyTuple_New(1);
    PyObject *pyValue  = PyBytes_FromString("/Users/apple/Desktop/digit ios/a.png");
     // PyObject* pName = NULL;


    PyRun_SimpleString("import sys");
    PyRun_SimpleString("sys.path.append('/Users/apple/Desktop/digit ios')"); //python源文件所在的路径

     //pName = PyString_FromString("BPidentify");
    pModule = PyImport_ImportModule("BPidentify");
    if(!pModule)qDebug()<<"module error";
    pFunc = PyObject_GetAttrString(pModule,"identify");

     PyTuple_SetItem(pyParams,0,pyValue);
    pReturn = PyEval_CallObject(pFunc, pyParams);
   int i= PyLong_AsLong(pReturn);
   return i;
}
