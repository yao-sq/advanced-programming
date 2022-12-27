#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// `typedef struct X X` is so we dont have to repeat `struct X` everywhere it is used
typedef struct Entry Entry;
struct Entry {
    int value;
    const char* key;
};

typedef struct HashMap HashMap;
struct HashMap {
    int size;
    int capacity;
    Entry** entries;
};

// declare functions ahead so later the order does not matter
void resize_map_m(HashMap* map, int new_capacity);
void add_to_map(const char *name);
void add_to_map_m(HashMap* map, const char *name);
void add_to_map_kv(HashMap* map, const char *key, int value);
void add_to_map_e(HashMap* map, Entry *entry);
Entry* create_entry(const char* key, int value);
void print_map_m(HashMap* map);
int remove_from_map_m(HashMap* map, const char *name);
int map_contains(HashMap* map, const char *key);
int find_entry(HashMap* map, const char *key);

//global static variables
static HashMap* instance;
static const float load_factor = 0.7f;

int hash_function(const char *key) {
    int hash = 0;
    for (int i = 0; key[i]; i++) {
        hash += key[i];
    }
    return hash;
}

void resize_map_m(HashMap* map, int new_capacity){
    Entry** entries = map->entries;
    int size = map->capacity;

    map->capacity = new_capacity;
    map->entries = calloc(new_capacity, sizeof(Entry*));
    map->size = 0;

    for (int i = 0; i < size; ++i) {
        if (entries[i] != NULL) {
            add_to_map_e(map, entries[i]);
        }
    }
    free(entries);
}

void resize_map(int new_size){
    resize_map_m(instance, new_size);
}

void add_to_map(const char *name){
    add_to_map_m(instance, name);
}
void add_to_map_m(HashMap* map, const char *name){
    add_to_map_kv(map, name, 1);
}
void add_to_map_kv(HashMap* map, const char *key, int value) {
    Entry *entry = create_entry(key, value);
    add_to_map_e(map, entry);
}
void add_to_map_e(HashMap* map, Entry *entry){
    if (entry->key == NULL) {
        return;
    }

    map->size++;

    if (map->capacity * load_factor <= map->size) {
        resize_map_m(map, 2 * map->capacity);
    }

    int k = hash_function(entry->key) % map->capacity;
    // while not "at a free bucket", go to the next
    while (map->entries[k] != NULL && map->entries[k]->key != NULL) {
        //if the entry exists already
        if (map->entries[k] == entry || strcmp(map->entries[k]->key, entry->key) == 0) {
            //not exactly the same entry, so replace with the new one -- otherwise nothing to do
            if (map->entries[k] != entry) {
                free(map->entries[k]);
                map->entries[k] = entry;
            }
            return;
        }
        k = (k+1) % map->capacity;
    }
    //Replacing a dummy tombstone entry - we can free the memory for it now
    if (map->entries[k] != NULL) {
        free(map->entries[k]);
    }
    map->entries[k] = entry;
}

Entry* create_entry(const char* key, int value) {
    Entry *entry = malloc(sizeof(Entry));
    entry->key = key;
    entry->value = value;
    return entry;
}


int remove_from_map(const char *name) {
    return remove_from_map_m(instance, name);
}

int remove_from_map_m(HashMap* map, const char *name) {
    int k = find_entry(map, name);
    if (k == -1) {
        return 0;
    }

    //Fully deleting the entry could leave gaps that break the linear-probing strategy when searching
//    free(map->entries[k]);
//    map->entries[k] = NULL;
    // Instead we leave a dummy entry - a tombstone
    map->entries[k]->key = NULL;

    map->size--;
    return 1;
}

int search_map(const char *name) {
    return map_contains(instance, name);
}

int map_contains(HashMap* map, const char *key) {
    int k = find_entry(map, key);
    return k == -1 ? 0 : 1;
}

int find_entry(HashMap* map, const char *key) {
    int k = hash_function(key) % map->capacity;
    for (int i = 0; i < map->capacity; ++i) {
        Entry* e = map->entries[k];
        if (e == NULL) {
            return -1;
        }
        if (e->key != NULL && strcmp(key, e->key) == 0) {
            return k;
        }
        k = (k + 1) % map->capacity;
    }
    return -1;
}

void print_map(){
    print_map_m(instance);
}

void print_map_m(HashMap* map){
    printf("\nHash map (capacity: %d)\n--------------------\n", map->capacity);
    for (int i=0; i<map->capacity; i++) {
        Entry *entry = map->entries[i];
        if (entry != NULL && entry->key != NULL) {
            printf("Bucket: %d, Hash: %d, Key: %s, Value: %d\n", i, hash_function(entry->key), entry->key, entry->value);
        }
    }
    printf("--------------------\n\n");
}

void init_map() {
    instance = malloc(sizeof(HashMap));
    instance->size = 0;
    instance->capacity = 4;
    instance->entries = calloc(instance->capacity, sizeof(Entry*));
}

int main(int argc, char *argv[]) {
    init_map();

    char *stringOne = "#Hello world";
    char *stringTwo = "How are you?";
    char *stringThree = "Be the best you...!!";
    char *stringFour = "Be kind to yourself";
    char *stringFive = "Principles of Programming 2";

    resize_map(6);
    add_to_map(stringOne);
    add_to_map(stringTwo);
    add_to_map(stringOne);
    add_to_map(stringThree);
    add_to_map(stringFour);
    add_to_map(stringFive);
    print_map();

    int ret = search_map(stringOne);
    if (ret)
        printf("Found %s!\n", stringOne); //Found #Hello world!

    remove_from_map(stringThree);

    ret = search_map(stringFive);
    if (ret)
        printf("Found %s!\n", stringFive); //Found Principles of Programming 2!
    print_map();

    add_to_map(stringThree);
    print_map();


//    init_map();
//    add_to_map("Anne");
//    add_to_map("Mary");
//    add_to_map("Mike");
//    add_to_map("ekiM");
//    add_to_map("Pete");
//
//    print_map();
//
//    printf("Anne is included: %d\n", search_map("Anne"));
//    printf("Bella is included: %d\n", search_map("Bella"));
//    printf("Mike is included: %d\n", search_map("Mike"));
//    printf("ekiM is included: %d\n", search_map("ekiM"));
//    printf("Miek is included: %d\n", search_map("Miek"));
//    printf("Pete is included: %d\n", search_map("Pete"));
//
//    printf("\nRemoving ekiM\n");
//    remove_from_map("ekiM");
//
//    printf("ekiM is included: %d\n", search_map("ekiM"));
}
