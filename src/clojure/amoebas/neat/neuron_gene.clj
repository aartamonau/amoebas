(ns amoebas.neat.neuron-gene
  (:use amoebas.utils.force-rec
        amoebas.utils.serialization))

(defrecord neuron-gene
  [id type recurrent? activation-response split-x split-y])

(defmethod print-dup neuron-gene [o w]
  (.write
     w
     (str "#=(amoebas.neat.neuron-gene.neuron-gene. "
          (apply str
                 (interpose " "
                            [ (serialize (:id o))
                              (serialize (:type o))
                              (serialize (:recurrent? o))
                              (serialize (:activation-response o))
                              (serialize (:split-x o))
                              (serialize (:split-y o)) ]))
          ")")))

(defmethod force-rec neuron-gene
  [x] x)

(defn make-neuron-gene
  ([type id x y]
     (make-neuron-gene type id x y false))
  ([type id x y recurrent?]
     (new neuron-gene
          id type recurrent? 1 x y)))
