(ns amoebas.neat.genetic-algorithm
  (:use amoebas.neat.genome
        amoebas.utils.random
        amoebas.utils.misc
        amoebas.utils.seq)
  (:require [amoebas.neat.parameters :as params]))

(defrecord genetic-algorithm
  [generation
   population
   simulator
   next-genome-id

   leader
   prev-generation-leader
   fitness])

(defn make-genetic-algorithm [population-size
                              nn-inputs nn-outputs
                              simulator]
  (new genetic-algorithm
       0
       (map #(make-genome % nn-inputs nn-outputs)
                     (range population-size))
       simulator
       population-size

       nil
       nil
       1.0))

(defn sus-selection [population
                     number]
  (let [overall-fitness (reduce +
                                (map second population))
        population (reductions #(vector (first %2)
                                        (+ (second %1)
                                           (second %2)))
                               population)
        gap   (/ overall-fitness number)
        start (random 0 gap)]
    (loop [xs       population
           point    start
           selected []]
      (if xs
        (let [[x cum-fitness] (first xs)]
          (if (< point cum-fitness)
            (recur xs (+ point gap) (conj selected x))
            (recur (next xs) point selected)))
        selected))))

(defn simulate [ga a b]
  ((:simulator ga) a b))

(defn simulate-random [ga a b]
  (random-out-of 'a 'draw 'b))

(defn genome-comparator [[genome-1 fitness-1]
                         [genome-2 fitness-2]]
  (- (compare fitness-1 fitness-2)))

(defn tour [ga a b]
  (let [result (simulate ga a b)]
    (cond
     (= result 'a) (hash-map a params/winner-score
                             b params/loser-score)
     (= result 'b) (hash-map a params/loser-score
                             b params/winner-score)
     :else         (hash-map a params/draw-score
                             b params/draw-score))))

(defn pairs [population]
  (loop [[x & rest :as xs] population
         result []]
    (if (empty? xs)
      result
      (recur rest
             (concat result
                     (map #(vector x %) rest))))))

(defn tournament [ga times population]
  (let [games   (pairs population)]
    (apply merge-with +
           (pmap #(apply tour ga %)
                 (reduce concat
                         (replicate times games))))))

(defn mutate [genome]
  (-> genome
      (add-neuron params/chance-to-add-neuron)
      (add-link params/chance-to-add-link
                params/chance-to-add-recurrent-link
                params/tries-to-find-looped-link
                params/tries-to-add-link)
      (mutate-weights params/mutation-rate
                      params/probability-to-replace-weight
                      params/max-weight-perturbation)
      (mutate-activation-response params/activation-mutation-rate
                                  params/max-activation-perturbation)))

(defn next-generation [ga]
  (let [results (tournament ga
                            params/tours-per-generation
                            (:population ga))
        sorted  (sort-by second
                         (comp - compare)
                         results)

        n (count (:population ga))
        elite-count (. Math round
                       (* n params/elite-ratio))
        elite (map first
                   (take elite-count sorted))

        children-count (- n elite-count)
        parents (sus-selection sorted (* children-count 2))

        starting-id (:next-genome-id ga)
        next-genome-id (+ starting-id children-count)
        children-ids (range starting-id next-genome-id)

        crossover (fn [[x y] id] (crossover x (results x)
                                            y (results y)
                                            id))
        children (map (comp mutate crossover)
                      (partition-all 2 parents)
                      children-ids)]
    (assoc ga
      :generation (inc (:generation ga))
      :population (concat elite children)
      :next-genome-id next-genome-id
      :leader         (first (first sorted))
      :prev-generation-leader (:leader ga))))
