# Add java runtime environment for execution
FROM java:8-jdk-alpine 

# Copy built jar to image using the jar name specified in the pom.xml (JAR_FILE)
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/basyxExecutable.jar
COPY target/lib /usr/share/lib
COPY src/main/resources/context.properties /usr/share/context.properties
COPY src/test/resources/dockerMongodb.properties /usr/share/dbConfig.properties
 
# Expose the appropriate port. In case of Tomcat, this is 8080.
ARG PORT
EXPOSE ${PORT}

# Set the path for the context configuration file
ARG CONTEXT_CONFIG_KEY
ENV ${CONTEXT_CONFIG_KEY} "/usr/share/context.properties"

# Set the path for the mongodb configuration file
ARG MONGODB_CONFIG_KEY
ENV ${MONGODB_CONFIG_KEY} "/usr/share/dbConfig.properties"
 
# Start the jar
CMD java -jar "/usr/share/basyxExecutable.jar" 