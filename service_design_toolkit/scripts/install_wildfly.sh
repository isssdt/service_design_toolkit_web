#!/bin/bash

export DEBIAN_FRONTEND=noninteractive

# General utils
sudo apt-get -y update
sudo apt-get -y upgrade
sudo apt-get -y install unzip
sudo apt-get -y install software-properties-common python-software-properties

# Oracle JDK 1.8
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get -y update
sudo apt-get -y upgrade
sudo apt-get -y install oracle-java8-installer
java -version
sudo apt-get -y install oracle-java8-set-default

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