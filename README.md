----
#欢迎使用NGP！#

----
## Ngp 是什么 ##
NGP是基于NXT开发的区块链系统，公链发行，使用NGP区块链，你可以基于此平台开发自己的业务系统，如ICO、P2P等业务平台。
更多信息请访问： http://www.ydwan.com

----
## 必备条件 ##
	NGP 是基于Java 8开发的，所以需要Java8的开发环境，至少需要一个JRE8的运行环境

    - *一般情况* - Java 8
    - *Ubuntu* - `http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html`
    - *Debian* - `http://www.webupd8.org/2014/03/how-to-install-oracle-java-8-in-debian.html`
    - *FreeBSD* - `pkg install openjdk8`
    - *CentOS  - `yum install java-1.8.0-openjdk.x86_64 java-1.8.0-openjdk-devel.x86_64`

## *源码获取*  ##
    `git clone https://github.com/slaxman/ngp.git`
  
----
## 运行! ##

  - click on the Ngp icon, or start from the command line:
  - Unix: `./start.sh`
  - Mac: `./run.command`
  - Window: `run.bat`

  - wait for the JavaFX wallet window to open
  - on platforms without JavaFX, open http://localhost:7877/ in a browser

----
## Compile it! ##

  - if necessary with: `./compile.sh`
  - you need jdk-8 as well

----
## Improve it! ##

  - we love **pull requests**
  - we love issues (resolved ones actually ;-) )
  - in any case, make sure you leave **your ideas** at BitBucket
  - assist others on the issue tracker
  - **review** existing code and pull requests
  - cf. coding guidelines in DEVELOPERS-GUIDE.md

----
## Troubleshooting the NRS (Ngp Reference Software) ##

  - How to Stop the NRS Server?
    - click on Ngp Stop icon, or run `./stop.sh`
    - or if started from command line, ctrl+c or close the console window

  - UI Errors or Stacktraces?
    - report on BitBucket

  - Permissions Denied?
    - no spaces and only latin characters in the path to the NRS installation directory
    - known jetty issue

----
## Further Reading ##

  - in this repository:
    - USERS-GUIDE.md
    - DEVELOPERS-GUIDE.md
    - OPERATORS-GUIDE.md
    