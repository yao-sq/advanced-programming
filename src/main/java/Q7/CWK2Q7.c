/*
 ============================================================================
 Name        : CWK2Q7.c
 Author      : Anonymous (DO NOT CHANGE)
 Description :
 Implement a Columnar Transposition Cipher in C to encrypt a message of any
 length. A Columnar Transposition Cipher is transposition cipher that follows
 a simple rule for mixing up the characters in the plaintext to form the
 ciphertext.

 As an example, to encrypt the message ATTACKATDAWN with the keyword KEYS,
 we first write out our message as shown below,
    K	E	Y	S
    A	T	T	A
    C	K	A	T
    D	A	W	N

 Note: if the message to encode does not fit into the grid, you should pad
 the message with x's or random characters for example, ATTACKNOW with the
 keyword KEYS might look like below,
    K	E	Y	S
    A	T	T	A
    C	K	N	O
    W	X	X	X

 Once you have constructed your table, the columns are now reordered such
 that the letters in the keyword are ordered alphabetically,
    E	K	S	Y
    T	A	A	T
    K	C	T	A
    A	D	N	W

 The ciphertext is now read off along the columns, so in our example above,
 the ciphertext is TAATKCTAADNW.

 You should demonstrate your implementation by encrypting the file in the
 folder Q7 using the keyword - LOVELACE.

 ============================================================================
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


char* readfile(const char *filename) {
    FILE *fp = fopen(filename, "r");
    fseek(fp, 0L, SEEK_END);
    long size = ftell(fp) + 1;
    rewind(fp);
    char *text = malloc(size * sizeof(char));
    fread(text, size, 1, fp);
    fclose(fp);
    return text;
}

char* clean_message(const char *input, const char *key) {
    char* message_buffer = malloc(strlen(input) + 1);
    char* input_copy = strdup(input);
    char* token;
    while ((token = strsep(&input_copy, " \t\n")) != NULL) {
        strcat(message_buffer, token);
    }
    free(input_copy);

    unsigned long ms = strlen(message_buffer);
    unsigned long ks = strlen(key);
    unsigned long pad = (ks - (ms % ks)) % ks;
    unsigned long gs = ms + pad;

    char *message = malloc(gs + 1);
    strcpy(message, message_buffer);
    free(message_buffer);

    for (int i = 0; i < pad; ++i) {
        strcat(message, "X");
    }

    return message;
}

struct CharAndIndex {
    char c;
    int index;
};
int compare_by_char(struct CharAndIndex *a, struct CharAndIndex *b) {
    return strncmp(&a->c, &b->c, 1);
}

void copy_column(char* dest, char* src, int dest_col, int src_col, int w, int len) {
    int h = len / w;
    for (int r = 0; r < h; ++r) {
        int ind_src = src_col + (w * r);
        int ind_dest = dest_col + (w * r);
        dest[ind_dest] = src[ind_src];
    }
}

void encrypt_columnar_message(const char *input, const char *key, char **result) {
    //clean the string
    char *message = clean_message(input, key);

    int ks = strlen(key);

    //tuplelize key
    struct CharAndIndex indexed[ks];
    for (int i = 0; i < ks; ++i) {
        indexed[i] = (struct CharAndIndex){.c=key[i], .index=i};
    }

    //sort key
    qsort(indexed, ks, sizeof(struct CharAndIndex), compare_by_char);

    //read off columns, writing to result
    int ms = strlen(message);
    char *encrypted = malloc(ms + 1);
    for (int i = 0; i < ks; ++i) {
        copy_column(encrypted, message, i, indexed[i].index, ks, ms);
    }
    encrypted[ms] = '\0';

    free(message);
    
    *result = encrypted;
}

void encrypt_columnar(const char *message_filename, const char *key, char **result) {
    encrypt_columnar_message(readfile(message_filename), key, result);
}

int main(int argc, char *argv[]) {
	char *encrypted_message = NULL;

//	const char *message = "ATTACK AT DAWN";
//	const char *key = "KEYS";
//    encrypt_columnar_message(message, key, &encrypted_message);     // TAATKCTAADNW

//    const char *message = "ATTACK NOW";
//    const char *key = "KEYS";
//    encrypt_columnar_message(message, key, &encrypted_message);     // TAATKCONXWXX

	const char *message_filename = "./Q7/text.txt";
	const char *key = "LOVELACE";
	encrypt_columnar(message_filename, key, &encrypted_message);

	printf("Encrypted message:\n%s\n", encrypted_message);
	return EXIT_SUCCESS;
}
