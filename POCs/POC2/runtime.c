#include <poll.h>
#include <signal.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <bot>\n", argv[0]);
        return 1;
    }

    int output_pipe[2];
    if (pipe(output_pipe) == -1) {
        perror("pipe");
        return 1;
    }

    pid_t child_pid = fork();
    if (child_pid == -1) {
        perror("fork");
        close(output_pipe[0]);
        close(output_pipe[1]);
        return 1;
    }

    if (child_pid == 0) {
        close(output_pipe[0]);

        if (dup2(output_pipe[1], STDOUT_FILENO) == -1) {
            perror("dup2");
            _exit(1);
        }

        close(output_pipe[1]);
        char *bot_args[] = {argv[1], NULL};
        execvp(argv[1], bot_args);
        perror("execvp");
        _exit(1);
    }

    close(output_pipe[1]);

    struct pollfd output_poll;
    output_poll.fd = output_pipe[0];
    output_poll.events = POLLIN;
    output_poll.revents = 0;

    int poll_result = poll(&output_poll, 1, 500);
    ssize_t bytes_read = 0;

    if (poll_result > 0) {
        char first_byte;
        bytes_read = read(output_pipe[0], &first_byte, 1);
    }

    kill(child_pid, SIGKILL);

    if (waitpid(child_pid, NULL, 0) == -1) {
        perror("waitpid");
        close(output_pipe[0]);
        return 1;
    }

    close(output_pipe[0]);

    if (poll_result == -1) {
        perror("poll");
        return 1;
    }

    if (bytes_read == -1) {
        perror("read");
        return 1;
    }

    if (bytes_read > 0) {
        printf("OK\n");
    } else {
        printf("TIMEOUT\n");
    }

    return 0;
}
