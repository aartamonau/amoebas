(ns amoebas.controller
  (:use clojure.contrib.io

        [amoebas.neat.genetic-algorithm :exclude [simulate]]
        amoebas.neat.phenotype
        amoebas.neat.innovation
        amoebas.utils.random
        amoebas.utils.serialization
        amoebas.utils.force-rec
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

(def population-size 50)
(def save-file (file-str "~/amoebas_save"))

(defn simulate [a b]
  (let [result (. (Simulator. (make-brain a)
                              (make-brain b))
                   simulate)]
    (cond
     (= result Simulator$SimulationResult/FIRST)  'a
     (= result Simulator$SimulationResult/SECOND) 'b
     :else 'draw)))

(defn init-ga []
  (if (. save-file exists)
    (let [[innovation-db ga] (deserialize (slurp* save-file))]
      (do (reset! global-innovation-db innovation-db)
          ga))
    (make-genetic-algorithm population-size
                            inputs-number
                            outputs-number)))

(defn save-ga [ga]
  (spit save-file
        (serialize [@global-innovation-db ga])))

(defn main-loop []
  (let [visualizer (new Visualizer)
        info       (InfoDisplay/instance)
        ga         (agent (init-ga))]
    (loop []
      (do (. info showGenerationNum (:generation @ga))
          (. info showBestAmoeba    (str (second (:leader @ga))))
          (save-ga @ga)

          (let [population (:population @ga)]
            (send ga #(next-generation % simulate))

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
