#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "paintarea.h"
MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    setMouseTracking(true);
    ui->setupUi(this);
    ui->myanimation->hide();
    movie= new QMovie(":/re/scan3.gif");
    blackboard=new QMovie(":/re/blackboard.gif");
    ui->myanimation->setMovie(movie);
    ui->resultbackground->setMovie(blackboard);
    blackboard->start();
    //movie->start();
    //显示字体
    QFont ft;
    ft.setPointSize(64);
    ui->result->setFont(ft);
    ui->result->setAlignment(Qt::AlignCenter|Qt::AlignTop);

    FutureWatcher=new QFutureWatcher<void>;

   connect(FutureWatcher,SIGNAL(finished()),this,SLOT(finished_slot()));

    Py_Initialize();//调用python
    if(!Py_IsInitialized())qDebug()<<"error";

    QPalette pa;
    pa.setColor(QPalette::WindowText,Qt::white);
    ui->result->setPalette(pa);
}

MainWindow::~MainWindow()
{
    delete ui;
}
void MainWindow::finished_slot(){

}

void MainWindow::on_recognition_clicked()
{
    ui->myanimation->show();

   ui->paint->savepix();


   PyObject *pModule = NULL;
   PyObject *pFunc = NULL;
   PyObject *pReturn = NULL;
   PyObject *pyParams = PyTuple_New(1);
   PyObject *pyValue  = PyBytes_FromString("/Users/apple/Desktop/digits recognition/digits/b.png");//画板图片保存目录
    // PyObject* pName = NULL;


   PyRun_SimpleString("import sys");
   PyRun_SimpleString("sys.path.append('/Users/apple/Desktop/digits recognition')"); //python源文件所在的路径

    //pName = PyString_FromString("BPidentify");
   pModule = PyImport_ImportModule("BPidentify");
   if(!pModule)qDebug()<<"module error";
   pFunc = PyObject_GetAttrString(pModule,"identify");

    PyTuple_SetItem(pyParams,0,pyValue);
   pReturn = PyEval_CallObject(pFunc, pyParams);
  int i= PyLong_AsLong(pReturn);
  qDebug()<<i;
  QString t=QString::number(i,10);
  //Py_Finalize();//有这个只能运行一次。。不能要

  ui->result->setText(t);

}

void MainWindow::on_clear_clicked()
{
    ui->paint->clearpix();
    ui->myanimation->hide();
}

void MainWindow::on_selectpix_clicked()
{
    QString file_full, file_name, file_path;
    QFileInfo fi;
    QFileDialog *fd=new QFileDialog;
    //fd->setFilter();
    file_full = fd->getOpenFileName(this);
    //qDebug()<<file_full;

    if(!file_full.isEmpty()){
    fi = QFileInfo(file_full);
    if(fi.suffix().toLower()=="png"||fi.suffix().toLower()=="jpg"){
    file_name = fi.fileName();
    file_path = fi.absolutePath();
    //ui->path->setText(file_path+"/"+file_name);
    ui->paint->showimage(file_full);

    }
    else QMessageBox::about(NULL,"error","请选择图片文件");
          }


}

void MainWindow::on_learning_clicked()
{   if(FutureWatcher->isFinished()){
    QFileDialog*fd=new QFileDialog;
    file_path=fd->getExistingDirectory();
    if(!file_path.isEmpty()){
    //qDebug()<<file_path;
    ProgressDialog=new QProgressDialog;

    connect(FutureWatcher,SIGNAL(finished()),ProgressDialog,SLOT(cancel()));

     future = QtConcurrent::run(this, &MainWindow::learnthread);
    FutureWatcher->setFuture(future);
    /*ProgressDialog->setMinimum(0);
     ProgressDialog->setMaximum(0);
     ProgressDialog->setWindowModality(Qt::WindowModal);
     ProgressDialog->setCancelButtonText("隐藏");
    ProgressDialog->exec();*/
    QFile f("/Users/apple/Desktop/digits recognition/flag.txt");//flag文件
    f.open(QIODevice::WriteOnly | QIODevice::Text);


    QTextStream txtoutput(&f);
    txtoutput<<"1"<<endl;

    ui->learning->setText("停止学习");
    }

    }
    else{
        QMessageBox message(QMessageBox::NoIcon,"停止","确定要停止学习吗?", QMessageBox::No | QMessageBox::Yes, NULL);
        if(message.exec() == QMessageBox::Yes)
        {
            QFile f("/Users/apple/Desktop/digits recognition/flag.txt");//flag文件
            f.open(QIODevice::WriteOnly | QIODevice::Text);


            QTextStream txtoutput(&f);
            txtoutput<<"0"<<endl;

        }


    }



}
void MainWindow::learnthread(){
    PyObject *pModule = NULL;
    PyObject *pFunc = NULL;
    PyObject *pReturn = NULL;
    PyObject *pyParams = PyTuple_New(1);


    std::string b=file_path.toStdString();
    const char*a=b.data();
    qDebug()<<file_path;
    PyObject *pyValue  = PyBytes_FromString("/Users/apple/Desktop/digits recognition/traindigits");//PyBytes_FromString(a);
     // PyObject* pName = NULL;


    PyRun_SimpleString("import sys");
    PyRun_SimpleString("sys.path.append('/Users/apple/Desktop/digits recognition')"); //python源文件所在的路径

     //pName = PyString_FromString("BPidentify");
    pModule = PyImport_ImportModule("BPstudy");
    if(!pModule)qDebug()<<"module error";
    pFunc = PyObject_GetAttrString(pModule,"study");

     PyTuple_SetItem(pyParams,0,pyValue);
    pReturn = PyEval_CallObject(pFunc, pyParams);
    int i= PyLong_AsLong(pReturn);
    qDebug()<<i;

    //若人为终止，则不替换权值文件，若是学习完成，则替换

    QFile f("/Users/apple/Desktop/digits recognition/flag.txt");//flag文件
    QString flag;
    f.open(QIODevice::ReadOnly | QIODevice::Text);


    QTextStream txtinput(&f);
    flag=txtinput.readLine();
    f.close();
    qDebug()<<flag;

    while(1&&flag=="1"){
        f.open(QIODevice::ReadOnly | QIODevice::Text);


        QTextStream txtinput(&f);
        flag=txtinput.readLine();
        f.close();
    }

    if(flag=="1"){
   QFile::remove("/Users/apple/Desktop/digits recognition/W1.txt");//权值文件
   QFile::remove("/Users/apple/Desktop/digits recognition/W2.txt");
   QFile::copy("/Users/apple/Desktop/digits recognition/tmp/W1.txt", "/Users/apple/Desktop/digits recognition/W1.txt");
   QFile::copy("/Users/apple/Desktop/digits recognition/tmp/W2.txt", "/Users/apple/Desktop/digits recognition/W2.txt");

    }





    ui->learning->setText("学习");
    file_path.clear();
}
//无边框窗口的拖动
void MainWindow:: mousePressEvent (QMouseEvent *event){
    press = true;
    startPos=event->pos();
}

void MainWindow::mouseMoveEvent (QMouseEvent *event){
    if(press)
    {
     QPoint endPos = event->globalPos();
    //在移动的过程中，坐标一直在变，只有当鼠标松开的时候,press变为false,坐标确定，开始移动窗口
     this->move(endPos-startPos);
    }
}

void MainWindow::mouseReleaseEvent(QMouseEvent *event){
    press=false;
}
