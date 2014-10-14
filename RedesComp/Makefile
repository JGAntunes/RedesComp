JC = javac
JFLAGS = -g
CLASSPATH = src/
OUTPUT = bin/

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) -cp $(CLASSPATH) -d $(OUTPUT) $*.java

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

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -rf $(OUTPUT)
	mkdir -p $(OUTPUT)