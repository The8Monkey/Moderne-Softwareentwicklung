(defun create-board ()
  (make-list 9 :initial-element '0))

(defun print-board (board)
  (dotimes (i 3)
    (dotimes (j 3)
      (format t "~A " (nth (+ i (* j 3)) board)))
    (format t "~%")))

(defun make-move (board pos player)
  (setf (nth pos board) player))

(defun has-won (board player)
  (let ((win-positions '((0 1 2) (3 4 5) (6 7 8) (0 3 6) (1 4 7) (2 5 8) (0 4 8) (2 4 6))))
    (some #'(lambda (pos) (every #'(lambda (p) (eq (nth p board) player)) pos)) win-positions)))

(defun play-game ()
  (let ((board (create-board)))
    (loop for player = 'X then (if (eq player 'X) 'O 'X)
          do (print-board board)
             (format t "Player ~A, choose a position (0-8): ~%" player)
             (let ((pos (read)))
               (if (eq (nth pos board) '0)
                   (progn
                     (make-move board pos player)
                     (when (has-won board player)
                       (print-board board)
                       (format t "Player ~A wins!~%" player)
                       (return)))
                   (format t "Invalid move. Try again.~%"))))))

(play-game)