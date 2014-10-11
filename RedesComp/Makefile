JAVAC = javac
JAVACFLAGS = -g

.SUFFIXES: .java .class

SRC= user/User.java \
    commands/Command.java \
    commands/user/Retrieve.java \
    commands/user/Upload.java \
    commands/user/List.java \
    commands/ss/Retrieve.java \
    commands/ss/Upload.java \
    commands/cs/Upload.java \
    socketwrappers/ServerTCP.java \
    socketwrappers/ClientTCP.java \
    socketwrappers/ServerUDP.java \
    socketwrappers/ClientUDP.java \
    socketwrappers/MessageTCP.java \
    socketwrappers/MessageUDP.java \
    utils/Errors.java \
    utils/Protocol.java \
    utils/FileHandler.java \
    utils/LocalPaths.java \
    utils/StreamProcessors.java \
    servers/CS.java \
    servers/SS.java

CLS= $(SRC:.java=.class)

all:  $(CLS)

default: classes

classes: $(SRC:.java=.class)

clean:
    $(RM) *.class