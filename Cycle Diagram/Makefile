CC=gcc
CFLAGS= -Wall -g
COMP= pipe test
BFILE= pipe

all: $(COMP) clean

pipe:
	$(CC) $(CFLAGS) -o pipe pipeline.c 

test:
	./pipe input.txt

clean:
	rm $(BFILE)