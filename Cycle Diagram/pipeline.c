#include <stdlib.h>
#include <stdio.h>
#define READ_NUM 2048

int main(int argc, char *argv[]){

	if(argc == 1 || argc > 2){
		fprintf(stderr, "Usage: %s <input.txt>\n", argv[0]);
		exit(0);
	}

	char *instr[] = {"IF", "ID", "EX", "DM", "WB"};
	int size = (int)(sizeof(instr)/sizeof(*instr));
	
	char *input;
	char line[READ_NUM];

	input = argv[argc-1];

	FILE *file;
	size_t nread = 0;

	file = fopen(input, "r");

	if(file){
		while((nread = fread(line, 1, sizeof(line), file)) > 0)
			fwrite(line, 1, nread, stdout);

		if(ferror(file)){
			fprintf(stderr, "ERROR: Could not read from the file\n");
			exit(0);
		}

		fclose(file);

		printf("\n");
	}
	else{
		fprintf(stderr, "ERROR: Could not open the file\n");
		exit(0);
	}

	printf("\nThe size of the pointer array is: %d\n\n", size);

	int i;
	for(i = 0; i < size; i++)
		printf("%s ", instr[i]);
	
	printf("\n\n");

	return 0;
}