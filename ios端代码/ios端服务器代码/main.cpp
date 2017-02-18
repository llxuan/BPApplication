#include "tcpserver.h"
#include <QApplication>
#include<QElapsedTimer>
int main(int argc, char *argv[])
{

    QApplication a(argc, argv);

    TcpServer w;
    w.show();
    
    return a.exec();
}
