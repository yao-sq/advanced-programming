/*
 ============================================================================
 Name        : CWK2Q4.c
 Author      : Anonymous (DO NOT CHANGE)
 Description :
 Implement your own XOR Linked List (https://en.wikipedia.org/wiki/XOR_linked_list)
 in C capable of storing names. Your implementation should have the following
 functions:
    void insert_string(const char* newObj)
	int insert_before(const char* before, const char* newObj)
	int insert_after(const char* after, const char* newObj)
	void remove_string(char* result)
	int remove_after(const char *after, char *result)
	int remove_before(const char *before, char *result)
    void print_list()

 ============================================================================
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// `typedef struct X X` is so we dont have to repeat `struct X` everywhere it is used
typedef struct Node Node;
struct Node {
    const char *name;
    struct Node *npt;
};


// declare functions ahead so later the order does not matter
void splice_node(Node *new_node, Node *curr, Node *prev);
void unlink_node(Node *curr, Node *next);
void insert_string(const char *newObj);
void insert_first(Node **head_ref, const char *newObj);
int insert_before_h(Node **head_ref, const char* before, const char* newObj);
int insert_after_h(Node **head_ref, const char* after, const char* newObj);
int remove_first(Node **head_ref, char *result);
int remove_before_h(Node **head_ref, const char *before, char *result);
int remove_after_h(Node **head_ref, const char *after, char *result);
void print_list_h(Node* head);

//global static variables
static struct Node *head = NULL;

Node *XOR(Node *a, Node *b) {
    return (Node *) ((uintptr_t) (a) ^ (uintptr_t) (b));
}

Node* create_node(const char *name) {
    if (strlen(name) > 64) {
        return NULL;
    }
    Node *new_node = malloc(sizeof(struct Node));
    new_node->name = name;
    return new_node;
}

Node* find_node(Node *head, const char *name, Node **prev_ref) {
    struct Node *curr = head;
    struct Node *prev = NULL;
    struct Node *next;

    while (curr != NULL) {
        if (strcmp(curr->name, name) == 0) {
            *prev_ref = prev;
            return curr;
        }

        next = XOR(prev, curr->npt);

        prev = curr;
        curr = next;
    }

    return NULL;
}


void insert_string(const char *newObj) {
    insert_first(&head, newObj);
}

void insert_first(Node **head_ref, const char *newObj) {
    Node *new_node = create_node(newObj);
    if (new_node == NULL) {
        return;
    }

    new_node->npt = *head_ref;

    if (*head_ref != NULL) {
        (*head_ref)->npt = XOR(new_node, (*head_ref)->npt);
    }
    *head_ref = new_node;
}


int insert_before(const char* before, const char* newObj){
    return insert_before_h(&head, before, newObj);
}
int insert_before_h(Node **head_ref, const char* before, const char* newObj) {
    Node* anchor_node_prev;
    Node* anchor_node = find_node(*head_ref, before, &anchor_node_prev);
    if (anchor_node == NULL) {
        return 0;
    }

    if (anchor_node_prev == NULL) {
        insert_first(head_ref, newObj);
        return 1;
    }

    Node *new_node = create_node(newObj);
    if (new_node != NULL) {
        splice_node(new_node, anchor_node, anchor_node_prev);
    }
    return 1;
}


int insert_after(const char* after, const char* newObj){
    return insert_after_h(&head, after, newObj);
}
int insert_after_h(Node **head_ref, const char* after, const char* newObj) {
    Node* anchor_node_prev_prev;
    Node* anchor_node_prev = find_node(*head_ref, after, &anchor_node_prev_prev);
    if (anchor_node_prev == NULL) {
        return 0;
    }
    Node* anchor_node = XOR(anchor_node_prev->npt, anchor_node_prev_prev);

    Node *new_node = create_node(newObj);
    if (new_node != NULL) {
        splice_node(new_node, anchor_node, anchor_node_prev);
    }
    return 1;
}

void splice_node(Node *new_node, Node *curr, Node *prev) {
    new_node->npt = XOR(curr, prev);
    if (prev != NULL) {
        prev->npt = XOR(new_node, XOR(prev->npt, curr));
    }
    if (curr != NULL) {
        curr->npt = XOR(XOR(curr->npt, prev), new_node);
    }
}
void unlink_node(Node *curr, Node *next) {
    Node* prev = XOR(curr->npt, next);
    if (prev != NULL) {
        prev->npt = XOR(next, XOR(prev->npt, curr));
    }
    if (next != NULL) {
        next->npt = XOR(prev, XOR(next->npt, curr));
    }
}


int remove_string(char *result) {
    return remove_first(&head, result);
}
int remove_first(Node **head_ref, char *result) {
    Node *first = *head_ref;
    if (first == NULL) {
        return 0;
    }

    strcpy(result, first->name);
    
    Node* next = first->npt;
    if (next != NULL) {
        next->npt = XOR(next->npt, first);
    }

    free(first);
    *head_ref = next;
    return 1;
}

int remove_before(const char *before, char *result) {
    return remove_before_h(&head, before, result);
}

int remove_before_h(Node **head_ref, const char *before, char *result) {
    Node* anchor_node_prev;
    Node* anchor_node = find_node(*head_ref, before, &anchor_node_prev);

    if (anchor_node == NULL) {
        return 0;
    }

    if (anchor_node_prev == NULL) {
        return 0;
    }

    if (anchor_node_prev == *head_ref) {
        return remove_first(head_ref, result);
    }

    strcpy(result, anchor_node_prev->name);
    unlink_node(anchor_node_prev, anchor_node);
    free(anchor_node);

    return 1;
}

int remove_after(const char *after, char *result) {
    return remove_after_h(&head, after, result);
}
int remove_after_h(Node **head_ref, const char *after, char *result) {
    Node* anchor_node_prev;
    Node* anchor_node = find_node(*head_ref, after, &anchor_node_prev);

    if (anchor_node == NULL) {
        return 0;
    }

    Node* next = XOR(anchor_node->npt, anchor_node_prev);
    Node* next_next = XOR(next->npt, anchor_node);

    strcpy(result, next->name);
    unlink_node(next, next_next);
    free(next);

    return 1;
}


void print_list() {
    print_list_h(head);
}
void print_list_h(Node* head) {
    struct Node *curr = head;
    struct Node *prev = NULL;
    struct Node *next;

    printf("List: ");

    while (curr != NULL) {
        printf("%s ", curr->name);

        next = XOR(prev, curr->npt);

        prev = curr;
        curr = next;
    }
}


int main() {
//    insert_string("You");
//    insert_string("Are");
//    insert_string("How");
//    insert_string("World");
//    insert_string("Hello");
//    print_list();     //Hello -> World -> How -> Are -> You
//
//    printf("\nInserting in the middle\n");
//    insert_before("World", "Beautiful");
//    insert_after("How", "Old");
//    print_list();
//
//    printf("\nInserting at the boundaries\n");
//    insert_before("Hello", "Uhm");
//    insert_after("You", "Really");
//    print_list();
//
//    printf("\nInserting something very long\n");
//    insert_string("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
//    print_list();
//
//    printf("\nRemoving\n");
//    char result[64/sizeof(char)];
//    remove_string(result);
//    printf("Removed: %s\n", result);
//    remove_before("World", result);
//    printf("Removed: %s\n", result);
//    remove_after("You", result);
//    printf("Removed: %s\n", result);
//    print_list();
//
//    return 0;

    insert_string("Alpha");
    insert_string("Bravo");
    insert_string("Charlie");
    insert_after("Bravo", "Delta");
    insert_before("Alpha", "Echo");
    print_list(); // Charlie -> Bravo -> Delta -> Echo -> Alpha
    printf("\n");

    char result[10];
    int ret;

    ret = remove_after("Delta",result);
    if(ret)
        printf("Removed: %s\n", result); //Echo

    ret = remove_before("Bravo", result);
    if(ret)
        printf("Removed: %s\n", result); //Charlie

    ret = remove_string(result);
    if(ret)
        printf("Removed: %s\n", result); //Bravo

    print_list();


}

//int main(int argc, char *argv[]) {
//
//	insert_string("Alpha");
//	insert_string("Bravo");
//	insert_string("Charlie");
//	insert_after("Bravo", "Delta");
//	insert_before("Alpha", "Echo");
//	print_list(); // Charlie -> Bravo -> Delta -> Echo -> Alpha
//
//	char result[10];
//	int ret;
//
//	ret = remove_after("Delta",result);
//	if(ret)
//		printf("Removed: %s\n", result);
//
//	ret = remove_before("Bravo", result);
//	if(ret)
//		printf("Removed: %s\n", result);
//
//	ret = remove_string(result);
//	if(ret)
//		printf("Removed: %s\n", result);
//
//	print_list();
//}
