java= javac
TEST= java

FILE= comp test1 test2 test3 test4 test5
OBJ= pipeline.class
BIN= pipeline

all: $(FILE) clean

comp:
	$(java) pipeline.java
	
test1:
	$(TEST) $(BIN) < input1.txt
	
test2:
	$(TEST) $(BIN) < input2.txt
	
test3:
	$(TEST) $(BIN) < input3.txt
	
test4:
	$(TEST) $(BIN) < input4.txt
	
test5:
	$(TEST) $(BIN) < input5.txt

clean:
	rm $(OBJ)