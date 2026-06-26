build:
	javac *.java

run:
	java SystemInfo

all: build run

clean:
	rm -f *.class