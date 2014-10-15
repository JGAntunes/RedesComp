JC = javac
JFLAGS = -g
CLASSPATH = src/

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) -classpath $(CLASSPATH) $*.java

CLASSES= \
	src/utils/Errors.java \
	src/utils/Protocol.java \
	src/socketwrappers/MessageTCP.java \
	src/socketwrappers/MessageUDP.java \
	src/utils/StreamProcessors.java \
	src/utils/FileHandler.java \
	src/utils/LocalPaths.java \
	src/socketwrappers/ServerTCP.java \
	src/socketwrappers/ClientTCP.java \
	src/socketwrappers/ServerUDP.java \
	src/socketwrappers/ClientUDP.java \
	src/commands/Command.java \
	src/commands/user/Retrieve.java \
	src/commands/user/Upload.java \
	src/commands/user/List.java \
	src/commands/ss/Retrieve.java \
	src/commands/ss/Upload.java \
	src/commands/cs/Upload.java \
	src/user/User.java \
	src/servers/CS.java \
	src/servers/SS.java

default: clean classes

classes: $(CLASSES:.java=.class)

clean:
	rm -rf 	src/utils/Errors.class \
	src/utils/Protocol.class \
	src/socketwrappers/MessageTCP.class \
	src/socketwrappers/MessageUDP.class \
	src/utils/StreamProcessors.class \
	src/utils/FileHandler.class \
	src/utils/LocalPaths.class \
	src/socketwrappers/ServerTCP.class \
	src/socketwrappers/ClientTCP.class \
	src/socketwrappers/ServerUDP.class \
	src/socketwrappers/ClientUDP.class \
	src/commands/Command.class \
	src/commands/user/Retrieve.class \
	src/commands/user/Upload.class \
	src/commands/user/List.class \
	src/commands/ss/Retrieve.class \
	src/commands/ss/Upload.class \
	src/commands/cs/Upload.class \
	src/user/User.class \
	src/servers/CS.class \
	src/servers/SS.class