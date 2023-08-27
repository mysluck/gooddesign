#! /bin/bash 本地一键svn更新、打包、部署、启动

echo "checking out files from git...."
deploy_root_path=$(cd `dirname $0`;pwd)
checkout_path=$deploy_root_path/design_checkout
run_path=/home/dev/cdss/resin
cd $checkout_path

git clone https://github.com/mysluck/gooddesign.git

echo "maven package...."
mvn clean install -DskipTests
echo "scp run"
cd $checkout_path/cloud-page/target
scp cdss.war $run_path/webapps
echo "========================delete cdss============================"
cd $run_path/webapps
rm -rf cdss
echo "scp run OK"
cd $run_path/bin
./resin.sh restart
tail -f $run_path/log/jvm-app-0.log

