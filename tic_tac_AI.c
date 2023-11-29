#include <stdio.h>
#include <math.h>

#define BOARD_SIZE 3

char board[BOARD_SIZE][BOARD_SIZE];

typedef struct
{
    int row;
    int col;
} Move;

int isBoardFull()
{
    int i, j;
    for (i = 0; i < BOARD_SIZE; i++)
    {
        for (j = 0; j < BOARD_SIZE; j++)
        {
            if (board[i][j] == ' ')
                return 0; // Board is not full
        }
    }
    return 1; // Board is full
}

int evaluate()
{
    int i, j;

    // Check rows
    for (i = 0; i < BOARD_SIZE; i++)
    {
        if (board[i][0] == board[i][1] && board[i][1] == board[i][2])
        {
            if (board[i][0] == 'X')
                return 10;
            else if (board[i][0] == 'O')
                return -10;
        }
    }

    // Check columns
    for (j = 0; j < BOARD_SIZE; j++)
    {
        if (board[0][j] == board[1][j] && board[1][j] == board[2][j])
        {
            if (board[0][j] == 'X')
                return 10;
            else if (board[0][j] == 'O')
                return -10;
        }
    }

    // Check diagonals
    if (board[0][0] == board[1][1] && board[1][1] == board[2][2])
    {
        if (board[0][0] == 'X')
            return 10;
        else if (board[0][0] == 'O')
            return -10;
    }
    if (board[0][2] == board[1][1] && board[1][1] == board[2][0])
    {
        if (board[0][2] == 'X')
            return 10;
        else if (board[0][2] == 'O')
            return -10;
    }

    return 0; // No winner
}

int isMoveValid(int row, int col)
{
    return (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == ' ');
}

int isGameOver()
{
    return evaluate() != 0 || isBoardFull();
}

int minimax(int depth, int isMaximizing)
{
    int score = evaluate();

    if (score == 10)
        return score - depth;

    if (score == -10)
        return score + depth;

    if (isBoardFull())
        return 0;

    int bestScore;

    if (isMaximizing)
    {
        bestScore = -1000;
        int i, j;
        for (i = 0; i < BOARD_SIZE; i++)
        {
            for (j = 0; j < BOARD_SIZE; j++)
            {
                if (board[i][j] == ' ')
                {
                    board[i][j] = 'X';
                    bestScore = fmax(bestScore, minimax(depth + 1, !isMaximizing));
                    board[i][j] = ' ';
                }
            }
        }
        return bestScore;
    }
    else
    {
        bestScore = 1000;
        int i, j;
        for (i = 0; i < BOARD_SIZE; i++)
        {
            for (j = 0; j < BOARD_SIZE; j++)
            {
                if (board[i][j] == ' ')
                {
                    board[i][j] = 'O';
                    bestScore = fmin(bestScore, minimax(depth + 1, !isMaximizing));
                    board[i][j] = ' ';
                }
            }
        }
        return bestScore;
    }
}

Move findBestMove()
{
    int bestScore = -1000;
    Move bestMove;
    bestMove.row = -1;
    bestMove.col = -1;

    int i, j;
    for (i = 0; i < BOARD_SIZE; i++)
    {
        for (j = 0; j < BOARD_SIZE; j++)
        {
            if (board[i][j] == ' ')
            {
                board[i][j] = 'X';
                int moveScore = minimax(0, 0);
                board[i][j] = ' ';

                if (moveScore > bestScore)
                {
                    bestScore = moveScore;
                    bestMove.row = i;
                    bestMove.col = j;
                }
            }
        }
    }

    return bestMove;
}

void displayBoard()
{
    int i, j;
    for (i = 0; i < BOARD_SIZE; i++)
    {
        for (j = 0; j < BOARD_SIZE; j++)
        {
            printf(" %c ", board[i][j]);
            if (j < BOARD_SIZE - 1)
                printf("|");
        }
        printf("\n");
        if (i < BOARD_SIZE - 1)
            printf("---+---+---\n");
    }
}

int main()
{
    int player = 1; // Player 1 is human, Player 2 is computer

    int row, col;

    // Initialize the board
    int i, j;
    for (i = 0; i < BOARD_SIZE; i++)
    {
        for (j = 0; j < BOARD_SIZE; j++)
        {
            board[i][j] = ' ';
        }
    }

    printf("Tic Tac Toe\n");
    displayBoard();

    while (!isGameOver())
    {
        if (player == 1)
        {
            // Human's turn
            printf("Player's move (row, col): ");
            scanf("%d %d", &row, &col);

            if (!isMoveValid(row, col))
            {
                printf("Invalid move. Please try again.\n");
                continue;
            }

            board[row][col] = 'O';
        }
        else
        {
            // Computer's turn
            Move bestMove = findBestMove();
            board[bestMove.row][bestMove.col] = 'X';
            printf("Computer's move (row, col): %d %d\n", bestMove.row, bestMove.col);
        }

        displayBoard();

        // Switch player
        player = 3 - player; // Toggle between 1 and 2
    }

    int result = evaluate();
    if (result == 10)
        printf("Player 2 (Computer) wins!\n");
    else if (result == -10)
        printf("Player 1 (You) wins!\n");
    else
        printf("It's a draw!\n");

    return 0;
}
