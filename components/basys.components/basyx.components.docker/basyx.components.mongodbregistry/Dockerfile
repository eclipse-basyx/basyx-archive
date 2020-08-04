# Add java runtime environment for execution
FROM java:8-jdk-alpine 

# Copy built jar to image using the jar name specified in the pom.xml (JAR_FILE)
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/basyxExecutable.jar
COPY target/lib /usr/share/lib
COPY src/main/resources/context.properties /usr/share/context.properties
 
# Expose the appropriate port. In case of Tomcat, this is 8080.
ARG PORT
EXPOSE ${PORT}
 
# Start the jar
CMD java -jar "/usr/share/basyxExecutable.jar" "/usr/share/context.properties"