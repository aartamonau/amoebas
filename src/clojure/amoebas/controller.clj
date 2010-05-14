(ns amoebas.controller
  (:use amoebas.neat.genetic-algorithm
        amoebas.neat.phenotype
        amoebas.neat.genome
        amoebas.neat.innovation
        amoebas.utils.random
        amoebas.brain)
  (:import (amoebas.java.interop Visualizer)
           (amoebas.java.battlevisualisation InfoDisplay)))

(defn population-size 50)

(defstruct controller
  :visualizer
  :info
  :genetic-algorithm)

(defn make-controller []
  :visualizer (new Visualizer)
  :info       (InfoDisplay/instance)
  :genetic-algorithm (make-genetic-algorithm population-size
                                             inputs-number
                                             outputs-number))

(defn select-random-phenotypes [ga]
  (let [population (:population ga)
        n          (count population)

        first-num  (random 0 (dec n))
        second-num (loop [rnd (random-int 0 (dec n))]
                     (if (= rnd first-num)
                       (recur (random-int 0 (dec n)))
                       rnd))

        first      (nth population first-num)
        second     (nth population second-num)]
    [ (genome-to-phenotype first)
      (genome-to-phenotype second) ]))

(defn main-loop [controller]
  (let [visualizer (:visualizer controller)
        info       (:info controller)]
    (loop [ga (:genetic-algorithm controller)]
      (do (. info showGenerationNum (:generation ga))
          (. info showBestAmoeba    (if (nil? (:leader ga))
                                      "not available"
                                      (. (:id (:leader ga))
                                         toString)))

          (let [[a b] (select-random-phenotypes ga)
                a-brain (make-brain a)
                b-brain (make-brain b)]
            (. visualizer show a-brain b-brain))

          (recur (next-generation ga))))))

(binding [*innovation-db* nil]
  (main-loop (make-controller)))
