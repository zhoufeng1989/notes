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
    (reduce (fn [new-board pos] (println new-board) (add-pos new-board max-pos pos))
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


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (new-board 5))
  )
  
