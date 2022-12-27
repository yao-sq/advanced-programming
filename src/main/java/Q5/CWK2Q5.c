/*
 ============================================================================
 Name        : CWK2Q5.c
 Author      : Anonymous (DO NOT CHANGE)
 Description :
 Implement an algorithm in C which given a file containing a block of text as
 input, redacts all words from a given set of “redactable” words (also from a
 file), and outputs the result to a file called “result.txt”. For example,
 given the block of text:
    The quick brown fox jumps over the lazy dog

 and the redactable set of words:
    the, jumps, lazy

 the output text in “result.txt” should be
    *** quick brown fox ***** over *** **** dog

 Note that the number of stars in the redacted text is the same as the number
 of letters in the word that has been redacted, and that capitalization is
 ignored. You should not use any of the string libraries to answer this
 question. You should also test your program using the example files
 provided.
 ============================================================================
*/

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

void redact_words(const char *text_filename, const char *redact_words_filename);

// IO and String functions
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

// "splits" the text into lines by replacing the line break with \0 and calling the callback
// (it modifies the original text!)
void iterate_lines(char *text, void (*callback)(char *line)) {
    int start = 0;
    for (int i = 0; text[i]; i++) {
        if (text[i] == '\n') {
            text[i] = '\0';
            callback(&text[start]);
            start = i + 1;
        }
    }
}

int is_wordchar(char c) {
    // standard library equivalent: return isalpha(c);
    return (('A' <= c && c <= 'Z')||('a' <= c && c <= 'z'));
}

void iterate_words(char *text, void (*callback)(char *word, int len)) {
    int start = 0;
    if (text[0] == '\0') return;
    int prev_was_wordchar = is_wordchar(text[0]);
    for (int i = 1;; i++) {
        int is_end = text[i] == '\0';
        int current_is_wordchar = is_wordchar(text[i]);
        if (current_is_wordchar != prev_was_wordchar) {
            if (prev_was_wordchar) {
                callback(&text[start], i - start);
            }
            start = i;
        }
        prev_was_wordchar = current_is_wordchar;
        if (is_end) break;
    }
}

int streq(char *a, char *b, int len) {
    // standard library equivalent: return strncmp(a, b, len) == 0;
    for (int i = 0; i < len; i++) {
        if (a[i] != b[i]) return 0;

        if (a[i] == '\0') break; // both would be terminated, otherwise the inequality would return early
    }
    return 1;
}

// Hash Set -- mostly from Q3
typedef struct HashSet HashSet;
struct HashSet {
    int size;
    int capacity;
    char** entries;
};

static HashSet* set_instance;
static const float load_factor = 0.7f;

void init_set();
void add_to_set(char *item);
int set_contains_l(char *item, int len);

void init_set() {
    set_instance = malloc(sizeof(HashSet));
    set_instance->size = 0;
    set_instance->capacity = 4;
    set_instance->entries = calloc(set_instance->capacity, sizeof(char*));
}
int hash_n(char *key, int len) {
    int hash = 0;
    for (int i = 0; key[i] && i < len; i++) {
        hash += key[i];
    }
    return hash;
}
int hash(char *key) {
    return hash_n(key, INT_MAX);
}
void resize_set(int new_capacity) {
    HashSet* set = set_instance;

    char** entries = set->entries;
    int size = set->capacity;

    set->capacity = new_capacity;
    set->entries = calloc(new_capacity, sizeof(char*));
    set->size = 0;

    for (int i = 0; i < size; ++i) {
        if (entries[i] != NULL) {
            add_to_set(entries[i]);
        }
    }

    free(entries);
}
void add_to_set(char *item) {
    HashSet* set = set_instance;
    set->size++;

    if (set->capacity * load_factor <= set->size) {
        resize_set(2 * set->capacity);
    }

    int k = hash(item) % set->capacity;
    while (set->entries[k] != NULL) {
        k = (k+1) % set->capacity;
    }
    set->entries[k] = item;
}
int set_contains_l(char *item, int len) {
    HashSet* set = set_instance;

    int k = hash_n(item, len) % set->capacity;
    for (int i = 0; i < set->capacity; ++i) {
        char* e = set->entries[k];
        if (e == NULL) {
            return 0;
        }
        if (streq(item, e, len)) {
            return 1;
        }
        k = (k + 1) % set->capacity;
    }
    return 0;
}
void print_set() {
    HashSet* set = set_instance;
    printf("\nHash set (capacity: %d)\n--------------------\n", set->capacity);
    for (int i=0; i < set->capacity; i++) {
        char *entry = set->entries[i];
        if (entry != NULL) {
            printf("Bucket: %d, Hash: %d, Item: %s\n", i, hash(entry), entry);
        }
    }
    printf("--------------------\n\n");
}

// Redacting
void handle_redact_input_word(char *word, int len) {
    word[len] = '\0';
    char *redact_word = word;
    add_to_set(redact_word);
}
void handle_redact_input_line(char *line) {
    iterate_words(line, handle_redact_input_word);
}

void handle_input_word(char *word, int len) {
    if (set_contains_l(word, len)) {
        for (int i = 0; i < len; ++i) {
            word[i] = '*';
        }
    }
}

void redact_words(const char *text_filename, const char *redact_words_filename){
    char *text = readfile(text_filename);

    char *redact_input = readfile(redact_words_filename);
    iterate_lines(redact_input, handle_redact_input_line);

    iterate_words(text, handle_input_word);

//    print_set();
//    printf("\n\nRESULT:\n%s", text);

    //write to file
    FILE* result_file = fopen("./result.txt", "w");
    fputs(text, result_file);
    fclose(result_file);
}

int main(int argc, char *argv[]) {
    init_set();
    const char *input_file = "./Q5/debate.txt";
    const char *redact_file = "./Q5/redact.txt";
    redact_words(input_file, redact_file);
    return EXIT_SUCCESS;
}
