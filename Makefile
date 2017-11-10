build:
	javac Main.java Graph.java
	javac MainP2.java GraphP2.java

run-p1:
	java Main
run-p2:
	java MainP2	

clean:
	rm *.class
