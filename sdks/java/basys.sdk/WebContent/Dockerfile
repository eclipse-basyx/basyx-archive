# Dockerfile

FROM tomcat:9.0-jre8-alpine
# ADD tomcat/catalina.sh $CATALINA_HOME/bin/
WORKDIR /usr/local/tomcat/bin
COPY run.sh run.sh
RUN chmod +x run.sh

#Copy war file
WORKDIR /usr/local/tomcat/webapps
# COPY --from=warfile basys.sdk.war basys.sdk.war
COPY basys.sdk.war basys.sdk.war

# Expose ports
ENV JPDA_ADDRESS="8000"
ENV JPDA_TRANSPORT="dt_socket"
EXPOSE 8080
WORKDIR /usr/local/tomcat/bin
CMD ["run.sh"]