nohup java -jar jeecg-system-start-3.5.3.jar  >catalina.out 2>error.log &
#!/bin/bash

#项目运行端口
port=9000

#打包分支
branch=master

#项目路径统一前缀(全路径)
prefix=/usr/local/java/prod
#日志目录
logdir=/usr/local/design/log

#项目根路径
projectrootdir=${prefix}/design
#生成的jar包的名称以及路径，保证sh文件到处都可运行，使用全路径
newjarfiledir=${prefix}/jar
newjarfilename=${newjarfiledir}/jeecg-system-start-3.5.3.jar
#旧jar包不删除，移动到指定目录(为了不重名，采用时间串)
oldjarfiledir=${prefix}/oldjar
currenttime=$(date "+%Y%m%d%H%M%S")
oldjarfilename=${oldjarfiledir}/jeecg-system-start-3.5.3${currenttime}.jar

#默认数据
#target目录``
targetdir=${projectrootdir}/target

#输出文件目录
outfilename=${logdir}/nohup.out

#是否存在根目录文件夹
echo "是否存在根目录文件夹"
if [ ! -d "${projectrootdir}" ]; then
	echo "不存在目录${projectrootdir}"
	echo "拉gitee上的项目 https://github.com/mysluck/gooddesign.git"
	git clone https://github.com/mysluck/gooddesign.git
else
	echo "使用已存在的目录${projectrootdir}"
fi
#是否存在存放jar的文件夹
echo "是否存在存放jar的文件夹"
if [ ! -d "${newjarfiledir}" ]; then
	mkdir ${newjarfiledir}
else
	echo "使用已存在的目录${newjarfiledir}"
fi
#是否存在存放oldjar的文件夹
echo "是否存在存放oldjar的文件夹"
if [ ! -d "${oldjarfiledir}" ]; then
	mkdir ${oldjarfiledir}
else
	echo "使用已存在的目录${oldjarfiledir}"
fi

#开始处理
echo "开始查找${port}端口进程PID"
pid=$(lsof -i:${port} | awk 'NR==2{print $2}')
if [ ! -n "$pid" ]; then
        echo "${port}端口未被占用"
else
       echo "端口已被占用，正在结束进程..."
       $(kill -s 9 $pid)
       echo "结束进程成功！"
fi

echo "cd ${projectrootdir}"
cd ${projectrootdir}
echo "git checkout ${branch}"
git checkout master
echo "git pull origin ${branch}"
git pull origin ${branch}
echo "mvn clean"
mvn clean
echo "mvn compile"
mvn compile
echo "mvn -Dmaven.test.skip=true package"
mvn -Dmaven.test.skip=true package
targetjarfilename=$(find ${targetdir} -name "*.jar" | sed 's#.*/##')
echo "生成的包名为：${targetjarfilename}"
if [ ! -f "${newjarfilename}" ]; then
	echo "${newjarfilename}下未发现文件名冲突jar包"
else
	echo "移动包：${newjarfilename} --> ${oldjarfilename}"
	mv ${newjarfilename} ${oldjarfilename}
fi
echo "移动包：${targetdir}/${targetjarfilename} -->  ${newjarfilename}"
mv ${targetdir}/${targetjarfilename} ${newjarfilename}

echo "启动：nohup java  -Xrunjdwp:transport=dt_socket,address=38080,server=y,suspend=n -jar -Dserver.port=${port} ${newjarfilename} >> ${outfilename}  &"
nohup java  -Xrunjdwp:transport=dt_socket,address=38080,server=y,suspend=n -jar -Dserver.port=${port}  ${newjarfilename} >> ${outfilename}  &

echo "启动成功，成功运行脚本自动化部署springBoot项目！！"

echo "查看日志：ctrl+c 退出查看"
tail -f ${outfilename}

