(ns amoebas.brain
  (:use amoebas.neat.phenotype
        amoebas.utils.seq)
  (:import (java.awt.geom Point2D$Double)))

(defn slice [[start end] xs]
  (take (inc (- end start))
        (drop start xs)))

(defn make-point [x y]
  (Point2D$Double. x y))

(def movement-outputs [0 1])
(def aim-outputs      [2 3])
(def shoot-output     4)

(defn make-brain [neural-net]
  (let [nn        (atom neural-net)
        reactions (atom nil)]
    (proxy [amoebas.java.battleSimulation.IBrain] []

      (feedSenses
         [self-position enemy-position thorn-position walls]
         (do (swap! nn (fn [nn]
                         (update nn
                                 (concat
                                  [ (. self-position x)
                                    (. self-position y)
                                    (. enemy-position x)
                                    (. enemy-position y) ]

                                  (if (nil? thorn-position)
                                    [-1.0, -1.0]
                                    [ (. thorn-position x)
                                      (. thorn-position y)])

                                  (concat-map #(vector (. % x)
                                                       (. % y)
                                                       (. % width)
                                                       (. % height))
                                              (seq walls))))))
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
