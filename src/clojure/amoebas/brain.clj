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
  (let [nx (* 2 (- x 0.5))
        ny (* 2 (- y 0.5))]
    (Point2D$Double. nx ny)))

(def inputs-number 7)
(def outputs-number 5)

(def movement-outputs [0 1])
(def aim-outputs      [2 3])
(def shoot-output     4)

(defn normalize-vector [vector]
  (let [norm (. Math sqrt (+ (* (. vector x) (. vector x))
                             (* (. vector y) (. vector y))))]
    (if (not (zero? norm))
      [ (/ (. vector x) norm) (/ (. vector y) norm)
        norm ]
      [ (. vector x) (. vector y) 0.0 ])))

(defn make-brain [genome]
  (let [nn        (atom (genome-to-phenotype genome))
        reactions (atom nil)]
    (proxy [amoebas.java.battleSimulation.IBrain] []

      (feedSenses
         [enemy-vector thorn-vector]
         (do (swap! nn (fn [nn]
                         (update nn
                                 (concat
                                   [(random -1.0 1.0)]
                                   (normalize-vector enemy-vector)

                                   (if (nil? thorn-vector)
                                     [-1.0 -1.0 -1.0]
                                     (normalize-vector thorn-vector))))))

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
           (if (> reaction 0.5)
             true
             false))))))
