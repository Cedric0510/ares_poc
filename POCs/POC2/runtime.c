#include <stdio.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <bot>\n", argv[0]);
        return 1;
    }

    printf("Bot a lancer: %s\n", argv[1]);
    return 0;
}
