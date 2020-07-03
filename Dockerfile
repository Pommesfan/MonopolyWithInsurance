FROM hseeberger/scala-sbt:8u181_2.12.7_1.2.6
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjfx && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6
WORKDIR /Monopoly
ADD . /Monopoly
CMD sbt run

#For Windows
 #Install  VcXsrv Windows X Server using the address below
 #https://sourceforge.net/projects/vcxsrv/
 #
 #Install & start XLaunch with usual Windows setup (a.k.a. next next) until you get to Extra Settings.
 #Check all options as below and finish configuration. It's important to disable access control.
 #Otherwise, the request from Docker will be rejected.

#docker run --rm -it -e DISPLAY=(IP-Addresse):0.0 monopoly
