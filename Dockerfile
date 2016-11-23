FROM java:8

MAINTAINER trustme013@gmail.com

VOLUME /logs

EXPOSE 8099

ENV USER_NAME directlink

ENV APP_HOME /home/$USER_NAME/app

RUN useradd -ms /bin/bash $USER_NAME

RUN mkdir $APP_HOME

ADD target/getdirectlink-0.0.1-SNAPSHOT.jar $APP_HOME/getdirectlink-0.0.1-SNAPSHOT.jar

RUN chown $USER_NAME $APP_HOME/getdirectlink-0.0.1-SNAPSHOT.jar

USER $USER_NAME

WORKDIR $APP_HOME

RUN bash -c 'touch getdirectlink-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","getdirectlink-0.0.1-SNAPSHOT.jar"]