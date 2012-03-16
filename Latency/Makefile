JC = javac
JFLAGS = -classpath lib/jcommon-1.0.17.jar:lib/jfreechart-1.0.14.jar:.

all:
	$(JC) $(JFLAGS) *.java
	java  $(JFLAGS) Latency

clean:
	$(RM) *.class
