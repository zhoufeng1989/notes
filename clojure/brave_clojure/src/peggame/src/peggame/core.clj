(ns peggame.core
  (require [clojure.set :as set])
  (:gen-class))

(defn tri*
  ([] (tri* 0 1))
  ([sum n] 
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))

(def tri (tri*))

(defn triangular?
  [n]
  (= n (last (take-while #(>= n %) tri))))

(defn row-tri
  [row]
  (last (take row tri)))

(defn row-num
  [pos]
  (inc (count (take-while #(> pos %) tri))))

(defn connect
  [board max-pos pos neighbor dest]
  (if (<= dest max-pos)
    (reduce (fn [new-board [p1 p2]]
              (assoc-in new-board [p1 :connection p2] neighbor))
            board
            [[pos dest] [dest pos]])
    board))

(defn connect-right
  [board max-pos pos]
  (let [neighbor (inc pos)
        dest (inc neighbor)]
    (if-not (or (triangular? pos) (triangular? neighbor))
      (connect board max-pos pos neighbor dest)
      board)))

(defn connect-left-down
  [board max-pos pos]
  (let [current-row (row-num pos)
        neighbor (+ current-row pos)
        dest (+ 1 current-row neighbor)]
    (connect board max-pos pos neighbor dest)))
        

(defn connect-right-down
  [board max-pos pos]
  (let [current-row (row-num pos)
        neighbor (+ 1 current-row pos)
        dest (+ 2 current-row neighbor)]
    (connect board max-pos pos neighbor dest)))

(defn add-pos
  [board max-pos pos]
  (let [pegged-board (assoc-in board [pos :pegged] true)]
    (reduce (fn [new-board connection-creation-fn]
              (connection-creation-fn new-board max-pos pos))
            pegged-board
            [connect-right connect-left-down connect-right-down])))

(defn new-board
  [rows]
  (let [initial-board {:rows rows}
        max-pos (row-tri rows)]
    (reduce (fn [new-board pos] (add-pos new-board max-pos pos))
            initial-board
            (range 1 (inc max-pos)))))

(defn pegged?
  [board pos]
  (get-in board [pos :pegged]))

(defn remove-peg
  [board pos]
  (assoc-in board [pos :pegged] false))

(defn place-peg
  [board pos]
  (assoc-in board [pos :pegged] true))

(defn move-peg
  [board p1 p2]
  (place-peg (remove-peg board p1) p2))

(defn valid-moves
  [board pos]
  (into {}
        (filter (fn [[dest jumped]]
                  (and (not (pegged? board dest))
                       (pegged? board jumped)))
                (get-in board [pos :connection]))))

(defn valid-move?
  [board p1 p2]
  (get (valid-moves board p1) p2))

(defn make-move
  [board p1 p2]
  (if-let [jumped (valid-move? board p1 p2)]
    (move-peg (remove-peg board jumped) p1 p2)))

(defn can-move?
  [board]
  (some (comp not-empty (partial valid-moves board))
    (map first (filter #(get (second %) :pegged) board))))

(def alpha-start 97)
(def alpha-end 123)
(def letters (map (comp str char) (range alpha-start alpha-end)))
(def pos-chars 3)

(defn render-pos
  [board pos]
  (str (nth letters (dec pos))
       (if (get-in board [pos :pegged])
         "0" 
         "-")
       ))
(defn row-positions
  [row-num]
  (range (inc (or (row-tri (dec row-num)) 0))
         (inc (row-tri row-num))))
(defn row-padding
  [row-num rows]
  (let [pad-length (/ (* (- rows row-num) pos-chars) 2)]
    (apply str (take pad-length (repeat " ")))))
(defn render-row
  [board row-num]
  (str (row-padding row-num (:rows board))
       (clojure.string/join " " (map (partial render-pos board) (row-positions row-num)))))
(defn print-board
  [board]
  (doseq [row-num (range 1 (inc (:rows board)))]
    (println (render-row board row-num))))

(defn letter->pos
  [letter]
  (inc (- (int (first letter)) alpha-start)))

(defn get-input
  ([] (get-input nil))
  ([default]
   (let [input (clojure.string/trim (read-line))]
     (if (empty? input)
       default
       (clojure.string/lower-case input)))))

(defn characters-as-strings
  [string]
  (map str (filter (fn [s]
                     (let [n (int s)]
                       (and (<= alpha-start n) (> alpha-end n)))) 
                   string))
)
(declare prompt-move user-entered-valid-move user-entered-invalid-move game-over prompt-rows)

(defn prompt-move
  [board]
  (println "\nHere's your board:")
  (print-board board)
  (println "Move from where to where? Enter two letters:")
  (let [input (map letter->pos (characters-as-strings (get-input)))]
    (if-let [new-board (make-move board (first input) (second input))]
      (user-entered-valid-move new-board)
      (user-entered-invalid-move board))))

(defn user-entered-invalid-move
  [board]
  (println "\n!!! That was an invalid move :(\n")
  (prompt-move board))

(defn user-entered-valid-move
  [board]
  (if (can-move? board)
    (prompt-move board)
    (game-over board)))

(defn game-over 
  [board]
  (let [remaining-pegs (count (filter :pegged (vals board)))]
    (println "Game over! You had " remaining-pegs "pegs left:")
    (print-board board)
    (println "Play again? y/n [y]")
    (let [input (get-input "y")]
      (if (= "y" input)
        (prompt-rows)
        (do
          (println "Bye!")
          (System/exit 0))))))

(defn prompt-empty-peg
  [board]
  (println "Here is your board:")
  (print-board board)
  (println "Remove which peg? [e]")
  (prompt-move (remove-peg board (letter->pos (get-input "e")))))

(defn prompt-rows
  []
  (println "How many rows? [5]")
  (let [rows (Integer. (get-input 5))
        board (new-board rows)]
    (prompt-empty-peg board)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (prompt-rows)
  )
  
