#!/bin/bash

export DEBIAN_FRONTEND=noninteractive

# General utils
sudo apt-get -y update
sudo apt-get -y upgrade

# Install MySQL Server in a Non-Interactive mode. Default root password will be "root"
echo "mysql-server-5.6 mysql-server/root_password password root" | sudo debconf-set-selections
echo "mysql-server-5.6 mysql-server/root_password_again password root" | sudo debconf-set-selections
sudo apt-get -y install mysql-server-5.6

#sudo sed -i 's/127\.0\.0\.1/0\.0\.0\.0/g' /etc/mysql/my.cnf
#mysql -uroot -p -e 'USE mysql; UPDATE `user` SET `Host`="%" WHERE `User`="root" AND `Host`="localhost"; DELETE FROM `user` WHERE `Host` != "%" AND `User`="root"; FLUSH PRIVILEGES;'

sudo service mysql restart

sudo apt-get -y install unzip
sudo apt-get -y install software-properties-common python-software-properties

# Oracle JDK 1.8
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get -y update
sudo apt-get -y upgrade

echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
sudo apt-get -y install oracle-java8-installer
java -version
sudo apt-get -y install oracle-java8-set-default

sudo mkdir /home/temp
sudo unzip service_design_toolkit.zip -d /home/temp/

mysql --user="root" --password="root" < /home/temp/init-db.sql

# Wildfly 9
wget http://download.jboss.org/wildfly/9.0.1.Final/wildfly-9.0.1.Final.zip
sudo unzip wildfly-9.0.1.Final.zip -d /opt/

# Wildfly defaults config
sudo cp /opt/wildfly-9.0.1.Final/bin/init.d/wildfly.conf /etc/default/wildfly9
# Changes to configure:
sudo sed -i 's|# JAVA_HOME="/usr/lib/jvm/default-java"|JAVA_HOME="/usr/lib/jvm/java-8-oracle"|g' /etc/default/wildfly9
sudo sed -i 's|# JBOSS_HOME="/opt/wildfly"|JBOSS_HOME="/opt/wildfly-9.0.1.Final"|g' /etc/default/wildfly9
sudo sed -i 's|# JBOSS_USER=wildfly|JBOSS_USER=wildfly|g' /etc/default/wildfly9
sudo sed -i 's|# JBOSS_MODE=standalone|JBOSS_MODE=standalone|g' /etc/default/wildfly9
sudo sed -i 's|# JBOSS_CONFIG=standalone.xml|JBOSS_CONFIG=standalone.xml|g' /etc/default/wildfly9
sudo sed -i 's|# STARTUP_WAIT=60|STARTUP_WAIT=60|g' /etc/default/wildfly9
sudo sed -i 's|# SHUTDOWN_WAIT=60|SHUTDOWN_WAIT=60|g' /etc/default/wildfly9
sudo sed -i 's|# JBOSS_CONSOLE_LOG="/var/log/wildfly/console.log"|JBOSS_CONSOLE_LOG="/var/log/wildfly/console.log"|g' /etc/default/wildfly9

# Wildfly Init file
sudo cp /opt/wildfly-9.0.1.Final/bin/init.d/wildfly-init-debian.sh /etc/init.d/wildfly9
sudo chown root:root /etc/init.d/wildfly9
sudo chmod +x /etc/init.d/wildfly9
# Changes in Init
sudo sed -i 's|# Provides:             wildfly|# Provides:             wildfly9|g' /etc/init.d/wildfly9
sudo sed -i 's|NAME=wildfly|NAME=wildfly9|g' /etc/init.d/wildfly9

# Create wildfly system user
sudo useradd --system --shell /bin/false wildfly

sudo mkdir -p /var/log/wildfly
sudo chown -R wildfly:wildfly /opt/wildfly-9.0.1.Final/
sudo chown -R wildfly:wildfly /var/log/wildfly

# Tunning
sudo sed -i 's|   JAVA_OPTS="-Xms64m -Xmx512m -XX:MaxPermSize=256m -Djava.net.preferIPv4Stack=true"|   JAVA_OPTS="-Xms1024m -Xmx1024m -XX:MaxPermSize=256m -XX:NewRatio=2 -XX:PermSize=64m -Djava.net.preferIPv4Stack=true"|g' /opt/wildfly-9.0.1.Final/bin/standalone.conf

sudo update-rc.d wildfly9 defaults
sudo update-rc.d wildfly9 enable

sudo service wildfly9 status

cd /opt/wildfly-9.0.1.Final/modules/system/layers/base/com
sudo mkdir sql
cd /opt/wildfly-9.0.1.Final/modules/system/layers/base/com/sql
sudo mkdir mysql
cd /opt/wildfly-9.0.1.Final/modules/system/layers/base/com/sql/mysql
sudo mkdir main

cd /home
sudo mkdir service_design_toolkit
cd /home/service_design_toolkit
sudo mkdir photos
#this command is used to grant permission on wildfly to save photos
sudo chown -R wildfly:wildfly /home/service_design_toolkit/photos

#create wildfly user
sudo /opt/wildfly-9.0.1.Final/bin/add-user.sh adminUser adminPassword

sudo cp /home/temp/module.xml /opt/wildfly-9.0.1.Final/modules/system/layers/base/com/sql/mysql/main
sudo cp /home/temp/mysql-connector-java-5.1.38-bin.jar /opt/wildfly-9.0.1.Final/modules/system/layers/base/com/sql/mysql/main
sudo cp /home/temp/standalone.xml /opt/wildfly-9.0.1.Final/standalone/configuration

sudo /etc/init.d/wildfly9 start

/opt/wildfly-9.0.1.Final/bin/jboss-cli.sh --connect --user=adminUser --password=adminPassword --command="deploy --force /home/temp/service_design_toolkit-ear-1.0.ear"
