(ns amoebas.controller
  (:use [amoebas.neat.genetic-algorithm :exclude [simulate]]
        amoebas.neat.phenotype
        amoebas.neat.innovation
        amoebas.utils.random
        amoebas.brain)
  (:import (amoebas.java.interop Visualizer)
           (amoebas.java.battlevisualisation InfoDisplay)
           (amoebas.java.interop Simulator Simulator$SimulationResult)))

(defn select-random-amoebas [population]
  (let [n (count population)

        first-num  (random-int 0 (dec n))
        second-num (loop [rnd (random-int 0 (dec n))]
                     (if (= rnd first-num)
                       (recur (random-int 0 (dec n)))
                       rnd))

        first      (nth population first-num)
        second     (nth population second-num)]
    [ first
      second ]))

(def population-size 30)

(defn simulate [a b]
  (let [result (doto
                   (new Simulator (make-brain a)
                        (make-brain b))
                   (.simulate))]
    (cond
     (= result Simulator$SimulationResult/FIRST) 'a
     (= result Simulator$SimulationResult/SECOND) 'b
     :else 'draw)))

(defn main-loop []
  (let [visualizer (new Visualizer)
        info       (InfoDisplay/instance)
        ga         (agent (make-genetic-algorithm population-size
                                                  inputs-number
                                                  outputs-number
                                                  simulate))]
    (loop []
      (do (. info showGenerationNum (:generation @ga))
          (let [population (:population @ga)]
            (send ga next-generation)

            (loop [i 0]
              (println i)
              (println (agent-errors ga))
              (println (:generation @ga))
              (let [[a b] (select-random-amoebas population)
                    a-brain (make-brain a)
                    b-brain (make-brain b)]
                (do (. visualizer showSynchronously a-brain b-brain)

                    ;; buggy
                    (when-not (await-for 10 ga)
                      (recur (inc i)))))))

          (println "****************************************************")
          (recur)))))

(main-loop)
