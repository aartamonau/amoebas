(ns amoebas.neat.genetic-algorithm
  (:use clojure.contrib.seq-utils
        amoebas.neat.genome
        amoebas.utils.random
        amoebas.utils.misc
        amoebas.utils.seq))

(defstruct genetic-algorithm
  :generation
  :population
  :next-genome-id

  :prev-generation-leader
  :fitness)

(defn make-genetic-algorithm [population-size
                              nn-inputs nn-outputs]
  (struct-map genetic-algorithm
    :generation 0
    :population (doall
                 (map #(make-genome % nn-inputs nn-outputs)
                     (range population-size)))
    :next-genome-id population-size

    :prev-generation-leader nil
    :fitness 1.0))

(defn tour
  ([a] a)
  ([[a a-members] [b b-members]]
     (letfn [(scale-members
              [scalar members]
              (map #(assoc-modify %
                                  1 (fn [x] (* scalar x))) members))]
       (let [winner       (random-out-of a b)
             winner-lives (random)
             winner-score (/ 1.0
                             (+ 1 (- 1 winner-lives)))
             loser-score  (- 1 winner-score)]
         (do
           (print a b "\n")
           (print winner winner-score "\n")
           (print "%%%%%%%%%\n")
           (print loser-score "\n")
           (print "--------\n")
           (if (= winner a)
             [a (concat (scale-members winner-score a-members)
                        (scale-members loser-score b-members))]
             [b (concat (scale-members loser-score a-members)
                        (scale-members winner-score b-members))]))))))

(defn tournament [population]
  (letfn [(dummy-representative
           [x]
           [x [[x 1.0]]])]
    (let [representatives (map dummy-representative (shuffle population))]
      (loop [rs representatives]
        ;; rs))))
        (if (> (count rs) 1)
          (recur (map #(apply tour %)
                      (partition-all 2 rs)))
          rs)))))
          ;; (first (rest (first rs))))))))
