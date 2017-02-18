/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.6.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStackedWidget>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QToolButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>
#include <paintarea.h>
#include <qcameraviewfinder.h>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralWidget;
    QGridLayout *gridLayout;
    QStackedWidget *stackedWidget;
    QWidget *page;
    QGridLayout *gridLayout_2;
    QGridLayout *gridLayout_4;
    paintarea *paint;
    QVBoxLayout *verticalLayout;
    QWidget *result;
    QGridLayout *gridLayout_6;
    QLabel *showresult;
    QPushButton *clearmap;
    QPushButton *recognition;
    QPushButton *pushButton_3;
    QWidget *page_3;
    QHBoxLayout *horizontalLayout_2;
    QCameraViewfinder *viewfinder;
    QPushButton *captureImage;
    QWidget *page_2;
    QGridLayout *gridLayout_5;
    QGridLayout *gridLayout_3;
    QLineEdit *serverIPLineEdit;
    QPushButton *pushButton;
    QLabel *label;
    QLabel *label_2;
    QLineEdit *portLineEdit;
    QListWidget *listWidget;
    QWidget *page_4;
    QVBoxLayout *verticalLayout_2;
    QListWidget *listWidget_2;
    QWidget *widget;
    QHBoxLayout *horizontalLayout;
    QToolButton *pushButton_6;
    QToolButton *pushButton_5;
    QToolButton *pushButton4;
    QToolButton *pushButton_2;
    QLineEdit *status;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;
    QButtonGroup *indexbutton;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QStringLiteral("MainWindow"));
        MainWindow->resize(960, 762);
        MainWindow->setStyleSheet(QStringLiteral("background:qlineargradient(spread:reflect, x1:0.711443, y1:0.546, x2:1, y2:0, stop:0 rgba(0, 0, 0, 255), stop:1 rgba(255, 255, 255, 255))"));
        centralWidget = new QWidget(MainWindow);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        gridLayout = new QGridLayout(centralWidget);
        gridLayout->setSpacing(6);
        gridLayout->setContentsMargins(11, 11, 11, 11);
        gridLayout->setObjectName(QStringLiteral("gridLayout"));
        stackedWidget = new QStackedWidget(centralWidget);
        stackedWidget->setObjectName(QStringLiteral("stackedWidget"));
        page = new QWidget();
        page->setObjectName(QStringLiteral("page"));
        gridLayout_2 = new QGridLayout(page);
        gridLayout_2->setSpacing(6);
        gridLayout_2->setContentsMargins(11, 11, 11, 11);
        gridLayout_2->setObjectName(QStringLiteral("gridLayout_2"));
        gridLayout_4 = new QGridLayout();
        gridLayout_4->setSpacing(6);
        gridLayout_4->setObjectName(QStringLiteral("gridLayout_4"));
        paint = new paintarea(page);
        paint->setObjectName(QStringLiteral("paint"));
        QSizePolicy sizePolicy(QSizePolicy::Preferred, QSizePolicy::Minimum);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(paint->sizePolicy().hasHeightForWidth());
        paint->setSizePolicy(sizePolicy);
        paint->setMinimumSize(QSize(0, 400));
        paint->setStyleSheet(QStringLiteral("background:transparent"));

        gridLayout_4->addWidget(paint, 0, 0, 1, 1);


        gridLayout_2->addLayout(gridLayout_4, 0, 0, 1, 1);

        verticalLayout = new QVBoxLayout();
        verticalLayout->setSpacing(6);
        verticalLayout->setObjectName(QStringLiteral("verticalLayout"));
        result = new QWidget(page);
        result->setObjectName(QStringLiteral("result"));
        QSizePolicy sizePolicy1(QSizePolicy::Preferred, QSizePolicy::Preferred);
        sizePolicy1.setHorizontalStretch(0);
        sizePolicy1.setVerticalStretch(0);
        sizePolicy1.setHeightForWidth(result->sizePolicy().hasHeightForWidth());
        result->setSizePolicy(sizePolicy1);
        result->setMinimumSize(QSize(200, 200));
        result->setStyleSheet(QLatin1String("background:transparent;\n"
"border-image:url(:/images/computer1.png)\n"
""));
        gridLayout_6 = new QGridLayout(result);
        gridLayout_6->setSpacing(6);
        gridLayout_6->setContentsMargins(11, 11, 11, 11);
        gridLayout_6->setObjectName(QStringLiteral("gridLayout_6"));
        showresult = new QLabel(result);
        showresult->setObjectName(QStringLiteral("showresult"));
        showresult->setStyleSheet(QLatin1String("border-image:none;\n"
"color:white"));

        gridLayout_6->addWidget(showresult, 1, 0, 1, 1);


        verticalLayout->addWidget(result);

        clearmap = new QPushButton(page);
        clearmap->setObjectName(QStringLiteral("clearmap"));
        QSizePolicy sizePolicy2(QSizePolicy::Minimum, QSizePolicy::Fixed);
        sizePolicy2.setHorizontalStretch(0);
        sizePolicy2.setVerticalStretch(0);
        sizePolicy2.setHeightForWidth(clearmap->sizePolicy().hasHeightForWidth());
        clearmap->setSizePolicy(sizePolicy2);
        clearmap->setMinimumSize(QSize(0, 70));
        clearmap->setStyleSheet(QStringLiteral("color:white"));

        verticalLayout->addWidget(clearmap);

        recognition = new QPushButton(page);
        recognition->setObjectName(QStringLiteral("recognition"));
        sizePolicy2.setHeightForWidth(recognition->sizePolicy().hasHeightForWidth());
        recognition->setSizePolicy(sizePolicy2);
        recognition->setMinimumSize(QSize(50, 70));
        recognition->setStyleSheet(QStringLiteral("color:white"));

        verticalLayout->addWidget(recognition);

        pushButton_3 = new QPushButton(page);
        pushButton_3->setObjectName(QStringLiteral("pushButton_3"));
        pushButton_3->setMinimumSize(QSize(0, 70));
        pushButton_3->setStyleSheet(QStringLiteral("color:white"));

        verticalLayout->addWidget(pushButton_3);


        gridLayout_2->addLayout(verticalLayout, 0, 1, 1, 1);

        stackedWidget->addWidget(page);
        page_3 = new QWidget();
        page_3->setObjectName(QStringLiteral("page_3"));
        horizontalLayout_2 = new QHBoxLayout(page_3);
        horizontalLayout_2->setSpacing(6);
        horizontalLayout_2->setContentsMargins(11, 11, 11, 11);
        horizontalLayout_2->setObjectName(QStringLiteral("horizontalLayout_2"));
        viewfinder = new QCameraViewfinder(page_3);
        viewfinder->setObjectName(QStringLiteral("viewfinder"));
        sizePolicy1.setHeightForWidth(viewfinder->sizePolicy().hasHeightForWidth());
        viewfinder->setSizePolicy(sizePolicy1);
        viewfinder->setMinimumSize(QSize(0, 0));

        horizontalLayout_2->addWidget(viewfinder);

        captureImage = new QPushButton(page_3);
        captureImage->setObjectName(QStringLiteral("captureImage"));
        QSizePolicy sizePolicy3(QSizePolicy::Fixed, QSizePolicy::Preferred);
        sizePolicy3.setHorizontalStretch(0);
        sizePolicy3.setVerticalStretch(0);
        sizePolicy3.setHeightForWidth(captureImage->sizePolicy().hasHeightForWidth());
        captureImage->setSizePolicy(sizePolicy3);
        captureImage->setMinimumSize(QSize(0, 100));
        captureImage->setStyleSheet(QStringLiteral("color:white"));

        horizontalLayout_2->addWidget(captureImage);

        stackedWidget->addWidget(page_3);
        page_2 = new QWidget();
        page_2->setObjectName(QStringLiteral("page_2"));
        gridLayout_5 = new QGridLayout(page_2);
        gridLayout_5->setSpacing(6);
        gridLayout_5->setContentsMargins(11, 11, 11, 11);
        gridLayout_5->setObjectName(QStringLiteral("gridLayout_5"));
        gridLayout_3 = new QGridLayout();
        gridLayout_3->setSpacing(6);
        gridLayout_3->setObjectName(QStringLiteral("gridLayout_3"));
        serverIPLineEdit = new QLineEdit(page_2);
        serverIPLineEdit->setObjectName(QStringLiteral("serverIPLineEdit"));
        serverIPLineEdit->setStyleSheet(QLatin1String("background:transparent;\n"
"color:white"));

        gridLayout_3->addWidget(serverIPLineEdit, 0, 1, 1, 1);

        pushButton = new QPushButton(page_2);
        pushButton->setObjectName(QStringLiteral("pushButton"));
        pushButton->setStyleSheet(QStringLiteral("color:white"));

        gridLayout_3->addWidget(pushButton, 2, 0, 1, 2);

        label = new QLabel(page_2);
        label->setObjectName(QStringLiteral("label"));
        label->setStyleSheet(QStringLiteral("color:white"));

        gridLayout_3->addWidget(label, 0, 0, 1, 1);

        label_2 = new QLabel(page_2);
        label_2->setObjectName(QStringLiteral("label_2"));
        label_2->setStyleSheet(QStringLiteral("color:white"));

        gridLayout_3->addWidget(label_2, 1, 0, 1, 1);

        portLineEdit = new QLineEdit(page_2);
        portLineEdit->setObjectName(QStringLiteral("portLineEdit"));
        portLineEdit->setStyleSheet(QLatin1String("background:transparent;\n"
"color:white"));

        gridLayout_3->addWidget(portLineEdit, 1, 1, 1, 1);

        listWidget = new QListWidget(page_2);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        new QListWidgetItem(listWidget);
        listWidget->setObjectName(QStringLiteral("listWidget"));
        listWidget->setStyleSheet(QStringLiteral("color:white"));

        gridLayout_3->addWidget(listWidget, 3, 0, 1, 2);


        gridLayout_5->addLayout(gridLayout_3, 1, 0, 1, 1);

        stackedWidget->addWidget(page_2);
        page_4 = new QWidget();
        page_4->setObjectName(QStringLiteral("page_4"));
        verticalLayout_2 = new QVBoxLayout(page_4);
        verticalLayout_2->setSpacing(6);
        verticalLayout_2->setContentsMargins(11, 11, 11, 11);
        verticalLayout_2->setObjectName(QStringLiteral("verticalLayout_2"));
        listWidget_2 = new QListWidget(page_4);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        new QListWidgetItem(listWidget_2);
        listWidget_2->setObjectName(QStringLiteral("listWidget_2"));
        listWidget_2->setStyleSheet(QStringLiteral("color:white"));

        verticalLayout_2->addWidget(listWidget_2);

        stackedWidget->addWidget(page_4);

        gridLayout->addWidget(stackedWidget, 1, 0, 1, 1);

        widget = new QWidget(centralWidget);
        widget->setObjectName(QStringLiteral("widget"));
        widget->setStyleSheet(QStringLiteral("background:rgb(64, 64, 64)"));
        horizontalLayout = new QHBoxLayout(widget);
        horizontalLayout->setSpacing(6);
        horizontalLayout->setContentsMargins(11, 11, 11, 11);
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        horizontalLayout->setContentsMargins(0, 0, 0, 0);
        pushButton_6 = new QToolButton(widget);
        indexbutton = new QButtonGroup(MainWindow);
        indexbutton->setObjectName(QStringLiteral("indexbutton"));
        indexbutton->addButton(pushButton_6);
        pushButton_6->setObjectName(QStringLiteral("pushButton_6"));
        QSizePolicy sizePolicy4(QSizePolicy::Expanding, QSizePolicy::Fixed);
        sizePolicy4.setHorizontalStretch(0);
        sizePolicy4.setVerticalStretch(0);
        sizePolicy4.setHeightForWidth(pushButton_6->sizePolicy().hasHeightForWidth());
        pushButton_6->setSizePolicy(sizePolicy4);
        pushButton_6->setMinimumSize(QSize(128, 128));
        pushButton_6->setStyleSheet(QLatin1String("background:transparent;\n"
"color:white;\n"
"\n"
""));
        QIcon icon;
        icon.addFile(QStringLiteral(":/images/button-4.png"), QSize(), QIcon::Normal, QIcon::Off);
        pushButton_6->setIcon(icon);
        pushButton_6->setIconSize(QSize(96, 96));
        pushButton_6->setToolButtonStyle(Qt::ToolButtonTextUnderIcon);
        pushButton_6->setAutoRaise(true);

        horizontalLayout->addWidget(pushButton_6);

        pushButton_5 = new QToolButton(widget);
        indexbutton->addButton(pushButton_5);
        pushButton_5->setObjectName(QStringLiteral("pushButton_5"));
        sizePolicy4.setHeightForWidth(pushButton_5->sizePolicy().hasHeightForWidth());
        pushButton_5->setSizePolicy(sizePolicy4);
        pushButton_5->setMinimumSize(QSize(128, 128));
        pushButton_5->setStyleSheet(QLatin1String("background:transparent;\n"
"color:white;\n"
"\n"
""));
        QIcon icon1;
        icon1.addFile(QStringLiteral(":/images/button-5.png"), QSize(), QIcon::Normal, QIcon::Off);
        pushButton_5->setIcon(icon1);
        pushButton_5->setIconSize(QSize(96, 96));
        pushButton_5->setToolButtonStyle(Qt::ToolButtonTextUnderIcon);
        pushButton_5->setAutoRaise(true);

        horizontalLayout->addWidget(pushButton_5);

        pushButton4 = new QToolButton(widget);
        indexbutton->addButton(pushButton4);
        pushButton4->setObjectName(QStringLiteral("pushButton4"));
        sizePolicy4.setHeightForWidth(pushButton4->sizePolicy().hasHeightForWidth());
        pushButton4->setSizePolicy(sizePolicy4);
        pushButton4->setMinimumSize(QSize(128, 128));
        pushButton4->setLayoutDirection(Qt::LeftToRight);
        pushButton4->setAutoFillBackground(false);
        pushButton4->setStyleSheet(QLatin1String("background:transparent;\n"
"color:white;\n"
"\n"
""));
        QIcon icon2;
        icon2.addFile(QStringLiteral(":/images/button-1.png"), QSize(), QIcon::Normal, QIcon::Off);
        pushButton4->setIcon(icon2);
        pushButton4->setIconSize(QSize(96, 96));
        pushButton4->setToolButtonStyle(Qt::ToolButtonTextUnderIcon);
        pushButton4->setAutoRaise(true);

        horizontalLayout->addWidget(pushButton4);

        pushButton_2 = new QToolButton(widget);
        indexbutton->addButton(pushButton_2);
        pushButton_2->setObjectName(QStringLiteral("pushButton_2"));
        sizePolicy4.setHeightForWidth(pushButton_2->sizePolicy().hasHeightForWidth());
        pushButton_2->setSizePolicy(sizePolicy4);
        pushButton_2->setMinimumSize(QSize(128, 128));
        pushButton_2->setStyleSheet(QLatin1String("background:transparent;\n"
"color:white;\n"
""));
        QIcon icon3;
        icon3.addFile(QStringLiteral(":/images/button-2.png"), QSize(), QIcon::Normal, QIcon::Off);
        pushButton_2->setIcon(icon3);
        pushButton_2->setIconSize(QSize(96, 96));
        pushButton_2->setPopupMode(QToolButton::DelayedPopup);
        pushButton_2->setToolButtonStyle(Qt::ToolButtonTextUnderIcon);
        pushButton_2->setAutoRaise(true);

        horizontalLayout->addWidget(pushButton_2);


        gridLayout->addWidget(widget, 3, 0, 1, 1);

        status = new QLineEdit(centralWidget);
        status->setObjectName(QStringLiteral("status"));
        status->setStyleSheet(QLatin1String("background:transparent;\n"
"color:white"));

        gridLayout->addWidget(status, 0, 0, 1, 1);

        MainWindow->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(MainWindow);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 960, 22));
        MainWindow->setMenuBar(menuBar);
        mainToolBar = new QToolBar(MainWindow);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        MainWindow->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(MainWindow);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        MainWindow->setStatusBar(statusBar);

        retranslateUi(MainWindow);

        stackedWidget->setCurrentIndex(0);


        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QApplication::translate("MainWindow", "MainWindow", 0));
        showresult->setText(QString());
        clearmap->setText(QApplication::translate("MainWindow", "\346\270\205\351\231\244", 0));
        recognition->setText(QApplication::translate("MainWindow", "\350\257\206\345\210\253", 0));
        pushButton_3->setText(QApplication::translate("MainWindow", "\346\211\223\345\274\200\347\233\270\345\206\214", 0));
        captureImage->setText(QApplication::translate("MainWindow", "\346\213\215\346\221\204", 0));
        serverIPLineEdit->setText(QString());
        pushButton->setText(QApplication::translate("MainWindow", "\350\277\236\346\216\245\346\234\215\345\212\241\345\231\250", 0));
        label->setText(QApplication::translate("MainWindow", "\346\234\215\345\212\241\345\231\250ip:", 0));
        label_2->setText(QApplication::translate("MainWindow", "\346\234\215\345\212\241\345\231\250\347\253\257\345\217\243:", 0));

        const bool __sortingEnabled = listWidget->isSortingEnabled();
        listWidget->setSortingEnabled(false);
        QListWidgetItem *___qlistwidgetitem = listWidget->item(0);
        ___qlistwidgetitem->setText(QApplication::translate("MainWindow", "TCP\357\274\210Transmission Control Protocol \344\274\240\350\276\223\346\216\247\345\210\266\345\215\217\350\256\256\357\274\211\346\230\257\344\270\200\347\247\215\351\235\242\345\220\221\350\277\236\346\216\245\347\232\204\343\200\201\345\217\257\351\235\240\347\232\204\343\200\201\345\237\272\344\272\216\345\255\227\350\212\202\346\265\201\347\232\204\344\274\240\350\276\223\345\261\202\351\200\232\344\277\241\345\215\217\350\256\256\357\274\214\347\224\261IETF\347\232\204RFC 793\345\256\232\344\271\211\343\200\202", 0));
        QListWidgetItem *___qlistwidgetitem1 = listWidget->item(1);
        ___qlistwidgetitem1->setText(QApplication::translate("MainWindow", "\345\234\250\347\256\200\345\214\226\347\232\204\350\256\241\347\256\227\346\234\272\347\275\221\347\273\234OSI\346\250\241\345\236\213\344\270\255\357\274\214\345\256\203\345\256\214\346\210\220\347\254\254\345\233\233\345\261\202\344\274\240\350\276\223\345\261\202\346\211\200\346\214\207\345\256\232\347\232\204\345\212\237\350\203\275\357\274\214\347\224\250\346\210\267\346\225\260\346\215\256\346\212\245\345\215\217\350\256\256\357\274\210UDP\357\274\211\346\230\257\345\220\214\344\270\200\345\261\202\345\206\205[1]  \345\217\246\344\270\200\344\270\252\351\207\215\350\246\201\347\232\204\344\274\240\350\276\223\345\215\217\350\256\256\343\200\202", 0));
        QListWidgetItem *___qlistwidgetitem2 = listWidget->item(2);
        ___qlistwidgetitem2->setText(QApplication::translate("MainWindow", "\345\234\250\345\233\240\347\211\271\347\275\221\345\215\217\350\256\256\346\227\217\357\274\210Internet protocol suite\357\274\211\344\270\255\357\274\214TCP\345\261\202\346\230\257\344\275\215\344\272\216IP\345\261\202\344\271\213\344\270\212\357\274\214\345\272\224\347\224\250\345\261\202\344\271\213\344\270\213\347\232\204\344\270\255\351\227\264\345\261\202\343\200\202", 0));
        QListWidgetItem *___qlistwidgetitem3 = listWidget->item(3);
        ___qlistwidgetitem3->setText(QApplication::translate("MainWindow", "\344\270\215\345\220\214\344\270\273\346\234\272\347\232\204\345\272\224\347\224\250\345\261\202\344\271\213\351\227\264\347\273\217\345\270\270\351\234\200\350\246\201\345\217\257\351\235\240\347\232\204\343\200\201\345\203\217\347\256\241\351\201\223\344\270\200\346\240\267\347\232\204\350\277\236\346\216\245\357\274\214\344\275\206\346\230\257IP\345\261\202\344\270\215\346\217\220\344\276\233\350\277\231\346\240\267\347\232\204\346\265\201\346\234\272\345\210\266\357\274\214\350\200\214\346\230\257\346\217\220\344\276\233\344\270\215\345\217\257\351\235\240\347\232\204\345\214\205\344\272\244\346\215\242\343\200\202", 0));
        QListWidgetItem *___qlistwidgetitem4 = listWidget->item(4);
        ___qlistwidgetitem4->setText(QApplication::translate("MainWindow", "\345\272\224\347\224\250\345\261\202\345\220\221TCP\345\261\202\345\217\221\351\200\201\347\224\250\344\272\216\347\275\221\351\227\264\344\274\240\350\276\223\347\232\204\343\200\201\347\224\2508\344\275\215\345\255\227\350\212\202\350\241\250\347\244\272\347\232\204\346\225\260\346\215\256\346\265\201\357\274\214\347\204\266\345\220\216TCP\346\212\212\346\225\260\346\215\256\346\265\201\345\210\206\345\214\272\346\210\220\351\200\202\345\275\223\351\225\277\345\272\246\347\232\204\346\212\245\346\226\207\346\256\265", 0));
        QListWidgetItem *___qlistwidgetitem5 = listWidget->item(5);
        ___qlistwidgetitem5->setText(QApplication::translate("MainWindow", "\357\274\210\351\200\232\345\270\270\345\217\227\350\257\245\350\256\241\347\256\227\346\234\272\350\277\236\346\216\245\347\232\204\347\275\221\347\273\234\347\232\204\346\225\260\346\215\256\351\223\276\350\267\257\345\261\202\347\232\204\346\234\200\345\244\247\344\274\240\350\276\223\345\215\225\345\205\203\357\274\210MTU\357\274\211\347\232\204\351\231\220\345\210\266\357\274\211\343\200\202", 0));
        QListWidgetItem *___qlistwidgetitem6 = listWidget->item(6);
        ___qlistwidgetitem6->setText(QApplication::translate("MainWindow", "\344\271\213\345\220\216TCP\346\212\212\347\273\223\346\236\234\345\214\205\344\274\240\347\273\231IP\345\261\202\357\274\214\347\224\261\345\256\203\346\235\245\351\200\232\350\277\207\347\275\221\347\273\234\345\260\206\345\214\205\344\274\240\351\200\201\347\273\231\346\216\245\346\224\266\347\253\257\345\256\236\344\275\223[1]  \347\232\204TCP\345\261\202\343\200\202TCP\344\270\272\344\272\206\344\277\235\350\257\201\344\270\215\345\217\221\347\224\237\344\270\242\345\214\205\357\274\214\345\260\261\347\273\231\346\257\217\344\270\252\345\214\205\344\270\200\344\270\252\345\272\217\345\217\267", 0));
        QListWidgetItem *___qlistwidgetitem7 = listWidget->item(7);
        ___qlistwidgetitem7->setText(QApplication::translate("MainWindow", "\345\220\214\346\227\266\345\272\217\345\217\267\344\271\237\344\277\235\350\257\201\344\272\206\344\274\240\351\200\201\345\210\260\346\216\245\346\224\266\347\253\257\345\256\236\344\275\223\347\232\204\345\214\205\347\232\204\346\214\211\345\272\217\346\216\245\346\224\266\343\200\202", 0));
        QListWidgetItem *___qlistwidgetitem8 = listWidget->item(8);
        ___qlistwidgetitem8->setText(QApplication::translate("MainWindow", "\347\204\266\345\220\216\346\216\245\346\224\266\347\253\257\345\256\236\344\275\223\345\257\271\345\267\262\346\210\220\345\212\237\346\224\266\345\210\260\347\232\204\345\214\205\345\217\221\345\233\236\344\270\200\344\270\252\347\233\270\345\272\224\347\232\204\347\241\256\350\256\244\357\274\210ACK\357\274\211\357\274\233", 0));
        QListWidgetItem *___qlistwidgetitem9 = listWidget->item(9);
        ___qlistwidgetitem9->setText(QApplication::translate("MainWindow", "\345\246\202\346\236\234\345\217\221\351\200\201\347\253\257\345\256\236\344\275\223\345\234\250\345\220\210\347\220\206\347\232\204\345\276\200\350\277\224\346\227\266\345\273\266\357\274\210RTT\357\274\211\345\206\205\346\234\252\346\224\266\345\210\260\347\241\256\350\256\244,\351\202\243\344\271\210\345\257\271\345\272\224\347\232\204\346\225\260\346\215\256\345\214\205\345\260\261\350\242\253\345\201\207\350\256\276\344\270\272\345\267\262\344\270\242\345\244\261\345\260\206\344\274\232\350\242\253\350\277\233\350\241\214\351\207\215\344\274\240\343\200\202", 0));
        QListWidgetItem *___qlistwidgetitem10 = listWidget->item(10);
        ___qlistwidgetitem10->setText(QApplication::translate("MainWindow", "TCP\347\224\250\344\270\200\344\270\252\346\240\241\351\252\214\345\222\214\345\207\275\346\225\260\346\235\245\346\243\200\351\252\214\346\225\260\346\215\256\346\230\257\345\220\246\346\234\211\351\224\231\350\257\257\357\274\233\345\234\250\345\217\221\351\200\201\345\222\214\346\216\245\346\224\266\346\227\266\351\203\275\350\246\201\350\256\241\347\256\227\346\240\241\351\252\214\345\222\214\343\200\202", 0));
        listWidget->setSortingEnabled(__sortingEnabled);


        const bool __sortingEnabled1 = listWidget_2->isSortingEnabled();
        listWidget_2->setSortingEnabled(false);
        QListWidgetItem *___qlistwidgetitem11 = listWidget_2->item(0);
        ___qlistwidgetitem11->setText(QApplication::translate("MainWindow", "Created by YingMo in 2016.", 0));
        QListWidgetItem *___qlistwidgetitem12 = listWidget_2->item(1);
        ___qlistwidgetitem12->setText(QApplication::translate("MainWindow", "Copyright \302\251 2016\345\271\264 so. All rights reserved.", 0));
        QListWidgetItem *___qlistwidgetitem13 = listWidget_2->item(2);
        ___qlistwidgetitem13->setText(QApplication::translate("MainWindow", "Teammates:Yu Jinbei,Li Lingxuan", 0));
        QListWidgetItem *___qlistwidgetitem14 = listWidget_2->item(4);
        ___qlistwidgetitem14->setText(QApplication::translate("MainWindow", "BUPT", 0));
        QListWidgetItem *___qlistwidgetitem15 = listWidget_2->item(5);
        ___qlistwidgetitem15->setText(QApplication::translate("MainWindow", "Address:", 0));
        QListWidgetItem *___qlistwidgetitem16 = listWidget_2->item(6);
        ___qlistwidgetitem16->setText(QApplication::translate("MainWindow", "Phone number:", 0));
        QListWidgetItem *___qlistwidgetitem17 = listWidget_2->item(8);
        ___qlistwidgetitem17->setText(QApplication::translate("MainWindow", "Client:", 0));
        QListWidgetItem *___qlistwidgetitem18 = listWidget_2->item(9);
        ___qlistwidgetitem18->setText(QApplication::translate("MainWindow", "Version:1.0.0", 0));
        QListWidgetItem *___qlistwidgetitem19 = listWidget_2->item(10);
        ___qlistwidgetitem19->setText(QApplication::translate("MainWindow", "Based on QT&OC ", 0));
        QListWidgetItem *___qlistwidgetitem20 = listWidget_2->item(11);
        ___qlistwidgetitem20->setText(QApplication::translate("MainWindow", "Server:", 0));
        QListWidgetItem *___qlistwidgetitem21 = listWidget_2->item(12);
        ___qlistwidgetitem21->setText(QApplication::translate("MainWindow", "Version2.0.0", 0));
        QListWidgetItem *___qlistwidgetitem22 = listWidget_2->item(13);
        ___qlistwidgetitem22->setText(QApplication::translate("MainWindow", "Based on QT&Python", 0));
        QListWidgetItem *___qlistwidgetitem23 = listWidget_2->item(17);
        ___qlistwidgetitem23->setText(QApplication::translate("MainWindow", "\350\213\245\345\207\272\347\216\260\351\207\215\345\244\247BUG\357\274\214\346\225\254\350\257\267\346\234\237\345\276\205\344\270\213\344\270\200\347\211\210\346\234\254", 0));
        listWidget_2->setSortingEnabled(__sortingEnabled1);

        pushButton_6->setText(QApplication::translate("MainWindow", "\350\257\206\345\210\253", 0));
        pushButton_5->setText(QApplication::translate("MainWindow", "\347\233\270\346\234\272", 0));
        pushButton4->setText(QApplication::translate("MainWindow", "\347\275\221\347\273\234", 0));
        pushButton_2->setText(QApplication::translate("MainWindow", "\345\205\263\344\272\216", 0));
        status->setText(QString());
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
