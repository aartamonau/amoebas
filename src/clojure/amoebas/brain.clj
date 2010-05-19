(ns amoebas.brain
  (:use amoebas.neat.genome
        amoebas.neat.phenotype
        amoebas.utils.seq
        amoebas.utils.random)
  (:import (java.awt.geom Point2D$Double)))

(defn slice [[start end] xs]
  (take (inc (- end start))
        (drop start xs)))

(defn make-point [x y]
  (Point2D$Double. x y))

(def inputs-number 4)
(def outputs-number 5)

(def movement-outputs [0 1])
(def aim-outputs      [2 3])
(def shoot-output     4)

(defn handle-null-input [vector]
  (if (nil? vector)
    [-1.0 -1.0]
    [ (. vector x)
      (. vector y) ]))

(defn make-brain [genome]
  (let [nn        (atom (genome-to-phenotype genome))
        reactions (atom nil)]
    (proxy [amoebas.java.battleSimulation.IBrain] []

      (feedSenses
         [enemy-vector thorn-vector]
         (do (swap! nn (fn [nn]
                         (update nn
                                 (concat
                                   (handle-null-input enemy-vector)
                                   (handle-null-input thorn-vector)))))
             (reset! reactions (outputs @nn))))

      (getMovementVector
         []
         (apply make-point
                (slice movement-outputs @reactions)))

      (getAimVector
         []
         (apply make-point
                (slice aim-outputs @reactions)))

      (shallWeShoot
         []
         (let [reaction (nth @reactions shoot-output)]
           (if (> reaction 0)
             true
             false))))))
