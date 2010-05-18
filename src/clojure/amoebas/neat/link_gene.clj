(ns amoebas.neat.link-gene
  (:use amoebas.utils.force-rec
        amoebas.utils.serialization))

(defrecord link-gene
  [from to weight enabled? recurrent? innovation-id])

(defmethod print-dup link-gene [o w]
  (.write
     w
     (str "#=(amoebas.neat.link-gene.link-gene. "
          (apply str
                 (interpose " "
                            [ (serialize (:from o))
                              (serialize (:to o))
                              (serialize (:weight o))
                              (serialize (:enabled? o))
                              (serialize (:recurrent? o))
                              (serialize (:innovation-id o)) ]))
          ")")))

(defmethod force-rec link-gene
  [x] x)

(defn make-link-gene
  ([from to enabled? tag weight]
     (make-link-gene from to enabled? tag weight false))
  ([from to enabled? tag weight recurrent?]
     (do (assert (and (not (nil? from))
                      (not (nil? to))))

         (new link-gene
              from to weight enabled? recurrent? tag))))
