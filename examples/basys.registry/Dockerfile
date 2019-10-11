# Dockerfile

FROM tomcat:8-jdk8

#Copy war file
COPY target/registry-0.0.3*.war $CATALINA_HOME/webapps/registry.war

CMD /usr/local/tomcat/bin/catalina.sh run && tail -f /usr/local/MYAPP.log