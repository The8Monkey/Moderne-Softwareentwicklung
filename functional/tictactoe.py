def create_board():
    return [' ']*9

def is_valid_move(board, move):
    return board[move] == ' '

def make_move(board, move, player):
    return board[:move] + [player] + board[move+1:]

def print_board(board):
    for i in range(0, 9, 3):
        print(board[i:i+3])
    print("\n")

def has_won(board, player):
    win_positions = [(0,1,2), (3,4,5), (6,7,8), (0,3,6), (1,4,7), (2,5,8), (0,4,8), (2,4,6)]
    return any(board[i] == board[j] == board[k] == player for i,j,k in win_positions)

def play_game():
    board = create_board()
    current_player = 'X'

    while True:
        print_board(board)
        move = int(input(f"Player {current_player}, choose a position (0-8): "))
        if is_valid_move(board, move):
            board = make_move(board, move, current_player)
            if has_won(board, current_player):
                print(f"Player {current_player} wins!")
                break
            current_player = 'O' if current_player == 'X' else 'X'
        else:
            print("Invalid move. Try again.")

play_game()
