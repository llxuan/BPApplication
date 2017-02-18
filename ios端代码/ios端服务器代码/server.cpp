#include "server.h"
#include<QDebug>
Server::Server(QObject *parent,int port)
    :QTcpServer(parent)
{

    listen(QHostAddress::Any,port);
}

void Server::incomingConnection(qintptr socketDescriptor)
{   qDebug()<<socketDescriptor;
    TcpClientSocket *tcpClientSocket=new TcpClientSocket(this);
    connect(tcpClientSocket,SIGNAL(updateClients(QString,int)),this,SLOT(updateClients(QString,int)));
    connect(tcpClientSocket,SIGNAL(disconnected(int)),this,SLOT(slotDisconnected(int)));

    tcpClientSocket->setSocketDescriptor(socketDescriptor);

    tcpClientSocketList.append(tcpClientSocket);
    updateClients("a connect",9);
}

void Server::updateClients(QString msg,int length)
{
    emit updateServer(msg,length);
    /*for(int i=0;i<tcpClientSocketList.count();i++)
    {
        QTcpSocket *item = tcpClientSocketList.at(i);
        if(item->write(msg.toLatin1(),length)!=length)
        {
            continue;
        }
    }*/
}

void Server::slotDisconnected(int descriptor)
{
  updateClients("a disconnect",12);
}
